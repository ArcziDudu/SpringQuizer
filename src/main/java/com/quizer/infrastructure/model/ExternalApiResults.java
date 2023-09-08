package com.quizer.infrastructure.model;

import lombok.Builder;

import java.util.List;
@Builder
public record ExternalApiResults(List<QuizQuestion> results) {
}
