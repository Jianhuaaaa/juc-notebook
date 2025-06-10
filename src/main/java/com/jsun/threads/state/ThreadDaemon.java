package com.jsun.threads.state;

public class ThreadDaemon {
    public static void main(String[] args) {
        God god = new God();
        People userThread = new People();
        Thread daemonThread = new Thread(god);
        // 将GOD设置为守护线程，默认为false，即用户线程
        daemonThread.setDaemon(true);
        // 启动守护进程
        daemonThread.start();
        // 启动用户进程
        new Thread(userThread)
                .start();
    }
}

class God implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("上帝保佑你每一天！");
        }
    }
}

class People implements Runnable {
    @Override
    public void run() {
        for (int i = 300; i <= 365; i++) {
            System.out.println("一年中的第" + i + "天");
        }
        System.out.println("Current year is end!");
    }
}