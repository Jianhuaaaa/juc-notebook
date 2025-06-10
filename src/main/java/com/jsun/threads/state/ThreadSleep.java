package com.jsun.threads.state;

public class ThreadSleep implements Runnable {
    private int ticketNumbers = 10;

    @Override
    public void run() {
        while (ticketNumbers > 0) {
            System.out.println(Thread.currentThread().getName() + " - 买到了第" + ticketNumbers-- + "张票");
            // 模拟延时：放大问题的发生概率
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        ThreadSleep threadSleep = new ThreadSleep();
        new Thread(threadSleep, "T1").start();
        new Thread(threadSleep, "T2").start();
        new Thread(threadSleep, "T3").start();
    }
}
