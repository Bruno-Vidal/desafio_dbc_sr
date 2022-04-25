package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import br.com.sicredi.model.StatusConta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
        List<DadoBancario> dadoBancarios = getDadosBancarios();
        String resultPath = dadoBancarioWriterService.writer(dadoBancarios, path.toAbsolutePath().toString());

        File result = new File(resultPath);

        assertTrue(result.isFile());
    }

    private List<DadoBancario> getDadosBancarios() {
        DadoBancario first = new DadoBancario();
        DadoBancario second = new DadoBancario();

        first.setAgencia("0101");
        first.setConta("122256");
        first.setSaldo(100.00);
        first.setStatus(StatusConta.A);
        first.setProcessado(Boolean.TRUE);

        second.setAgencia("0101");
        second.setConta("122784");
        second.setSaldo(122.50);
        second.setStatus(StatusConta.B);
        second.setProcessado(Boolean.FALSE);

        return Arrays.asList(first, second);
    }

}