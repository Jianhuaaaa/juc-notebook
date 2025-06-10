package com.jsun.threads.startthreads;

public class TestThread4 implements Runnable {
    private int ticketNumbers = 10;

    @Override
    public void run() {
        while (ticketNumbers > 0) {
            System.out.println(Thread.currentThread().getName() + " - 买到了第" + ticketNumbers-- + "张票");
            // 模拟延时：
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        TestThread4 testThread4 = new TestThread4();
        new Thread(testThread4, "T1").start();
        new Thread(testThread4, "T2").start();
        new Thread(testThread4, "T3").start();
    }
}
