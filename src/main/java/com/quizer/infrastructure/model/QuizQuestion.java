package com.quizer.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
public record QuizQuestion(String category,
                           String type,
                           String difficulty,
                           String question,
                           String correct_answer,
                           List<String> incorrect_answers) {
}
