package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DadoBancarioReaderServiceTest {

    @InjectMocks
    private DadoBancarioReaderService dadoBancarioReaderService;

    @Test
    @DisplayName("validar leitura e mapeamento de csv com sucesso")
    public void validarleituraArquivoSucesso() throws IOException {

        Path path = Paths.get("src/test/resources/csv/receita_21_04_2022.csv");
        List<DadoBancario> dadoBancarios = dadoBancarioReaderService.read(path.toAbsolutePath().toString());

        assertTrue(dadoBancarios.size() > 0);
        assertNotNull(dadoBancarios);
        assertFalse(dadoBancarios.isEmpty());
    }
}