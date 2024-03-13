package ir.xenoncommunity.jss.utils;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.security.SecureRandom;
import java.util.Random;

@UtilityClass
public class Randomize {
    private final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public int randomPort() {
        return (int) (Math.random() * 64535) + 1000;
    }

    public byte[] randomBytes(final int size) {
        val bytes = new byte[size];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    public String randomString(final int length) {
        return new Random().ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
