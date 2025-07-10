package utils;

import java.util.Random;

public class RandomUtil {
    private static final Random rand = new Random();

    public static int randomLevel(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public static double randomStat(double min, double max) {
        double value = min + (max - min) * rand.nextDouble();
        return Math.round(value * 100.0) / 100.0;
    }
}