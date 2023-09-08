package com.quizer.infrastructure.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionForm {
    List<QuestionAnswer> answers;
}
