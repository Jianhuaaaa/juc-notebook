package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;

// 模拟死锁
public class DeadLockTest {
    private static String strA = "String A";
    private static String strB = "String B";

    public static void main(String[] args) {
        new Thread(new MyThread(strA, strB), "T1").start();
        new Thread(new MyThread(strB, strA), "T1").start();
    }
}

class MyThread implements Runnable {
    private String strA;
    private String strB;

    public MyThread(String strA, String strB) {
        this.strA = strA;
        this.strB = strB;
    }

    @Override
    public void run() {
        synchronized (strA) {
            System.out.println(Thread.currentThread().getName() + "lock: " + strA + "- get" + strB);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (strB) {
                System.out.println(Thread.currentThread().getName() + "lock: " + strB + "- get" + strA);
            }
        }
    }
}
