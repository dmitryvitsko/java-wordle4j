package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WordleDictionaryLoader {

    public WordleDictionary loadDictionary(String filePath) throws IOException {
        WordleDictionary wordleDictionary = new WordleDictionary();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 5) {
                    line = line.toLowerCase().replace("ё", "е");
                    wordleDictionary.getWords().add(line);
                }
            }
        }

        return wordleDictionary;
    }
}