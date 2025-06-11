package juc.concurrent.programming.producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Zero7ProducerConsumer3 {
    public static void main(String[] args) {
        Data3 data3 = new Data3();
        // 需求： A执行完通知B； B执行完通知C；
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printA();
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printB();
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printC();
            }
        }, "线程C").start();
    }
}

/* 资源类 */
class Data3 {
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    /*
     * 1 - A执行
     * 2 - B执行
     * 3 - C执行
     * */
    // 根据程序设定，初始值必须是1，2或3，否则程序将进入await().
    private int number = 1;

    public void printA() {
        lock.lock();
        try {
            // 业务代码： 判断；执行；通知。
            while (number != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + " - A");
            // 唤醒B
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 业务代码： 判断；执行；通知。
            while (number != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + " - B");
            // 唤醒C
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {

        lock.lock();
        try {
            // 业务代码： 判断；执行；通知。
            while (number != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + " - C");
            // 唤醒A
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}