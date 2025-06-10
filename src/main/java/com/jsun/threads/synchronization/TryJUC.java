package com.jsun.threads.synchronization;

import java.util.concurrent.CopyOnWriteArrayList;

// java.util.concurrent
public class TryJUC {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
            }).start();
        }

        Thread.sleep(3000);
        System.out.println("List长度： " + list.size());
    }
}
