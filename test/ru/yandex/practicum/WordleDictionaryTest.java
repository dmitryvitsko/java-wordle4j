package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary();
    }

    @Test
    void newDictionary_shouldBeEmpty() {
        assertTrue(dictionary.getWords().isEmpty());
    }

    @Test
    void contains_shouldReturnTrueForAddedWord() {
        dictionary.getWords().add("кошка");
        assertTrue(dictionary.contains("кошка"));
    }

    @Test
    void contains_shouldReturnFalseForWordNotAdded() {
        dictionary.getWords().add("кошка");
        assertFalse(dictionary.contains("мышка"));
    }
}