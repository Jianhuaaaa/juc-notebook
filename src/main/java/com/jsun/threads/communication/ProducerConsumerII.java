package com.jsun.threads.communication;

public class ProducerConsumerII {

    public static void main(String[] args) {
        Play play = new Play();
        new Actor(play)
                .start();
        new Audience(play)
                .start();
    }
}

// 生产者 -> 演员
class Actor extends Thread {

    Play play = new Play();

    public Actor(Play play) {
        this.play = play;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                this.play.play("动画片");
            } else {
                this.play.play("探索发现");
            }
        }
    }
}

// 消费者 -> 观众
class Audience extends Thread {
    Play play = new Play();

    public Audience(Play play) {
        this.play = play;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            this.play.watch();
        }
    }
}

// 产品 -> 节目
class Play {
    // 演员表演，观众等待 T
    // 观众观看，演员等待 F
    String movie;
    boolean flag = true;

    // 表演
    public synchronized void play(String movie) {
        if (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("演员拍摄了" + movie);
        // 通知观众观看
        this.notifyAll();
        this.movie = movie;
        this.flag = !this.flag;
    }

    // 观看
    public synchronized void watch() {
        if (flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("观众观看了" + movie);
        // 通知演员拍摄
        this.notifyAll();
        this.flag = !this.flag;
    }
}