package com.quizer.business.services;

import com.quizer.business.services.dao.GameDao;
import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.model.QuestionAnswer;
import com.quizer.infrastructure.model.QuizQuestion;
import com.quizer.infrastructure.repository.mapper.GameMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final GameMapper gameMapper;

    public Map<String, Integer> getPlayerStatsSorted(List<GameDto> allGames) {
        Map<String, Integer> playerPointsMap = new HashMap<>();

        for (GameDto game : allGames) {
            String playerName = game.getPlayer().getUserName();
            int points = game.getPoints();
            playerPointsMap.put(playerName, playerPointsMap.getOrDefault(playerName, 0) + points);
        }

        TreeMap<String, Integer> sortedPlayerPointsMap = new TreeMap<>((player1, player2) -> {
            int points1 = playerPointsMap.get(player1);
            int points2 = playerPointsMap.get(player2);
            return Integer.compare(points2, points1);
        });

        sortedPlayerPointsMap.putAll(playerPointsMap);

        return sortedPlayerPointsMap;
    }

    public void saveGameToDb(GameDto game) {
        gameDao.addGameToDatabase( gameMapper.mapFromDto(game));
    }

    public List<GameDto> fetchAllGames() {
       return gameDao.fetchAllGames();
    }

    public List<QuestionAnswer> getQuestionAnswers(List<QuizQuestion> results) {
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
