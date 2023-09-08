package com.quizer.api.controller;

import com.quizer.api.controller.view.GameController;
import com.quizer.business.services.QuestionsService;
import com.quizer.infrastructure.constants.QuizCategories;
import com.quizer.infrastructure.constants.QuizDifficulty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GameController.class)
class GameControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionsService questionsService;

    @Test
    void thatReturnCorrectCategoriesTemplate() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("quizCategories"))
                .andExpect(content().string(containsString(QuizCategories.BOOKS.getCategoryName())))
                .andExpect(content().string(containsString(QuizCategories.COMPUTERS.getCategoryName())))
                .andExpect(content().string(containsString(QuizCategories.FILMS.getCategoryName())))
                .andExpect(content().string(containsString(QuizCategories.MUSIC.getCategoryName())))
                .andExpect(content().string(containsString(QuizCategories.TELEVISION.getCategoryName())))
                .andExpect(content().string(containsString(QuizCategories.RANDOM.getCategoryName())))
                .andExpect(content().string(containsString("btn-category")));
    }

    @ParameterizedTest
    @EnumSource(QuizCategories.class)
    void thatReturnCorrectDifficultiesTemplateForCategory(QuizCategories category) throws Exception {
        String categoryPath = "/categories/" + category.name();
        mockMvc.perform(get(categoryPath))
                .andExpect(status().isOk())
                .andExpect(view().name("quizDifficulty"))
                .andExpect(content().string(containsString(QuizDifficulty.HARD.getDifficultyName())))
                .andExpect(content().string(containsString(QuizDifficulty.MEDIUM.getDifficultyName())))
                .andExpect(content().string(containsString(QuizDifficulty.EASY.getDifficultyName())))
                .andExpect(content().string(containsString("btn-category")));
    }


}