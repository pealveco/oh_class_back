package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.EmailValidationResult;
import reactor.core.publisher.Mono;

public interface EmailValidatorGateway {

    Mono<EmailValidationResult> validateEmail(String email, String messageId);
}
