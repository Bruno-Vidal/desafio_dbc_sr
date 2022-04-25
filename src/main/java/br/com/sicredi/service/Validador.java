package br.com.sicredi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Validador {

    private static final Logger logger = LoggerFactory.getLogger(Validador.class);
    private static final String EXTENSAO = "csv";

    public boolean validar(String... args) {
        if(args.length < 1) {
            logger.error("Erro ao executar: para o processo acontecer eh necessario informar o endereco do arquivo\n ex: \"C:\\sicred\\receita_21_04_2022.csv\" ");
            return false;
        }

        String path  = args[0];

        if(! path.endsWith(EXTENSAO)) {
            logger.error("Erro ao executar: a extensao do arquivo deve ser \"{}\"", EXTENSAO);
            return false;
        }

        File file = new File(path);
        if (! file.isFile()) {
            logger.error("Erro ao executar: o endereco deve ser referente a um arquivo");
            return false;
        }

        return true;
    }
}
