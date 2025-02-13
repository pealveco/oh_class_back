package com.pragma.capacidad.domain.model;

public record EmailValidationResult(String deliverability, String quality_score) { }

