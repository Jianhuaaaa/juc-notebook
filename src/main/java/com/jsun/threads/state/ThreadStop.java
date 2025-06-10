package com.jsun.threads.state;

public class ThreadStop implements Runnable {
    // 设置标志位
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("Run thread " + i++);
        }
    }

    // 设置一个公有方法停止线程，转换标志位。
    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        ThreadStop stopThread = new ThreadStop();
        new Thread(stopThread).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("Main thread - i equals " + i);
            if (i == 500) {
                // 调用stop()方法切换标志位，让线程停止
                stopThread.stop();
                System.out.println("线程停止了");
            }
        }
    }
}
