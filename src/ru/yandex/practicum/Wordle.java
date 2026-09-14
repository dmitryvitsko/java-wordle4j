package ru.yandex.practicum;

import java.io.IOException;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        WordleDictionary wordleDictionary;
        try {
            wordleDictionary = new WordleDictionaryLoader().loadDictionary("words_ru.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WordleGame wordleGame = new WordleGame(wordleDictionary);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите предполагаемое слово: ");
        String word = scanner.nextLine();


    }

}
