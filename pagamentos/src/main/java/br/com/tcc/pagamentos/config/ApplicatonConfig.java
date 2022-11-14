package br.com.tcc.pagamentos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ApplicatonConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
