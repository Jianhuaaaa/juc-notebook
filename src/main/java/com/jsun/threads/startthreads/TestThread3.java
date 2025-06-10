package com.jsun.threads.startthreads;

public class TestThread3 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("This is another thread - " + i);
        }
    }

    public static void main(String[] args) {
        // 创建runnable接口的实现类对象
        TestThread3 testThread3 = new TestThread3();
        // 代理： 创建线程对象，通过线程对象来开启线程。
        new Thread(testThread3, "实现Runnable接口")
                .start();

        for (int i = 0; i < 100; i++) {
            System.out.println("This is main thread - " + i);
        }
    }

}
