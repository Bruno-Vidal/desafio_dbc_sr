package br.com.sicredi;

import br.com.sicredi.service.Runner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(classes = SincronizacaoReceita.class)

class ReceitaApplicationTests {

    @MockBean
    private Runner runner;

    @BeforeEach
    public void before() throws Exception {

        doNothing().when(runner).run(any());
    }

    @Test
    void contextLoads() {
    }

}
