package juc.concurrent.programming;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerJet10C {
    public static void main(String[] args) {
        Jet10C jet10C = new Jet10C();
        new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                jet10C.order(i);
            }
        }, "订单线程").start();
        new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                jet10C.packaging(i);
            }
        }, "打包线程").start();
        new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                jet10C.deliver(i);
            }
        }, "快递线程").start();
    }
}

class Jet10C {
    Lock lock = new ReentrantLock();
    Condition orderCondition = lock.newCondition();
    Condition packagingCondition = lock.newCondition();
    Condition deliverCondition = lock.newCondition();
    /* 业务需求
     * 0 - order
     * 1 - packaging
     * 2 - deliver
     * */
    private int code = 0;

    public void order(int orderNo) {
        lock.lock();
        try {
            // 业务代码
            // 1. 判断
            while (code != 0) {
                orderCondition.await();
            }
            // 2. 执行
            System.out.println(Thread.currentThread().getName() + String.format(" - Step1/3 - 客户已下单%s，请通知打包线程", orderNo));
            // 3. 通知
            // 更新业务代码
            code = 1;
            packagingCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void packaging(int orderNo) {
        lock.lock();
        try {
            // 业务代码
            // 1. 判断
            while (code != 1) {
                packagingCondition.await();
            }
            // 2. 执行
            System.out.println(Thread.currentThread().getName() + String.format(" - Step2/3 - 打包已完成%s，请通知快递线程", orderNo));
            // 3. 通知
            // 更新业务代码
            code = 2;
            deliverCondition.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void deliver(int orderNo) {
        lock.lock();
        try {
            // 业务代码
            // 1. 判断
            while (code != 2) {
                deliverCondition.await();
            }
            // 2. 执行
            System.out.println(Thread.currentThread().getName() + String.format(" - Step3/3 - 快递已发出%s，请通知下单线程", orderNo));
            // 3. 通知
            // 更新业务代码
            code = 0;
            orderCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
