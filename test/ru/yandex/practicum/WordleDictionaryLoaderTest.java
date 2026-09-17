package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {
    @Test
    void loadDictionary_shouldKeepOnlyFiveLetterWords() throws IOException {
        String testFilePath = "test_dictionary_1.txt";
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("кошка\n");
        writer.write("дом\n");
        writer.write("велосипед\n");
        writer.close();

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadDictionary(testFilePath);

        List<String> words = dictionary.getWords();

        assertEquals(1, words.size());
        assertTrue(words.contains("кошка"));

        File testFile = new File(testFilePath);
        testFile.delete();
    }

    @Test
    void loadDictionary_shouldNormalizeCaseAndReplace() throws IOException {
        String testFilePath = "test_dictionary_2.txt";
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("ЁЖИКИ\n");
        writer.close();

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.loadDictionary(testFilePath);

        List<String> words = dictionary.getWords();

        assertEquals(1, words.size());
        assertEquals("ежики", words.get(0));

        File testFile = new File(testFilePath);
        testFile.delete();
    }

    @Test
    void loadDictionary_shouldThrowExceptionWhenFileNotFound() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();

        try {
            loader.loadDictionary("несуществующий_файл.txt");
            fail("Ожидалось исключение IOException, но оно не было выброшено");
        } catch (IOException e) {
        }
    }
}