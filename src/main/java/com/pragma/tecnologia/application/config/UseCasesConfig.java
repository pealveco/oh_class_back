package com.pragma.tecnologia.application.config;

import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
import com.pragma.tecnologia.infrastructure.adapters.tecnologiaadapter.TecnologiaAdapter;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.pragma.tecnologia.domain.api.UserServicePort;
import com.pragma.tecnologia.domain.spi.EmailValidatorGateway;
import com.pragma.tecnologia.domain.spi.UserPersistencePort;
import com.pragma.tecnologia.domain.usecase.UserUseCase;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.UserPersistenceAdapter;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.UserEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
        private final UserRepository userRepository;
        private final UserEntityMapper userEntityMapper;
        private final TecnologiaRepository tecnologiaRepository;
        private final TecnologiaEntityMapper tecnologiaEntityMapper;

        @Bean
        public UserPersistencePort usersPersistencePort() {
                return new UserPersistenceAdapter(userRepository,userEntityMapper);
        }

        @Bean
        public UserServicePort usersServicePort(UserPersistencePort usersPersistencePort, EmailValidatorGateway emailValidatorGateway){
                return new UserUseCase(usersPersistencePort, emailValidatorGateway);
        }

        @Bean
        public TecnologiaPersistencePort tecnologiaPersistencePort() {
                return new TecnologiaAdapter(tecnologiaRepository, tecnologiaEntityMapper);
        }

        @Bean
        public TecnologiaUseCase tecnologiaUseCase(TecnologiaPersistencePort tecnologiaPersistencePort) {
                return new TecnologiaUseCase(tecnologiaPersistencePort, tecnologiaEntityMapper);
        }
}
