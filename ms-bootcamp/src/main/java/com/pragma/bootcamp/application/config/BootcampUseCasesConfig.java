package com.pragma.bootcamp.application.config;

    import com.pragma.bootcamp.domain.spi.BootcampPersistencePort;
    import com.pragma.bootcamp.domain.usecase.BootcampUseCase;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.BootcampPersistenceAdapter;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper.BootcampEntityMapper;
    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository.BootcampRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    @RequiredArgsConstructor
    public class BootcampUseCasesConfig {

        private final BootcampRepository bootcampRepository;
        private final BootcampEntityMapper bootcampEntityMapper;

        @Bean
        public BootcampPersistencePort bootcampPersistencePort() {
            return new BootcampPersistenceAdapter(bootcampRepository, bootcampEntityMapper);
        }

        @Bean
        public BootcampUseCase bootcampUseCase(BootcampPersistencePort bootcampPersistencePort) {
            return new BootcampUseCase(bootcampPersistencePort, bootcampRepository, bootcampEntityMapper);
        }
    }