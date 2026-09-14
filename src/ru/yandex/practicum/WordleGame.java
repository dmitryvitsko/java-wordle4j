package ru.yandex.practicum;

import java.io.IOException;
import java.util.Random;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    public WordleGame(WordleDictionary wordleDictionary){
        if (wordleDictionary.getWords().isEmpty()) {
            throw new IllegalArgumentException("Словарь пуст, играть не во что");
        }
        this.dictionary = wordleDictionary;
        this.steps = 6;
        this.answer = this.dictionary.getWords().get(new Random().nextInt(this.dictionary.getWords().size()));
    }
}
