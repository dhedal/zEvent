package com.ecf.zevent.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

public class PasswordUtil {
    private final static int SIZE_MIN = 8;

    private final static String CHAR_SPE = "!@#$%&*?:+-";

    public static String passwordTemp() {
        return "PASSWORD_TEMP";
    }

    public static String passwordTemp(PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(generateRandomPassword());
    }

    public static String generateRandomPassword() {
        return generateRandomPassword(SIZE_MIN);
    }

    public static String generateRandomPassword(int size) {
        if(size < SIZE_MIN) size = SIZE_MIN;
        StringBuilder password = new StringBuilder();

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String charSpe = "!@#$";
        String numbers = "1234567890";

        Random random = new Random();

        char [] chars = new char[]{
                upperCase.charAt(random.nextInt(upperCase.length())),
                lowerCase.charAt(random.nextInt(lowerCase.length())),
                charSpe.charAt(random.nextInt(charSpe.length())),
                numbers.charAt(random.nextInt(numbers.length()))
        };
        password.append(chars);

        size -= chars.length;
        chars = new char[size];
        String combinedChars = upperCase + lowerCase + charSpe + numbers;
        for(int i = 0; i < chars.length; i++) {
            chars[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        password.append(chars);

        return password.toString();
    }

}
