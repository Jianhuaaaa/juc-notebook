package com.jsun.threads.state;

public class ThreadYield {
    public static void main(String[] args) {
        TryYield tryYield = new TryYield();
        new Thread(tryYield, "线程-A").start();
        new Thread(tryYield, "线程-B").start();
    }
}

class TryYield implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");
        // 礼让
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + "线程停止执行");
    }
}
