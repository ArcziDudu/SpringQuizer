package com.quizer.business.services;

import com.quizer.business.services.dao.GameDao;
import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;
import com.quizer.infrastructure.model.QuestionAnswer;
import com.quizer.infrastructure.model.QuizQuestion;
import com.quizer.infrastructure.repository.mapper.GameMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final GameMapper gameMapper;

    public void saveGameToDb(GameDto game) {
        gameDao.addGameToDatabase( gameMapper.mapFromDto(game));
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
