package br.com.sicredi.service;

import br.com.sicredi.DataMockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class RunnerTest {

    @InjectMocks
    private Runner runner;

    @Mock
    private Validador validador;

    @Mock
    private DadoBancarioReaderService dadoBancarioReaderService;
    @Mock
    private ReceitaService receitaService;
    @Mock
    private DadoBancarioWriterService dadoBancarioWriterService;


    @Test
    @DisplayName("Validar Fluxo de execucao")
    public void validarFluxoExecucao() throws Exception {
        String path = Paths.get("src/test/resources/csv/receita_21_04_2022.csv").toAbsolutePath().toString();

        when(validador.validar(any())).thenReturn(true);
        when(dadoBancarioReaderService.read(path)).thenReturn(DataMockFactory.getDadosBancarios());
        when(receitaService.atualizarConta(anyString(), anyString(), anyDouble(), anyString())).thenReturn(Boolean.TRUE);
        when(dadoBancarioWriterService.writer(any(), any())).thenReturn(path);

        runner.run(path);

        assertDoesNotThrow(() -> runner.run(path));
    }

    @Test
    @DisplayName("Validar Fluxo de execucao com excessao")
    public void validarFluxoExecucaoExcessao() throws Exception {
        String path = Paths.get("src/test/resources/csv/receita_21_04_2022.csv").toAbsolutePath().toString();

        when(validador.validar(any())).thenReturn(true);
        when(dadoBancarioReaderService.read(path)).thenReturn(DataMockFactory.getDadosBancarios());
        when(receitaService.atualizarConta(anyString(), anyString(), anyDouble(), anyString())).thenThrow(new RuntimeException());
        when(dadoBancarioWriterService.writer(any(), any())).thenReturn(path);

        runner.run(path);

        assertDoesNotThrow(() -> runner.run(path));
    }

    @Test()
    @DisplayName("Validar Fluxo de execucao com excessao de validacao")
    public void validarFluxoExecucaoExcessaoValidacao() throws Exception {
        String path = Paths.get("src/test/resources/csv/receita_21_04_2022.csv").toAbsolutePath().toString();

        when(validador.validar(any())).thenReturn(false);

        assertThrows(RuntimeException.class,() -> runner.run(path));
    }

}