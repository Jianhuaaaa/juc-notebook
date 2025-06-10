package com.jsun.threads.startthreads;

public class TurtleRabbitRace implements Runnable {
    private String winner;

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            // 模拟兔子休息, 每10米休息100毫秒
            if (Thread.currentThread().getName().equals("兔子") && i % 10 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean flag = gameOver(i);
            if (flag) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "-> 跑了" + i + "米");
        }
    }

    public static void main(String[] args) {
        TurtleRabbitRace race = new TurtleRabbitRace();
        new Thread(race, "兔子").start();
        new Thread(race, "乌龟").start();
    }

    private boolean gameOver(int metter) {
        if (winner != null) {
            return true;
        }
        if (metter >= 100) {
            winner = Thread.currentThread().getName();
            System.out.println("Winner is " + winner);
            return true;
        }
        return false;
    }
}
