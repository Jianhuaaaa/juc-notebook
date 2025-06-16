package juc.concurrent.programming.cas;

import java.util.concurrent.atomic.AtomicReference;

// 自动逸的自旋锁
public class CustomizedSpinLock {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 加锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "- myLock");
        while (!atomicReference.compareAndSet(null, thread)) {
            // 自旋
        }
    }

    // 解锁
    public void myUnlock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "- myUnlock");

        atomicReference.compareAndSet(thread, null);
    }
}
