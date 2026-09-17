package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private final List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }
}