package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private PrintWriter log;

    @BeforeEach
    void setUp() {
        log = new PrintWriter(System.out);
    }

    @Test
    void constructor_shouldThrowExceptionWhenDictionaryIsEmpty() {
        WordleDictionary emptyDictionary = new WordleDictionary();

        try {
            new WordleGame(emptyDictionary, log);
            fail("Ожидалось исключение IllegalArgumentException, но оно не было выброшено");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void validateWord_shouldThrowExceptionForWrongLength() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        try {
            game.validateWord("дом");
            fail("Ожидалось исключение WordNotFoundInDictionaryException, но оно не было выброшено");
        } catch (WordNotFoundInDictionaryException e) {
        }
    }

    @Test
    void validateWord_shouldThrowExceptionWhenWordNotInDictionary() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        try {
            game.validateWord("мышка");
            fail("Ожидалось исключение WordNotFoundInDictionaryException, но оно не было выброшено");
        } catch (WordNotFoundInDictionaryException e) {
        }
    }

    @Test
    void validateWord_shouldNotThrowForCorrectWord() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        try {
            game.validateWord("кошка");
        } catch (WordNotFoundInDictionaryException e) {
            fail("Исключение не должно было быть выброшено для корректного слова");
        }
    }

    @Test
    void compareWithAnswer_shouldReturnAllPlusesForCorrectWord() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        String result = game.compareWithAnswer("кошка");

        assertEquals("+++++", result);
    }

    @Test
    void compareWithAnswer_shouldDecreaseStepsByOne() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        int stepsBefore = game.getSteps();
        game.compareWithAnswer("кошка");
        int stepsAfter = game.getSteps();

        assertEquals(stepsBefore - 1, stepsAfter);
    }

    @Test
    void compareWithAnswer_shouldReturnAllMinusesWhenNoLettersMatch() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        String result = game.compareWithAnswer("бивни");

        assertEquals("-----", result);
    }

    @Test
    void compareWithAnswer_shouldHandleRepeatedLettersCorrectly() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("сокол");
        WordleGame game = new WordleGame(dictionary, log);

        String result = game.compareWithAnswer("олово");

        assertEquals("^^^--", result);
    }

    @Test
    void isCorrectAnswer_shouldReturnTrueForMatchingWord() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        assertTrue(game.isCorrectAnswer("кошка"));
    }

    @Test
    void isCorrectAnswer_shouldReturnFalseForDifferentWord() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        assertFalse(game.isCorrectAnswer("мышка"));
    }

    @Test
    void isFinished_shouldReturnTrueWhenStepsReachZero() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        for (int i = 0; i < 6; i++) {
            game.compareWithAnswer("кошка");
        }

        assertTrue(game.isFinished());
    }

    @Test
    void isFinished_shouldReturnFalseWhenStepsRemain() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.getWords().add("кошка");
        WordleGame game = new WordleGame(dictionary, log);

        game.compareWithAnswer("кошка");

        assertFalse(game.isFinished());
    }
}