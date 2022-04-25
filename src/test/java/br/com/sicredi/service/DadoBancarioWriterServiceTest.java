package br.com.sicredi.service;

import br.com.sicredi.DataMockFactory;
import br.com.sicredi.model.DadoBancario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DadoBancarioWriterServiceTest {

    @InjectMocks
    private DadoBancarioWriterService dadoBancarioWriterService;

    @Test
    @DisplayName("validar escrita de csv com sucesso")
    public void validarEscritaArquivoSucesso() throws IOException {

        Path path = Paths.get("src/test/resources/csv/receita_21_04_2022.csv");
        List<DadoBancario> dadoBancarios = DataMockFactory.getDadosBancarios();
        String resultPath = dadoBancarioWriterService.writer(dadoBancarios, path.toAbsolutePath().toString());

        File result = new File(resultPath);
        boolean isFile = result.isFile();
        result.deleteOnExit();

        assertTrue(isFile);
    }


}