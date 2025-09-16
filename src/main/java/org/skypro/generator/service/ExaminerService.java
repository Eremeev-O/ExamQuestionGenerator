package org.skypro.generator.service;

import org.skypro.generator.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);

}
