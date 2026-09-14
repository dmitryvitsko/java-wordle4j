package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordleGame {

    private static final char EXACT_MATCH = '+';
    private static final char PARTIAL_MATCH = '^';
    private static final char NO_MATCH = '-';
    private static final int MAX_STEPS = 6;

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private PrintWriter log;

    private Set<Character> excludedLetters = new HashSet<>();
    private Map<Integer, Character> knownPositions = new HashMap<>();
    private Map<Character, Set<Integer>> knownLettersWrongPositions = new HashMap<>();
    private Set<String> usedHints = new HashSet<>();

    public WordleGame(WordleDictionary wordleDictionary, PrintWriter log) {
        if (wordleDictionary.getWords().isEmpty()) {
            throw new IllegalArgumentException("Словарь пуст, играть не во что");
        }
        this.dictionary = wordleDictionary;
        this.log = log;
        this.steps = MAX_STEPS;

        Random random = new Random();
        int randomIndex = random.nextInt(this.dictionary.getWords().size());
        this.answer = this.dictionary.getWords().get(randomIndex);

        log.println("Загадано слово: " + answer);
    }

    public void validateWord(String word) throws WordNotFoundInDictionaryException {
        if (word.length() != 5 || !dictionary.contains(word)) {
            throw new WordNotFoundInDictionaryException("Слово не найдено в словаре: " + word);
        }
    }

    public boolean isFinished() {
        return steps <= 0;
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public String compareWithAnswer(String word) {
        steps = steps - 1;

        char[] result = new char[5];

        Map<Character, Integer> remaining = new HashMap<>();
        for (int i = 0; i < answer.length(); i++) {
            char c = answer.charAt(i);
            if (remaining.containsKey(c)) {
                int count = remaining.get(c);
                remaining.put(c, count + 1);
            } else {
                remaining.put(c, 1);
            }
        }

        for (int i = 0; i < 5; i++) {
            char c = word.charAt(i);
            if (c == answer.charAt(i)) {
                result[i] = EXACT_MATCH;
                int count = remaining.get(c);
                remaining.put(c, count - 1);
                knownPositions.put(i, c);
            }
        }

        for (int i = 0; i < 5; i++) {
            char c = word.charAt(i);

            if (result[i] == EXACT_MATCH) {
                continue;
            }

            int leftCount = 0;
            if (remaining.containsKey(c)) {
                leftCount = remaining.get(c);
            }

            if (leftCount > 0) {
                result[i] = PARTIAL_MATCH;
                remaining.put(c, leftCount - 1);

                if (!knownLettersWrongPositions.containsKey(c)) {
                    knownLettersWrongPositions.put(c, new HashSet<>());
                }
                knownLettersWrongPositions.get(c).add(i);
            } else {
                result[i] = NO_MATCH;
                if (answer.indexOf(c) < 0) {
                    excludedLetters.add(c);
                }
            }
        }

        String resultString = new String(result);
        log.println("Ввод: " + word + " -> " + resultString);
        return resultString;
    }

    public boolean isCorrectAnswer(String word) {
        return word.equals(answer);
    }

    public String getHint() {
        List<String> candidates = new ArrayList<>();

        for (String candidate : dictionary.getWords()) {
            if (usedHints.contains(candidate)) {
                continue;
            }
            if (matchesKnowledge(candidate)) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            return answer;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(candidates.size());
        String hint = candidates.get(randomIndex);
        usedHints.add(hint);
        return hint;
    }

    private boolean matchesKnowledge(String candidate) {
        for (Integer position : knownPositions.keySet()) {
            char requiredLetter = knownPositions.get(position);
            if (candidate.charAt(position) != requiredLetter) {
                return false;
            }
        }

        for (Character letter : knownLettersWrongPositions.keySet()) {
            if (candidate.indexOf(letter) < 0) {
                return false;
            }
            Set<Integer> wrongPositions = knownLettersWrongPositions.get(letter);
            for (Integer wrongPosition : wrongPositions) {
                if (candidate.charAt(wrongPosition) == letter) {
                    return false;
                }
            }
        }

        for (Character excludedLetter : excludedLetters) {
            if (candidate.indexOf(excludedLetter) >= 0) {
                return false;
            }
        }
        return true;
    }
}