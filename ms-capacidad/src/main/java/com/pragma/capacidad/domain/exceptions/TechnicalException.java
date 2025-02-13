package com.pragma.capacidad.domain.exceptions;

import com.pragma.capacidad.domain.enums.TechnicalMessage;
import com.pragma.capacidad.domain.exceptions.ProcessorException;
import lombok.Getter;

@Getter
public class TechnicalException extends ProcessorException {

    public TechnicalException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause, technicalMessage);
    }

    public TechnicalException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage(), technicalMessage);
    }
}