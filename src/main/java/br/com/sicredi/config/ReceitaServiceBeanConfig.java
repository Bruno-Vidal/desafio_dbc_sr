package br.com.sicredi.config;

import br.com.sicredi.service.ReceitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceitaServiceBeanConfig {

    @Bean
    public ReceitaService receitaService() {
        return new ReceitaService();
    }
}
