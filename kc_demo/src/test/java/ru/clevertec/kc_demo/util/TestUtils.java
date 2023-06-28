package ru.clevertec.kc_demo.util;


import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public final class TestUtils {
    private static final Random rnd = new Random();

    public static int getRandomInt(){
        return getRandomInt(Integer.MAX_VALUE);
    }

    public static int getRandomInt(int bound){
        return rnd.nextInt(bound);

    }

    public static long getRandomLong(){
        return getRandomLong(Long.MAX_VALUE);
    }

    public static long getRandomLong(long bound){
        return rnd.nextLong(bound);
    }

    public static double getRandomDouble(){
        return getRandomDouble(100.00);
    }

    public static double getRandomDouble(double bound){
        return rnd.nextDouble(bound);
    }

    public static String getRandomString(int targetLength){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return rnd.ints(leftLimit, rightLimit + 1)
                .limit(targetLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getRandomString(){
        return getRandomString(18);
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
        Random random = new Random();
        T[] enumValues = enumClass.getEnumConstants();
        int index = random.nextInt(enumValues.length);
        return enumValues[index];
    }
}