package com.jsun.threads.synchronization;

import java.util.ArrayList;
import java.util.List;

public class UnsafeList {
    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            new Thread(() -> {
                // 先锁住list，再操作！
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }
        // 模拟延时
        Thread.sleep(3000);
        System.out.println("ArrayList 长度： " + list.size());
    }
}
