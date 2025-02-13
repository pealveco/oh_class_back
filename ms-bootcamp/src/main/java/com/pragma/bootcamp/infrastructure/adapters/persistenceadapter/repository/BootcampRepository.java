package com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository;

    import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.entity.BootcampEntity;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.repository.reactive.ReactiveCrudRepository;
    import org.springframework.stereotype.Repository;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Mono;

@Repository
    public interface BootcampRepository extends ReactiveCrudRepository<BootcampEntity, Long> {
        Flux<BootcampEntity> findAllBy(Pageable pageable);

        Mono<Boolean> existsByName(String name);
    }