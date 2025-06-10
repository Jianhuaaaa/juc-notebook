package com.jsun.threads.lock;

import java.util.concurrent.locks.ReentrantLock;

public class TryLock {
    public static void main(String[] args) {
        Lock2 lock2 = new Lock2();
        new Thread(lock2).start();
        new Thread(lock2).start();
        new Thread(lock2).start();
    }
}

class Lock2 implements Runnable {
    int tickerNumbers = 10;
    // 定义lock
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                // 显示加锁
                lock.lock();
                if (tickerNumbers > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("买到第" + tickerNumbers-- + "张票");
                } else {
                    break;
                }
            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
