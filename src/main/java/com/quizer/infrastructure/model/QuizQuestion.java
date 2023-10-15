package com.quizer.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
public record QuizQuestion( @JsonProperty("category") String category,
                            @JsonProperty("type") String type,
                            @JsonProperty("difficulty") String difficulty,
                            @JsonProperty("question") String question,
                            @JsonProperty("correct_answer") String correct_answer,
                            @JsonProperty("incorrect_answers") List<String> incorrect_answers) {
}
