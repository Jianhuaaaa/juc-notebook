package com.jsun.threads.lock;

/* 多个线程互相抱着对方需要的资源，然后行程僵持 */
public class DeadLock {
    public static void main(String[] args) {
        MakeUp girlA = new MakeUp(0, "白雪公主");
        MakeUp girlB = new MakeUp(1, "王子");
        girlA.start();
        girlB.start();
    }
}

class MakeUp extends Thread {
    // 需要的资源只有一份，用<static>关键字来保证！
    static Mirror mirror = new Mirror();
    static LipStick lipStick = new LipStick();
    // 选择
    int choices;
    // 需要化妆的人
    String herName;

    public MakeUp(int choices, String herName) {
        this.choices = choices;
        this.herName = herName;
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeup() throws InterruptedException {
        if (choices == 0) {
            synchronized (lipStick) {
                System.out.println(this.herName + "获得口红的锁");
                Thread.sleep(1000);
            }
            synchronized (mirror) {
                System.out.println(this.herName + "获得镜子的锁");
            }
        } else {
            synchronized (mirror) {
                System.out.println(this.herName + "获得镜子的锁");
                Thread.sleep(2000);
            }
            synchronized (lipStick) {
                System.out.println(this.herName + "获得口红的锁");
            }
        }
    }

}

// 口红
class LipStick {

}

// 镜子
class Mirror {

}