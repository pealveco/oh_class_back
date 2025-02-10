package com.pragma.tecnologia.domain.model;

public record EmailValidationResult(String deliverability, String quality_score) { }

