package urlshortcut.util;

import java.util.Random;

public class RandomStringGenerator {
    public static String generate(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

       return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
