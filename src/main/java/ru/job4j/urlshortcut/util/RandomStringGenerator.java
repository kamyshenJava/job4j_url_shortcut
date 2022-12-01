package ru.job4j.urlshortcut.util;

import java.util.Random;

public class RandomStringGenerator {
    public static String generate(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

       return random.ints(leftLimit, rightLimit + 1)
               .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
               .limit(length)
               .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
               .toString();
    }

}
