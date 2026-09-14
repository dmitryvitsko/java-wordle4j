package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    // 1. Создаем метод, который принимает имя файла и возвращает WordleDictionary
    public WordleDictionary loadDictionary(String filePath) throws IOException{

        // Создаем новый объект словаря, который будем заполнять
        WordleDictionary wordleDictionary = new WordleDictionary();

        // Тело try-catch теперь находится внутри метода, где ему и положено быть
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 5) {
                    line = line.toLowerCase().replace("ё", "е"); // replace быстрее, чем replaceAll
                    wordleDictionary.getWords().add(line);
                }
            }
        }

        // 2. На выходе возвращаем заполненный класс словаря, как просит задание
        return wordleDictionary;
    }
}
