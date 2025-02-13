package com.pragma.tecnologia.infrastructure.adapters.emailvalidatoradapter.dto;

public record EmailValidationResponse(String deliverability, String quality_score) {
}
