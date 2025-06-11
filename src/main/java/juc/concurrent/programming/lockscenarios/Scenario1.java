package juc.concurrent.programming.lockscenarios;

import java.util.concurrent.TimeUnit;

public class Scenario1 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendSms();
        }, "").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            phone.call();
        }, "").start();
    }
}

class Phone {
    // synchronized锁的对象是方法的调用者（phone），两个方法用的是同一把锁，谁先拿到谁先执行！
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " 在发短信");
    }

    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + " 在打电话");
    }
}
