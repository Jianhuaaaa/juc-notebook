package com.jsun.threads.state;

import java.time.LocalTime;

public class ThreadMonitoring {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("--------------------------");
        });

        // 观察状态
        Thread.State state = thread.getState();
        System.out.println("线程状态： " + state); // new

        // 观察启动后状态
        thread.start();
        state = thread.getState();
        System.out.println("线程状态： " + state); // running

        // 只要线程不终止， 就一直输出状态
        while (state != Thread.State.TERMINATED) {
            Thread.sleep(100);
            // 更新线程状态
            state = thread.getState();
            // 输出线程状态
            System.out.println("线程状态： " + LocalTime.now() + " - " + state);
        }
    }
}
