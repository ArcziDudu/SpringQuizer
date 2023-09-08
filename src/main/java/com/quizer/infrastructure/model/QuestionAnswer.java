package com.quizer.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class QuestionAnswer {
    private String questionq;
    private String yourAnswer;
    private String correctAnswer;
    private Set<String> possibleAnswers = new HashSet<>();

}
