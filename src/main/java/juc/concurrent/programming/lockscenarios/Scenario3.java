package juc.concurrent.programming.lockscenarios;

import java.util.concurrent.TimeUnit;

public class Scenario3 {
    public static void main(String[] args) throws InterruptedException {
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();

        new Thread(() -> {
            phone1.sendSms();
        }, "").start();

        TimeUnit.SECONDS.sleep(1);

        // 调用普通方法，不受锁的影响
        new Thread(phone2::call, "").start();
    }
}

class Phone2 {
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

    /**
     * 我是一个普通方法
     */
    public void sayHi() {
        System.out.println("Hi Lock!");
    }
}
