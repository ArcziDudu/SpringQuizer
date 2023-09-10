package com.quizer.business.services;

import com.quizer.business.services.dao.QuestionDao;
import com.quizer.infrastructure.model.ExternalApiResults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.quizer.business.services.Fixtures.QuestionFixtures.question1;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class QuestionsServiceTest {

    @Mock
    private QuestionDao questionDao;
    @InjectMocks
    private QuestionsService questionsService;

    @Test
    void thatReturnTypeCorrect() {

        given(questionDao.getResultsFromExternalApi(anyInt(), anyInt(), anyString(), anyString()))
                .willReturn(Flux.just(question1()));

        //when
        Flux<ExternalApiResults> externalApiResults = questionsService.fetchQuestions(10, "easy");
        //then
        StepVerifier.create(externalApiResults)
                .consumeNextWith(result -> {
                    Assertions.assertEquals(1, result.results().size());
                    boolean allEasy = result.results().stream()
                            .allMatch(quizQuestion -> "easy".equals(quizQuestion.difficulty()));
                    boolean allBooks = result.results().stream()
                            .allMatch(quizQuestion -> "Entertainment: Books".equals(quizQuestion.category()));
                    boolean allType = result.results().stream()
                            .allMatch(quizQuestion -> "boolean".equals(quizQuestion.type()));
                    Assertions.assertTrue(allEasy);
                    Assertions.assertTrue(allBooks);
                    Assertions.assertTrue(allType);
                })
                .expectComplete()
                .verify();

    }
}