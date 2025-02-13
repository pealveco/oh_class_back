package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.TecnologiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TecnologiaPersistenceAdapterTest {

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @Mock
    private TecnologiaEntityMapper tecnologiaEntityMapper;

    @InjectMocks
    private TecnologiaPersistenceAdapter tecnologiaPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        when(tecnologiaEntityMapper.toEntity(any(Tecnologia.class))).thenReturn(new TecnologiaEntity());
        when(tecnologiaEntityMapper.toModel(any(TecnologiaEntity.class))).thenReturn(tecnologia);
        when(tecnologiaRepository.save(any(TecnologiaEntity.class))).thenReturn(Mono.just(new TecnologiaEntity()));

        StepVerifier.create(tecnologiaPersistenceAdapter.save(tecnologia))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void findAll() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        when(tecnologiaEntityMapper.toModel(any(TecnologiaEntity.class))).thenReturn(tecnologia);
        when(tecnologiaRepository.findAll()).thenReturn(Flux.just(new TecnologiaEntity()));

        StepVerifier.create(tecnologiaPersistenceAdapter.findAll())
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        Pageable pageable = PageRequest.of(0, 10);
        when(tecnologiaEntityMapper.toModel(any(TecnologiaEntity.class))).thenReturn(tecnologia);
        when(tecnologiaRepository.findAllBy(pageable)).thenReturn(Flux.just(new TecnologiaEntity()));

        StepVerifier.create(tecnologiaPersistenceAdapter.findAll(pageable))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void findById() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        when(tecnologiaEntityMapper.toModel(any(TecnologiaEntity.class))).thenReturn(tecnologia);
        when(tecnologiaRepository.findById(any(Long.class))).thenReturn(Mono.just(new TecnologiaEntity()));

        StepVerifier.create(tecnologiaPersistenceAdapter.findById(1L))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void deleteById() {
        when(tecnologiaRepository.deleteById(any(Long.class))).thenReturn(Mono.empty());

        StepVerifier.create(tecnologiaPersistenceAdapter.deleteById(1L))
                .verifyComplete();
    }

    @Test
    void existByName() {
        when(tecnologiaRepository.findByName(any(String.class))).thenReturn(Flux.just(new TecnologiaEntity()));

        StepVerifier.create(tecnologiaPersistenceAdapter.existByName("test"))
                .expectNext(true)
                .verifyComplete();
    }
}