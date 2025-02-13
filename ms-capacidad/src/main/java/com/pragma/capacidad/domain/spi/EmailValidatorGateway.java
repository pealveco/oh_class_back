package com.pragma.capacidad.domain.spi;

import com.pragma.capacidad.domain.model.EmailValidationResult;
import reactor.core.publisher.Mono;

public interface EmailValidatorGateway {

    Mono<EmailValidationResult> validateEmail(String email, String messageId);
}
