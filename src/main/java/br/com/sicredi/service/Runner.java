package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import br.com.sicredi.model.StatusConta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Runner implements CommandLineRunner {


    public Runner(Validador validador) {
        this.validador = validador;
    }

    private final Validador validador;

    @Override
    public void run(String... args) throws Exception {

        validador.validar(args);

        String path = args[0];

        ReceitaService receitaService = new ReceitaService();

        System.out.println(path);
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine(); // TODO split first line
        List<DadoBancario> dadosBancario = new ArrayList<>();
        while (reader.ready()) {
            String[] data = reader.readLine().split(";");
            DadoBancario dadoBancario = new DadoBancario();
            dadoBancario.setAgencia(data[0]);
            dadoBancario.setConta(data[1].replaceAll("[^\\d.]", ""));
            dadoBancario.setSaldo(Double.parseDouble(data[2].replace(",", ".")));
            dadoBancario.setStatus(StatusConta.valueOf(data[3]));
            dadosBancario.add(dadoBancario);
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(path.replaceAll("\\.csv","")+"_result.csv"));
        writer.write("agencia;conta;saldo;status;processado");
        nextLine(writer);
        DecimalFormat df = new DecimalFormat("0.00");
        for (DadoBancario dado : dadosBancario
        ) {

            boolean processado = receitaService.atualizarConta(
                    dado.getAgencia(),
                    dado.getConta(),
                    dado.getSaldo(),
                    dado.getStatus().name()
            );

            dado.setProcessado(processado);
            writerSplit(writer,dado.getAgencia());
            writerSplit(writer,dado.getConta().substring(0,5) + "-" + dado.getConta().charAt(5));
            writerSplit(writer,df.format(dado.getSaldo()).replace(".",","));
            writerSplit(writer,dado.getStatus().name());
            writerFinish(writer,dado.getProcessado());
        }




        writer.close();
    }

    private void writerFinish(BufferedWriter writer, Boolean text) throws IOException {
        writer.write(text.toString());
        nextLine(writer);
    }

    private void writerSplit(BufferedWriter writer, String text) throws IOException {
        writer.write(text + ";");
    }

    private void nextLine(BufferedWriter writer) throws IOException {
        writer.write("\n");
    }

}

