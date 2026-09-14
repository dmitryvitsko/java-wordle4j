package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log"))) {
            try {
                runGame(log);
            } catch (Exception e) {
                log.println("Ошибка: " + e.getMessage());
                e.printStackTrace(log);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать лог-файл", e);
        }
    }

    private static void runGame(PrintWriter log) throws IOException {
        WordleDictionary dictionary;
        try {
            dictionary = new WordleDictionaryLoader().loadDictionary("words_ru.txt");
        } catch (IOException e) {
            throw new DictionaryLoadException("Не удалось загрузить словарь", e);
        }

        WordleGame game = new WordleGame(dictionary, log);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Угадайте слово из 5 букв. У вас " + game.getSteps() + " попыток.");

        boolean won = false;

        while (!game.isFinished() && !won) {
            System.out.println("Осталось попыток: " + game.getSteps());
            System.out.print("Введите слово (Enter - подсказка): ");
            String word = scanner.nextLine().trim().toLowerCase().replace("ё", "е");

            if (word.isEmpty()) {
                String hint = game.getHint();
                System.out.println("Подсказка: " + hint);

                String result = game.compareWithAnswer(hint);
                System.out.println(result);

                if (game.isCorrectAnswer(hint)) {
                    won = true;
                }
                continue;
            }

            try {
                game.validateWord(word);
            } catch (WordNotFoundInDictionaryException e) {
                System.out.println(e.getMessage());
                continue;
            }

            String result = game.compareWithAnswer(word);
            System.out.println(result);

            if (game.isCorrectAnswer(word)) {
                won = true;
            }
        }

        if (won) {
            System.out.println("Поздравляем, вы угадали слово: " + game.getAnswer());
        } else {
            System.out.println("Попытки закончились. Загаданное слово было: " + game.getAnswer());
        }
    }
}