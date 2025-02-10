package com.pragma.tecnologia.domain.spi;

import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.model.EmailValidationResult;

public interface EmailValidatorGateway {

    Mono<EmailValidationResult> validateEmail(String email, String messageId);
}
