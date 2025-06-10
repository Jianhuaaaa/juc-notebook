package com.jsun.threads.startthreads;

//
public class TestThread1 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("This is another thread" + i);
        }
    }

    public static void main(String[] args) {
        TestThread1 testThread1 = new TestThread1();
        // 调用start()方法时，main()和run()方法同步执行
        testThread1.start();
        // 调用run()方法时，先执行run()方法，后执行main()方法。
//        testThread1.run();

        for (int i = 0; i < 1000; i++) {
            System.out.println("This is main thread" + i);
        }
    }
}
