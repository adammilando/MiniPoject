package org.Camp.Utils;

import java.util.Random;

public class IdGenerator {
    private static final int UPPER_BOUND = 10000;

    public Long generateId() {
        Random random = new Random();
        return (long) (random.nextInt(UPPER_BOUND) + 10000);
    }
}



