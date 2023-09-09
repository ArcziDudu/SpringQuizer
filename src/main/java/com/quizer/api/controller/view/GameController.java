package com.quizer.api.controller.view;

import com.quizer.business.services.GameService;
import com.quizer.business.services.PlayerService;
import com.quizer.business.services.QuestionsService;
import com.quizer.domain.dto.GameDto;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.constants.QuizCategories;
import com.quizer.infrastructure.constants.QuizDifficulty;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.entity.PlayerEntity;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuestionAnswer;
import com.quizer.infrastructure.model.QuestionForm;
import com.quizer.infrastructure.model.QuizQuestion;
import com.quizer.infrastructure.repository.jpaImpl.PlayerJpaImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.model.IModel;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.*;

@Controller
@AllArgsConstructor
public class GameController {
    private final String CATEGORIES = "/categories";
    private final String CHOSEN_CATEGORY = "/categories/{category}";
    private final String CHOSEN_DIFFICULTY = "/categories/{category}/difficulty/{difficulty}";
    private final String MY_ACCOUNT = "/my-account/{username}";
    private final QuestionsService questionsService;
    private final GameService gameService;
    private final PlayerService playerService;
    private PlayerJpaImpl playerJpa;

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

        List<QuestionAnswer> questionAnswers = getQuestionAnswers(results);

        QuestionForm questionForm = new QuestionForm();
        questionForm.setSelectedDifficulty(selectedDifficulty);
        questionForm.setSelectedCategory(selectedCategory);
        questionForm.setAnswers(questionAnswers);

        model.addAttribute("questions", results);
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("selectedDifficulty", selectedDifficulty);
        model.addAttribute("answer", questionForm);

        return "gameBoard";
    }

    @PostMapping("/game-result")
    public String handleFormSubmission(@ModelAttribute("answer") QuestionForm answer, Model model) {


        List<QuestionAnswer> answers = answer.getAnswers();
        int result = questionsService.amountOfPoints(answers);
        gameService.saveGameToDb(GameDto.builder()
                .dateOfGame(OffsetDateTime.now())
                .points(result)
                .quizDifficulty(answer.getSelectedDifficulty())
                .quizCategory(answer.getSelectedCategory())
                        .player(playerJpa.get(1))
                .build());
        model.addAttribute("result", result);
        model.addAttribute("userAnswers", answers);
        return "temp2";
    }
    @GetMapping(MY_ACCOUNT)
    public String handleMyAccount(@PathVariable String userName, Model model) {
        PlayerDto player = playerService.findPlayer(userName);
        Set<GameEntity> games = player.getGames();
        model.addAttribute("games", games);

        return "gameBoard";
    }

    private static List<QuestionAnswer> getQuestionAnswers(List<QuizQuestion> results) {
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
