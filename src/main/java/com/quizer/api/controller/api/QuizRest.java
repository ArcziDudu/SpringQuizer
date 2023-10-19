package com.quizer.api.controller.api;


import com.quizer.business.services.GameService;
import com.quizer.business.services.PlayerService;
import com.quizer.business.services.QuestionsService;
import com.quizer.domain.dto.GameDto;
import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuizQuestion;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(QuizRest.API)
@AllArgsConstructor
public class QuizRest {
    public static final String API = "/api";
    public static final String API_CATEGORY_LEVEL = "/{category}/{selectedDifficulty}";
    public static final String API_CATEGORY_SCOREBOARD = "/scoreboard";
    public static final String API_CATEGORY_GAME_INFO = "/game-info/{userName}";
    private final QuestionsService questionsService;
    private final PlayerService playerService;

    @GetMapping(value = API_CATEGORY_LEVEL)
    public ResponseEntity<List<QuizQuestion>> fetchQuestionsByCategoryAndLevel(
            @PathVariable String category,
            @PathVariable String selectedDifficulty){
        int categoryTranslateToIntForExternalApi = questionsService.chosenCategory(category);
        Flux<ExternalApiResults> externalApiResultsFlux = questionsService
                .fetchQuestions(categoryTranslateToIntForExternalApi, selectedDifficulty);
        List<QuizQuestion> results = Objects.requireNonNull(externalApiResultsFlux.blockFirst()).results();
        return ResponseEntity.ok(results);
    }
    @GetMapping(value = API_CATEGORY_SCOREBOARD)
    public ResponseEntity<List<PlayerDto>> fetchScoreboard(){
        List<PlayerDto> allPlayers = playerService.findAllPlayers();
        List<PlayerDto> scoreboard = new ArrayList<>();
        for (PlayerDto player : allPlayers) {
            int totalPoints = 0;
            for (GameDto game : player.getGames()) {
                totalPoints += game.getPoints();
            }
            player.setTotalPoints(totalPoints);
            scoreboard.add(PlayerDto.builder()
                            .userName(player.getUserName())
                            .userEmail(player.getUserEmail())
                            .amountOfPlayedGames(player.getGames().size())
                            .totalPoints(player.getTotalPoints())
                    .build());
        }
        scoreboard.sort((p1, p2) -> p2.getTotalPoints() - p1.getTotalPoints());
        return ResponseEntity.ok(scoreboard);
    }

    @GetMapping(value = API_CATEGORY_GAME_INFO)
    public ResponseEntity<PlayerDto> fetchPlayerInfoByUserName(@PathVariable String userName){
        PlayerDto dtoByUserName = playerService.findDtoByUserName(userName);
        PlayerDto playerInfo = PlayerDto.builder()
                .playerId(dtoByUserName.getPlayerId())
                .userName(dtoByUserName.getUserName())
                .userEmail(dtoByUserName.getUserEmail())
                .games(dtoByUserName.getGames().stream().map(a ->
                        GameDto.builder()
                                .gameId(a.getGameId())
                                .dateOfGame(a.getDateOfGame())
                                .quizDifficulty(a.getQuizDifficulty())
                                .quizCategory(a.getQuizCategory())
                                .points(a.getPoints())
                                .build()).collect(Collectors.toSet())
                )
                .build();
        return ResponseEntity.ok(playerInfo);
    }
}
