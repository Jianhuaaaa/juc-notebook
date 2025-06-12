package juc.concurrent.programming.assissantclasses;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        // 线程数量： 停车位数量
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 获得锁
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得车位");
                    // 模拟停车时间
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
                System.out.println();
                // 释放锁

            }, String.valueOf(i)).start();
        }
    }
}
