/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

import nextteam.exception.NoRandomCommandException;

/**
 *
 * @author vnitd
 */
public class RandomGenerator {

    private static final String[] SEED = {"abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789"};
    public static final int NUMERIC_CHARACTER = 0b001;
    public static final int LOWERCASE_CHARACTER = 0b010;
    public static final int UPPERCASE_CHARACTER = 0b100;

    public static int randNum(int min, int max) {
        return (int) (Math.random() * (max)) + min;
    }

    private static boolean bool(int bool) {
        return bool > 0;
    }

    public static String randString(int command, int length) {
        String SEED = "";
        if (length == 0) {
            throw new NoRandomCommandException();
        }
        if (bool(command & UPPERCASE_CHARACTER)) {
            SEED += RandomGenerator.SEED[0];
        }
        if (bool(command & LOWERCASE_CHARACTER)) {
            SEED += RandomGenerator.SEED[1];
        }
        if (bool(command & NUMERIC_CHARACTER)) {
            SEED += RandomGenerator.SEED[2];
        }
        if (length <= 0) {
            return null;
        }
        String res = "";
        for (int i = 0; i < length; i++) {
            res += SEED.charAt(randNum(0, SEED.length()));
        }
        return res;
    }

    public static String simpleRandString(int length) {
        return randString(UPPERCASE_CHARACTER | LOWERCASE_CHARACTER | NUMERIC_CHARACTER, length);
    }
}
