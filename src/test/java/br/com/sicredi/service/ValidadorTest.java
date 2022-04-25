package br.com.sicredi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ValidadorTest {

    @InjectMocks
    private Validador validador;

    @Test
    @DisplayName("validação da ausencia de parametros")
    public void validarInexistenciaParametros(){

        String[] args = {};

        boolean validacao = validador.validar(args);

        assertEquals(false , validacao);
    }

    @Test
    @DisplayName("validação parametro com extensão incorreta")
    public void validarExtensaoIncorreta(){

        String[] args = {"C:\\sicred\\receita_21_04_2022.xls"};

        boolean validacao = validador.validar(args);

        assertEquals(false , validacao);
    }

    @Test
    @DisplayName("validação de parametro com inexistencia de arquivo")
    public void validarInexistenciaArquivo(){

        String path = ".csv";
        File file ;
        do {
            path = "test_" + path;
            file = new File(path);
        }while (file.isFile());

        boolean validacao = validador.validar(file.getAbsolutePath());

        assertEquals(false , validacao);
    }

    @Test
    @DisplayName("validação de parametro com existencia de arquivo")
    public void validarExistenciaArquivo() throws IOException {

        String path = "test.csv";
        File file = new File(path);
        file.createNewFile();
        String absolutePath = file.getAbsolutePath();


        boolean validacao = validador.validar(absolutePath);

        file.deleteOnExit();

        assertEquals(true , validacao);
    }
}