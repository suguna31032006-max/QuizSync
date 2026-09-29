package com.quizsync.quizsync;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizController(
            QuizRepository quizRepository,
            QuestionRepository questionRepository) {

        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public ResponseEntity<?> createQuiz(
            @RequestBody CreateQuizRequest request) {

        Quiz quiz = new Quiz();

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setCategory(request.getCategory());

        String code;

        do {
            code = UUID.randomUUID()
                    .toString()
                    .substring(0, 6)
                    .toUpperCase();

        } while (quizRepository.existsByQuizCode(code));

        quiz.setQuizCode(code);

        Quiz savedQuiz = quizRepository.save(quiz);

        if (request.getQuestions() != null) {

            for (QuestionRequest questionRequest : request.getQuestions()) {

                Question question = new Question();

                question.setQuestionText(
                        questionRequest.getQuestionText()
                );

                question.setOptionA(
                        questionRequest.getOptionA()
                );

                question.setOptionB(
                        questionRequest.getOptionB()
                );

                question.setOptionC(
                        questionRequest.getOptionC()
                );

                question.setOptionD(
                        questionRequest.getOptionD()
                );

                question.setCorrectAnswer(
                        questionRequest.getCorrectAnswer()
                );

                question.setQuiz(savedQuiz);

                questionRepository.save(question);
            }
        }

        return ResponseEntity.ok(savedQuiz);
    }


    public static class CreateQuizRequest {

        private String title;
        private String description;
        private String category;
        private List<QuestionRequest> questions;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<QuestionRequest> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionRequest> questions) {
            this.questions = questions;
        }
    }


    public static class QuestionRequest {

        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
    }
    @GetMapping("/code/{quizCode}")
    public ResponseEntity<?> getQuizByCode(@PathVariable String quizCode) {

        return quizRepository.findByQuizCode(quizCode.toUpperCase())
                .map(quiz -> ResponseEntity.ok(quiz))
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
    @GetMapping("/code/{quizCode}/questions")
    public ResponseEntity<?> getQuestionsByQuizCode(
            @PathVariable String quizCode) {

        return quizRepository.findByQuizCode(quizCode.toUpperCase())
                .map(quiz -> ResponseEntity.ok(
                        questionRepository.findByQuizId(quiz.getId())
                ))
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
}
