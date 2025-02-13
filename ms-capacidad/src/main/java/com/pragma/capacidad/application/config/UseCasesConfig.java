package com.pragma.capacidad.application.config;

import com.pragma.capacidad.domain.spi.CapacidadPersistencePort;
import com.pragma.capacidad.domain.usecase.CapacidadUseCase;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.CapacidadPersistenceAdapter;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

        private final CapacidadRepository capacidadRepository;
        private final CapacidadEntityMapper capacidadEntityMapper;

        @Bean
        public CapacidadPersistencePort capacidadPersistencePort() {
                return new CapacidadPersistenceAdapter(capacidadRepository, capacidadEntityMapper);
        }

        @Bean
        public CapacidadUseCase capacidadUseCase(CapacidadPersistencePort capacidadPersistencePort) {
                return new CapacidadUseCase(capacidadPersistencePort, capacidadRepository, capacidadEntityMapper);
        }
}
