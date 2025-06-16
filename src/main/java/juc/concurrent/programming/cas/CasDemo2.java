package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CasDemo2 {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference<>(25, 1);
        new Thread(() -> {
            // 获取版本号
            int stamp = atomicReference.getStamp();
            System.out.println("A1 - " + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(atomicReference.compareAndSet(
                    25,
                    26,
                    atomicReference.getStamp(),
                    atomicReference.getStamp() + 1));
            System.out.println("A2 - " + atomicReference.getStamp());

            System.out.println(atomicReference.compareAndSet(
                    26,
                    25,
                    atomicReference.getStamp(),
                    atomicReference.getStamp() + 1));
            System.out.println("A3 - " + atomicReference.getStamp());
        }, "线程A").start();


        new Thread(() -> {
            int stamp = atomicReference.getStamp();
            System.out.println("B1 - " + stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            atomicReference.compareAndSet(25, 80, stamp, stamp + 1);
            System.out.println("B2 - " + atomicReference.getStamp());

        }, "线程B").start();
    }
}
