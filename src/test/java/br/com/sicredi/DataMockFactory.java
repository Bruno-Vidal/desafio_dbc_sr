package br.com.sicredi;

import br.com.sicredi.model.DadoBancario;
import br.com.sicredi.model.StatusConta;

import java.util.Arrays;
import java.util.List;

public class DataMockFactory {
    public static List<DadoBancario> getDadosBancarios() {
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
