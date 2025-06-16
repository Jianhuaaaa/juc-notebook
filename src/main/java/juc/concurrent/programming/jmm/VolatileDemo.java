package juc.concurrent.programming.jmm;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    private volatile static AtomicInteger number = new AtomicInteger();

    private static void add() {
        // +1, 底层用的是CAS
        number.getAndIncrement();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " " + number);
    }
}
