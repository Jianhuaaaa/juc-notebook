package juc.concurrent.programming.lockscenarios;

import java.util.concurrent.TimeUnit;

public class Scenario7 {
    public static void main(String[] args) throws InterruptedException {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

        new Thread(() -> {
            phone1.sendSms();
        }, "").start();

        TimeUnit.SECONDS.sleep(1);

        // 调用普通方法，不受锁的影响
        new Thread(() -> {
            phone2.call();
        }, "").start();
    }
}

class Phone4 {
    // 静态同步方法
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " 在发短信");
    }

    // 普通同步方法
    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + " 在打电话");
    }
}
