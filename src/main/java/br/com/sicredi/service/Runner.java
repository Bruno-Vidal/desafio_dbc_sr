package br.com.sicredi.service;

import br.com.sicredi.model.DadoBancario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "receita.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);


    public Runner(Validador validador, DadoBancarioReaderService dadoBancarioReaderService, ReceitaService receitaService, DadoBancarioWriterService dadoBancarioWriterService) {
        this.validador = validador;
        this.dadoBancarioReaderService = dadoBancarioReaderService;
        this.receitaService = receitaService;
        this.dadoBancarioWriterService = dadoBancarioWriterService;
    }

    private final Validador validador;
    private final DadoBancarioReaderService dadoBancarioReaderService;
    private final ReceitaService receitaService;
    private final DadoBancarioWriterService dadoBancarioWriterService;

    @Override
    public void run(String... args) throws Exception {

        logger.info("validando arquivo de entrada");
        if( !validador.validar(args)) {
            throw new RuntimeException("parametros de entrada invalido !!!");
        }
        String path = args[0];

        logger.info("extraindos dados do arquivo: {}", path);
        List<DadoBancario> dadosBancario = dadoBancarioReaderService.read(path);

        logger.info("iniciando processamento em lote de atualizacao de dados na Receita Federal.");
        dadosBancario = processBatch(dadosBancario);

        logger.info("Montando arquivo de saida.");
        String resultPath = dadoBancarioWriterService.writer(dadosBancario, path);

        logger.info("Processo realizado com sucesso: \n Arquivo de resultado: {}", resultPath);

    }

    private List<DadoBancario> processBatch(List<DadoBancario> dadosBancario) {
        return dadosBancario
                .stream()
                .peek(dadoBancario -> {
                    boolean processado = atualizarReceita(dadoBancario);
                    dadoBancario.setProcessado(processado);
                }).collect(Collectors.toList());
    }

    private boolean atualizarReceita(DadoBancario dadoBancario) {
        boolean processado;
        try {
            processado = receitaService.atualizarConta(
                    dadoBancario.getAgencia(),
                    dadoBancario.getConta(),
                    dadoBancario.getSaldo(),
                    dadoBancario.getStatus().name()
            );
        } catch (InterruptedException | RuntimeException e) {
            logger.error("Erro de comunicacao com a Receita. \nagencia: {}\nconta: {}", dadoBancario.getAgencia(), dadoBancario.getConta());
            processado = false;
        }

        return processado;
    }


}

