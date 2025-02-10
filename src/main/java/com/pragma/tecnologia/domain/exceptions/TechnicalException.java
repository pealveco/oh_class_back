package com.pragma.tecnologia.domain.exceptions;

import lombok.Getter;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;

@Getter
public class TechnicalException extends ProcessorException {

    public TechnicalException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause, technicalMessage);
    }

    public TechnicalException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage(), technicalMessage);
    }
}