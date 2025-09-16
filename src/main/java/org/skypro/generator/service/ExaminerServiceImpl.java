package org.skypro.generator.service;

import org.skypro.generator.domain.Question;
import org.skypro.generator.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new BadRequestException("Число вопросов должно быть положительным.");
        }
        if (amount > questionService.getAll().size()) {
            throw new BadRequestException("Запрошенное количество вопросов больше, чем доступно.");
        }

        Set<Question> examQuestions = new HashSet<>();
        while (examQuestions.size() < amount) {
            examQuestions.add(questionService.getRandomQuestion());
        }
        return examQuestions;
    }
}