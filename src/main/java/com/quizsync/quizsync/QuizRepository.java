package com.quizsync.quizsync;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByQuizCode(String quizCode);

    boolean existsByQuizCode(String quizCode);
}
