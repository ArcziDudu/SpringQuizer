package com.quizer.api.controller.view;

import com.quizer.business.services.GameService;
import com.quizer.business.services.PlayerService;
import com.quizer.business.services.QuestionsService;
import com.quizer.domain.dto.GameDto;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.constants.QuizCategories;
import com.quizer.infrastructure.constants.QuizDifficulty;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuestionAnswer;
import com.quizer.infrastructure.model.QuestionForm;
import com.quizer.infrastructure.model.QuizQuestion;
import com.quizer.infrastructure.security.UserEntity;
import com.quizer.infrastructure.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class GameController {
    private final String CATEGORIES = "/categories";
    private final String CHOSEN_CATEGORY = "/categories/{category}";
    private final String CHOSEN_DIFFICULTY = "/categories/{category}/difficulty/{difficulty}";
    private final String SCOREBOARD = "/scoreboard";
    private final String MY_ACCOUNT = "/my-account";
    private final QuestionsService questionsService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final UserRepository userRepository;


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
        int categoryTranslateToIntForExternalApi = questionsService.chosenCategory(selectedCategory);

        Flux<ExternalApiResults> externalApiResultsFlux = questionsService
                .fetchQuestions(categoryTranslateToIntForExternalApi, selectedDifficulty);
        List<QuizQuestion> results = Objects.requireNonNull(externalApiResultsFlux.blockFirst()).results();

        List<QuestionAnswer> questionAnswers = gameService.getQuestionAnswers(results);

        QuestionForm questionForm = new QuestionForm();
        questionForm.setSelectedDifficulty(selectedDifficulty);
        questionForm.setSelectedCategory(questionsService
                .CategoryFromIntToString(categoryTranslateToIntForExternalApi));

        questionForm.setAnswers(questionAnswers);

        model.addAttribute("questions", results);
        model.addAttribute("selectedCategory", results.get(0).category());
        model.addAttribute("selectedDifficulty", selectedDifficulty);
        model.addAttribute("answer", questionForm);

        return "gameBoard";
    }

    @PostMapping("/game-result")
    public String handleFormSubmission(@ModelAttribute("answer") QuestionForm answer,
                                       Principal principal,
                                       Model model) {
        String userName = principal.getName();
        UserEntity currentUser = userRepository.findByUserName(userName);

        List<QuestionAnswer> answers = answer.getAnswers();
        int result = questionsService.amountOfPoints(answers);
        gameService.saveGameToDb(GameDto.builder()
                .player(playerService.findEntityByUserName(currentUser.getUserName()))
                .quizDifficulty(answer.getSelectedDifficulty())
                .quizCategory(answer.getSelectedCategory())
                .dateOfGame(OffsetDateTime.now())
                .points(result)

                .build());
        model.addAttribute("result", result);
        model.addAttribute("userAnswers", answers);
        return "gameResult";
    }

    @GetMapping(MY_ACCOUNT)
    public String handleMyAccount(Principal principal, Model model) {
        String userName = principal.getName();
        UserEntity currentUser = userRepository.findByUserName(userName);
        PlayerDto dtoByUserName = playerService.findDtoByUserName(currentUser.getUserName());

        TreeSet<GameDto> gamesSortedByDate = dtoByUserName.getGames()
                .stream()
                .sorted(getGameDtoComparator())
                .collect(Collectors.toCollection(TreeSet::new));

        int totalPoints = gamesSortedByDate.stream()
                .mapToInt(GameDto::getPoints)
                .sum();

        model.addAttribute("games", gamesSortedByDate);
        model.addAttribute("result", totalPoints);
        return "myAccount";
    }

    @GetMapping(SCOREBOARD)
    public String showScoreboard(Model model) {

        List<PlayerDto> allPlayers = playerService.findAllPlayers();

        for (PlayerDto player : allPlayers) {
            int totalPoints = 0;
            for (GameDto game : player.getGames()) {
                totalPoints += game.getPoints();
            }
            player.setTotalPoints(totalPoints);
        }


        List<PlayerDto> mutablePlayers = new ArrayList<>(allPlayers);
        mutablePlayers.sort((p1, p2) -> p2.getTotalPoints() - p1.getTotalPoints());
        model.addAttribute("allPlayers", mutablePlayers);
        return "scoreboard";
    }

    private Comparator<? super GameDto> getGameDtoComparator() {
        return Comparator.comparing(GameDto::getDateOfGame);
    }


}
