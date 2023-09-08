package com.quizer.infrastructure.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuizCategories {
    RANDOM("random"),
    BOOKS("books"),
    FILMS("films"),
    MUSIC("music"),
    TELEVISION("television"),
    COMPUTERS("computers");

    private final String categoryName;


    public String getCategoryName() {
        return categoryName;
    }
}
