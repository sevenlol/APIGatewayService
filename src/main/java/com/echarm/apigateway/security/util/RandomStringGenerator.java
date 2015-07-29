package com.echarm.apigateway.security.util;

import java.util.Random;

public class RandomStringGenerator {

    public static String getString(int length) {
        if (length <= 0) {
            return null;
        }

        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
