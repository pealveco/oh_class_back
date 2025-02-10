package com.pragma.tecnologia.domain.usecase;

    import com.pragma.tecnologia.domain.enums.TechnicalMessage;
    import com.pragma.tecnologia.domain.exceptions.BusinessException;
    import com.pragma.tecnologia.domain.model.Tecnologia;
    import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.Mockito;
    import reactor.core.publisher.Mono;
    import reactor.test.StepVerifier;

    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.Mockito.when;

    public class TecnologiaUseCaseTest {

        private TecnologiaPersistencePort tecnologiaPersistencePort;
        private TecnologiaUseCase tecnologiaUseCase;

        @BeforeEach
        public void setUp() {
            tecnologiaPersistencePort = Mockito.mock(TecnologiaPersistencePort.class);
            tecnologiaUseCase = new TecnologiaUseCase(tecnologiaPersistencePort);
        }

        @Test
        public void testRegisterTecnologia() {
            Tecnologia tecnologia = new Tecnologia(null, "Java", "Lenguaje de programación");

            when(tecnologiaPersistencePort.existByName(anyString())).thenReturn(Mono.just(false));
            when(tecnologiaPersistencePort.save(tecnologia)).thenReturn(Mono.just(tecnologia));

            StepVerifier.create(tecnologiaUseCase.registerTecnologia(tecnologia))
                    .expectNext(tecnologia)
                    .verifyComplete();
        }

        @Test
        public void testRegisterTecnologiaAlreadyExists() {
            Tecnologia tecnologia = new Tecnologia(null, "Java", "Lenguaje de programación");

            when(tecnologiaPersistencePort.existByName(anyString())).thenReturn(Mono.just(true));

            StepVerifier.create(tecnologiaUseCase.registerTecnologia(tecnologia))
                    .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                            ((BusinessException) throwable).getTechnicalMessage().equals(TechnicalMessage.TECNOLOGIA_ALREADY_EXISTS))
                    .verify();
        }
    }