package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;

public class CustomizedSpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        CustomizedSpinLock lock = new CustomizedSpinLock();

        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.myUnlock();
            }
        }, "T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.myUnlock();
            }
        }, "T2").start();
    }
}
