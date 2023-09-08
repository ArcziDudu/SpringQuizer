package com.quizer.infrastructure.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuizDifficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");


    private final String difficultyName;

    public String getDifficultyName() {
        return difficultyName;
    }
}
