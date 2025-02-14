package com.pragma.bootcamp.application.config;

    import com.pragma.bootcamp.domain.spi.BootcampPersistencePort;
    import com.pragma.bootcamp.domain.usecase.BootcampUseCase;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.BootcampPersistenceAdapter;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper.BootcampEntityMapper;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository.BootcampRepository;
    import com.pragma.capacidad.domain.spi.CapacidadPersistencePort;
    import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.CapacidadPersistenceAdapter;
    import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
    import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.reactive.function.client.WebClient;

@Configuration
    @RequiredArgsConstructor
    public class BootcampUseCasesConfig {

        private final BootcampRepository bootcampRepository;
        private final BootcampEntityMapper bootcampEntityMapper;

        private final CapacidadRepository capacidadRepository;
        private final CapacidadEntityMapper capacidadEntityMapper;
        private final WebClient.Builder webClientBuilder;

        @Bean
        public BootcampPersistencePort bootcampPersistencePort() {
            return new BootcampPersistenceAdapter(bootcampRepository, bootcampEntityMapper);
        }

        @Bean
        public BootcampUseCase bootcampUseCase(BootcampPersistencePort bootcampPersistencePort) {
            return new BootcampUseCase(bootcampPersistencePort, bootcampRepository, bootcampEntityMapper, webClientBuilder);
        }

        @Bean
        public CapacidadPersistencePort capacidadPersistencePort() {
            return new CapacidadPersistenceAdapter(capacidadRepository, capacidadEntityMapper);
        }
    }