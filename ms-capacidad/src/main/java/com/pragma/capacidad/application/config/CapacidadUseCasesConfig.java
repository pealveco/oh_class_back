package com.pragma.capacidad.application.config;

import com.pragma.capacidad.domain.spi.CapacidadPersistencePort;
import com.pragma.capacidad.domain.usecase.CapacidadUseCase;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.CapacidadPersistenceAdapter;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.TecnologiaPersistenceAdapter;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class CapacidadUseCasesConfig {

    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;

    private final TecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;

    private final WebClient.Builder webClientBuilder;

    @Bean(name = "uniqueCapacidadUseCasesConfig")
    public CapacidadPersistencePort capacidadPersistencePort() {
        return new CapacidadPersistenceAdapter(capacidadRepository, capacidadEntityMapper);
    }

    @Bean
    public CapacidadUseCase capacidadUseCase(CapacidadPersistencePort capacidadPersistencePort) {
        return new CapacidadUseCase(capacidadPersistencePort, capacidadRepository, capacidadEntityMapper, webClientBuilder);
    }

    @Bean
    public TecnologiaPersistencePort tecnologiaPersistencePort() {
        return new TecnologiaPersistenceAdapter(tecnologiaRepository, tecnologiaEntityMapper);
    }
}