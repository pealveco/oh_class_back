package com.pragma.tecnologia.domain.usecase;

import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.api.UserServicePort;
import com.pragma.tecnologia.domain.constants.Constants;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;
import com.pragma.tecnologia.domain.exceptions.BusinessException;
import com.pragma.tecnologia.domain.model.EmailValidationResult;
import com.pragma.tecnologia.domain.model.User;
import com.pragma.tecnologia.domain.spi.EmailValidatorGateway;
import com.pragma.tecnologia.domain.spi.UserPersistencePort;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final EmailValidatorGateway validatorGateway;

    public UserUseCase(UserPersistencePort userPersistencePort, EmailValidatorGateway validatorGateway) {
        this.userPersistencePort = userPersistencePort;
        this.validatorGateway = validatorGateway;
    }

    @Override
    public Mono<User> registerUser(User user, String messageId) {
        return userPersistencePort.existByEmail(user.email())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.USER_ALREADY_EXISTS)))
                .flatMap(exists -> validateEmail(user.email(), messageId))
                .flatMap(validationResult -> validationResult.deliverability().equals(Constants.DELIVERABLE)
                        ? userPersistencePort.save(user)
                        : Mono.error(new BusinessException(TechnicalMessage.INVALID_EMAIL)))
                ;
    }

    private Mono<EmailValidationResult> validateEmail(String email, String messageId) {
        return validatorGateway.validateEmail(email, messageId)
                .filter(validationResult -> validationResult.deliverability().equals(Constants.DELIVERABLE))
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_EMAIL)));
    }

}
