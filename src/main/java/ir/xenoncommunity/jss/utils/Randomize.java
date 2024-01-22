package ir.xenoncommunity.jss.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.Random;

@UtilityClass
public class Randomize {
    private final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public int randomPort() {
        return (int) (Math.random() * 64535) + 1000;
    }

    public byte[] randomBytes(int size) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    public String randomString(int length) {
        Random random = new Random();
        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
