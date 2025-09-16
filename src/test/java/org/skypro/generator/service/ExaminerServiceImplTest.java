package org.skypro.generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.generator.domain.Question;
import org.skypro.generator.exception.BadRequestException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService; // Мокаем QuestionService

    @InjectMocks
    private ExaminerServiceImpl out; // Внедряем мок в ExaminerServiceImpl

    private Question q1, q2, q3, q4, q5;
    private Collection<Question> allQuestions;

    @BeforeEach
    public void setUp() {
        q1 = new Question("Q1", "A1");
        q2 = new Question("Q2", "A2");
        q3 = new Question("Q3", "A3");
        q4 = new Question("Q4", "A4");
        q5 = new Question("Q5", "A5");

        allQuestions = new HashSet<>(List.of(q1, q2, q3, q4, q5));
    }

    @Test
    public void getQuestionsShouldReturnCorrectAmountOfUniqueQuestions() {
        when(questionService.getAll()).thenReturn(allQuestions);
        when(questionService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2)
                .thenReturn(q3)
                .thenReturn(q1)
                .thenReturn(q4);

        int amount = 4;
        Collection<Question> result = out.getQuestions(amount);

        assertEquals(amount, result.size());
        assertTrue(result.contains(q1));
        assertTrue(result.contains(q2));
        assertTrue(result.contains(q3));
        assertTrue(result.contains(q4));
        assertFalse(result.contains(q5));
    }

    @Test
    public void getQuestionsShouldThrowExceptionWhenAmountIsMoreThanAvailable() {
        when(questionService.getAll()).thenReturn(allQuestions); // 5 вопросов доступно

        int amount = 6;

        assertThrows(BadRequestException.class, () -> out.getQuestions(amount));
    }

    @Test
    public void getQuestionsShouldThrowExceptionWhenAmountIsZeroOrNegative() {
        assertThrows(BadRequestException.class, () -> out.getQuestions(0));
        assertThrows(BadRequestException.class, () -> out.getQuestions(-1));
    }
}