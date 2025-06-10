package com.jsun.threads.state;

public class ThreadPriority {
    public static void main(String[] args) {
        // 主线程默认优先级
        System.out.println(Thread.currentThread().getName() + "->" + Thread.currentThread().getPriority());
        TryPriority tryPriority = new TryPriority();
        Thread thread1 = new Thread(tryPriority);
        Thread thread2 = new Thread(tryPriority);
        Thread thread3 = new Thread(tryPriority);
        Thread thread4 = new Thread(tryPriority);
        Thread thread5 = new Thread(tryPriority);

        // 先设置优先级， 再启动
        thread1.start();

        thread2.setPriority(1);
        thread2.start();

        thread3.setPriority(3);
        thread3.start();

        thread4.setPriority(Thread.MAX_PRIORITY);
        thread4.start();

        thread5.setPriority(7);
        thread5.start();
    }
}

class TryPriority implements Runnable {

    @Override
    public void run() {
        // 自定义线程
        System.out.println(Thread.currentThread().getName() + "->" + Thread.currentThread().getPriority());
    }
}