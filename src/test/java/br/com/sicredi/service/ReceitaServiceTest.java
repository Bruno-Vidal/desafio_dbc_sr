package br.com.sicredi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReceitaServiceTest {

    @Autowired
    private ReceitaService receitaService;


    @Test
    public void sucessoAtualizacaoConta() throws InterruptedException {
        boolean processado = receitaService.atualizarConta("0000", "000000", 100.00, "A");
        assertEquals(processado, true);
    }

    @Test
    public void agenciaError() throws InterruptedException {
        boolean processado = receitaService.atualizarConta("00000", "000000", 100.00, "A");
        assertEquals(processado, false);
    }

    @Test
    public void contaError() throws InterruptedException {
        boolean processado = receitaService.atualizarConta("0000", "0000000", 100.00, "A");
        assertEquals(processado, false);
    }

    @Test
    public void statusError() throws InterruptedException {
        boolean processado = receitaService.atualizarConta("0000", "000000", 100.00, "Z");
        assertEquals(processado, false);
    }
}