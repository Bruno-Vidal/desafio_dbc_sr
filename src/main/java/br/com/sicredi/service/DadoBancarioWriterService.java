package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class DadoBancarioWriterService {

    private final String SEPARATOR = ";";

    public String writer(List<DadoBancario> dadosBancario, String path) throws IOException {

        String resultPath = path.replaceAll("\\.csv", "") + "_result.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(resultPath));
        writer.write("agencia;conta;saldo;status;processado");
        nextLine(writer);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (DadoBancario dado : dadosBancario
        ) {

            writerSplit(writer, dado.getAgencia());
            writerSplit(writer, dado.getConta().substring(0, 5) + "-" + dado.getConta().charAt(5));
            writerSplit(writer, decimalFormat.format(dado.getSaldo()).replace(".", ","));
            writerSplit(writer, dado.getStatus().name());
            writerFinish(writer, dado.getProcessado());
        }


        writer.close();

        return resultPath;
    }

    private void writerFinish(BufferedWriter writer, Boolean text) throws IOException {
        writer.write(text.toString());
        nextLine(writer);
    }

    private void writerSplit(BufferedWriter writer, String text) throws IOException {
        writer.write(text + SEPARATOR);
    }

    private void nextLine(BufferedWriter writer) throws IOException {
        writer.write("\n");
    }


}
