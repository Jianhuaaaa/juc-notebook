package juc.concurrent.programming;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Zero7ProducerConsumer_Lock {
    public static void main(String[] args) throws InterruptedException {
        NewData newData = new NewData();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    newData.incremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "A").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    newData.decremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    newData.incremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    newData.decremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "D").start();
    }
}

class NewData {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private int counter = 0;

    public void incremental() throws InterruptedException {
        lock.lock();
        try {
            /* 业务代码 */
            // 判断等待
            while (counter != 0) {
                // 目前队列不为空，继续等待消费者消费
                condition.await();
            }
            // 执行业务
            counter++;
            System.out.println(Thread.currentThread().getName() + " -> " + counter);
            // 通知
            condition.signalAll();
            /* 结束业务代码 */
        } finally {
            lock.unlock();
        }
    }

    public void decremental() throws InterruptedException {
        lock.lock();
        try {
            /* 业务代码 */
            // 判断等待
            while (counter == 0) {
                // 目前队列为空，等待生产者生产
                condition.await();
            }
            // 执行业务
            counter--;
            System.out.println(Thread.currentThread().getName() + " -> " + counter);
            // 通知
            condition.signalAll();
            /* 结束业务代码 */
        } finally {
            lock.unlock();
        }
    }
}