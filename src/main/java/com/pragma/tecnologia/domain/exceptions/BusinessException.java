package com.pragma.tecnologia.domain.exceptions;

import lombok.Getter;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;

@Getter
public class BusinessException extends ProcessorException {

    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage(), technicalMessage);
    }


}
