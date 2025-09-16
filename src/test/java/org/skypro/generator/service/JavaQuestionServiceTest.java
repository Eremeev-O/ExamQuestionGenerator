package org.skypro.generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.generator.domain.Question;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    private JavaQuestionService out;
    private Question q1, q2, q3;

    @BeforeEach
    public void setUp() {
        out = new JavaQuestionService();
        q1 = new Question("Q1", "A1");
        q2 = new Question("Q2", "A2");
        q3 = new Question("Q3", "A3");
    }

    @Test
    public void addQuestionShouldReturnAddedQuestion() {
        Question addedQuestion = out.add(q1);
        assertEquals(q1, addedQuestion);
        assertTrue(out.getAll().contains(q1));
        assertEquals(1, out.getAll().size());
    }

    @Test
    public void addQuestionWithTextAndAnswerShouldReturnAddedQuestion() {
        Question addedQuestion = out.add("Q1", "A1");
        assertEquals(q1, addedQuestion);
        assertTrue(out.getAll().contains(q1));
        assertEquals(1, out.getAll().size());
    }

    @Test
    public void addExistingQuestionShouldNotChangeSize() {
        out.add(q1);
        out.add(q1); // Добавляем тот же вопрос еще раз
        assertEquals(1, out.getAll().size());
    }

    @Test
    public void removeQuestionShouldReturnRemovedQuestionAndDecreaseSize() {
        out.add(q1);
        Question removedQuestion = out.remove(q1);
        assertEquals(q1, removedQuestion);
        assertFalse(out.getAll().contains(q1));
        assertEquals(0, out.getAll().size());
    }

    @Test
    public void removeNonExistentQuestionShouldThrowException() {
        out.add(q1);
        assertThrows(IllegalArgumentException.class, () -> out.remove(q2));
    }

    @Test
    public void getAllShouldReturnAllQuestions() {
        out.add(q1);
        out.add(q2);
        Collection<Question> allQuestions = out.getAll();
        assertEquals(2, allQuestions.size());
        assertTrue(allQuestions.contains(q1));
        assertTrue(allQuestions.contains(q2));
    }

    @Test
    public void getRandomQuestionShouldReturnOneOfTheExistingQuestions() {
        out.add(q1);
        out.add(q2);
        Question randomQuestion = out.getRandomQuestion();
        assertTrue(out.getAll().contains(randomQuestion));
    }

    @Test
    public void getRandomQuestionWhenEmptyShouldThrowException() {
        assertThrows(NoSuchElementException.class, () -> out.getRandomQuestion());
    }

    @Test
    public void getSizeShouldReturnCorrectSize() {
        assertEquals(0, out.getSize());
        out.add(q1);
        assertEquals(1, out.getSize());
        out.add(q2);
        assertEquals(2, out.getSize());
        out.remove(q1);
        assertEquals(1, out.getSize());
    }
}