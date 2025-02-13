package com.pragma.tecnologia.application.config;

import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.TecnologiaPersistenceAdapter;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TecnologiaUseCasesConfig {

        private final TecnologiaRepository tecnologiaRepository;
        private final TecnologiaEntityMapper tecnologiaEntityMapper;


        @Bean(name = "uniqueTecnologiaUseCasesConfig")
        public TecnologiaPersistencePort tecnologiaPersistencePort() {
                return new TecnologiaPersistenceAdapter(tecnologiaRepository, tecnologiaEntityMapper);
        }

        @Bean
        public TecnologiaUseCase tecnologiaUseCase(TecnologiaPersistencePort tecnologiaPersistencePort) {
                return new TecnologiaUseCase(tecnologiaPersistencePort);
        }
}
