package com.jsun.threads.state;

public class ThreadJoin implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("我是VIP线程，所有人都要为我让路！" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 自定义线程：
        ThreadJoin threadJoin = new ThreadJoin();
        Thread vipThread = new Thread(threadJoin);
        vipThread.start();

        // 主线程：
        for (int j = 0; j < 1000; j++) {
            if (j == 200) {
                // 强制插队
                vipThread.join();
            }
            System.out.println("我是文明排队的主线程" + j);
        }
    }
}
