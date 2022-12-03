package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomStringGenerator implements StringGenerator {
    public String generate(int length) {
        int leftLimit = '0';
        int rightLimit = 'z';
        Random random = new Random();

       return random.ints(leftLimit, rightLimit + 1)
               .filter(i -> (i <= '9' || i >= 'A') && (i <= 'Z' || i >= 'a'))
               .limit(length)
               .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
               .toString();
    }

}
