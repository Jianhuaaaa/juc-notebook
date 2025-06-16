package juc.concurrent.programming.jmm;

import java.util.concurrent.TimeUnit;

public class JmmDemo {
    private volatile static int number = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (number == 0) {
                // 让程序循环
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        number = 1;
        System.out.println("Current number: " + number);
    }
}
