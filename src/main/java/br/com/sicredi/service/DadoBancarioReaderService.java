package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import br.com.sicredi.model.StatusConta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DadoBancarioReaderService {

    private static final Logger logger = LoggerFactory.getLogger(DadoBancarioReaderService.class);

    private final String SEPARATOR = ";";

    public List<DadoBancario> read(String path) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));

        skipHeader(reader);

        List<DadoBancario> dadosBancario = new ArrayList<>();
        
        while (reader.ready()) {
            
            String[] data = reader.readLine().split(SEPARATOR);
            DadoBancario dadoBancario = mapDadoBancario(data);
            dadosBancario.add(dadoBancario);
        }
        reader.close();

        return dadosBancario;
    }

    private String skipHeader(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    private DadoBancario mapDadoBancario(String[] data) {
        try {
            DadoBancario dadoBancario = new DadoBancario();
            dadoBancario.setAgencia(data[0]);
            dadoBancario.setConta(data[1].replaceAll("[^\\d.]", ""));
            dadoBancario.setSaldo(Double.parseDouble(data[2].replace(",", ".")));
            dadoBancario.setStatus(StatusConta.valueOf(data[3]));
            return dadoBancario;
        }catch (Exception ex) {
            logger.error("Erro de leitura do arquivo: ");
            logger.warn(
                    "Siga o formato:\n" +
                    "\tagencia;conta;saldo;status\n" +
                    "\t0101;12225-6;100,00;A\n" +
                    "\t0101;12226-8;3200,50;A\n" +
                    "\t3202;40011-1;-35,12;I\n" +
                    "\t3202;54001-2;0,00;P\n" +
                    "\t3202;00321-2;34500,00;B");
            throw ex;
        }
    }
}
