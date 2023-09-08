package com.quizer.api.controller.view;

import com.quizer.business.services.QuestionsService;
import com.quizer.infrastructure.constants.QuizCategories;
import com.quizer.infrastructure.constants.QuizDifficulty;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuestionAnswer;
import com.quizer.infrastructure.model.QuestionForm;
import com.quizer.infrastructure.model.QuizQuestion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

import java.util.*;

@Controller
@AllArgsConstructor
public class GameController {
    private final String CATEGORIES = "/categories";
    private final String CHOSEN_CATEGORY = "/categories/{category}";
    private final String CHOSEN_DIFFICULTY = "/categories/{category}/difficulty/{difficulty}";
    private final QuestionsService questionsService;

    @GetMapping(CATEGORIES)
    public String showQuizGameCategories(Model model) {
        model.addAttribute("categories", QuizCategories.values());
        return "quizCategories";
    }

    @GetMapping(CHOSEN_CATEGORY)
    public String showQuizGameDifficulties(
            @PathVariable("category") String selectedCategory,
            Model model) {

        model.addAttribute("difficulties", QuizDifficulty.values());
        model.addAttribute("selectedCategory", selectedCategory);
        return "quizDifficulty";
    }

    @GetMapping(CHOSEN_DIFFICULTY)
    public String handleCategoryAndDifficultySelection(
            @PathVariable("category") String selectedCategory,
            @PathVariable("difficulty") String selectedDifficulty,
            Model model) {

        Flux<ExternalApiResults> externalApiResultsFlux = questionsService.fetchQuestions(selectedCategory, selectedDifficulty);
        List<QuizQuestion> results = Objects.requireNonNull(externalApiResultsFlux.blockFirst()).results();

        List<QuestionAnswer> questionAnswers = getQuestionAnswers(results, selectedDifficulty);

        QuestionForm questionForm = new QuestionForm();
        questionForm.setAnswers(questionAnswers);

        model.addAttribute("questions", results);
        model.addAttribute("selectedDifficulty", selectedDifficulty);
        model.addAttribute("answer", questionForm);

        return "gameBoard";
    }

    @PostMapping("/game-result")
    public String handleFormSubmission(@ModelAttribute("answer") QuestionForm answer, Model model) {

        List<QuestionAnswer> answers = answer.getAnswers();
        int result = questionsService.amountOfPoints(answers);
        model.addAttribute("result", result);
        model.addAttribute("userAnswers", answers);
        return "temp2";
    }

    private static List<QuestionAnswer> getQuestionAnswers(List<QuizQuestion> results, String selectedDifficulty) {
        List<QuestionAnswer> questionAnswers = new ArrayList<>();

        for (QuizQuestion question : results) {
            QuestionAnswer answer = new QuestionAnswer();

            answer.setQuestionq(question.question());

            answer.setCorrectAnswer(question.correct_answer());

            Set<String> possibleAnswers = new HashSet<>();

            possibleAnswers.add(question.correct_answer());
            possibleAnswers.addAll(question.incorrect_answers());

            answer.setPossibleAnswers(possibleAnswers);
            questionAnswers.add(answer);
        }
        return questionAnswers;
    }

}
