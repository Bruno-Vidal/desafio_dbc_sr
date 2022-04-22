/*
Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.


Formato CSV:
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
...

*/
package br.com.sicredi;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class SincronizacaoReceita implements CommandLineRunner {


    public static void main(String[] args) {

        // Exemplo como chamar o "serviço" do Banco Central.
        // ReceitaService receitaService = new ReceitaService();
        // receitaService.atualizarConta("0101", "123456", 100.50, "A");

        SpringApplication app = new SpringApplication(SincronizacaoReceita.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {

        ReceitaService receitaService = new ReceitaService();
        
        for (String arg : args) {
            System.out.println(arg);
            BufferedReader reader = new BufferedReader(new FileReader(arg));
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

            BufferedWriter writer = new BufferedWriter(new FileWriter(arg.replaceAll("\\.csv","")+"_result.csv"));
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
