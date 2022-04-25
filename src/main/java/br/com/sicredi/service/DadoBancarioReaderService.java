package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import br.com.sicredi.model.StatusConta;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DadoBancarioReaderService {

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
        DadoBancario dadoBancario = new DadoBancario();
        dadoBancario.setAgencia(data[0]);
        dadoBancario.setConta(data[1].replaceAll("[^\\d.]", ""));
        dadoBancario.setSaldo(Double.parseDouble(data[2].replace(",", ".")));
        dadoBancario.setStatus(StatusConta.valueOf(data[3]));
        return dadoBancario;
    }
}
