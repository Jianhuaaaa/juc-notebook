package juc.concurrent.programming.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2025);
        // public final boolean compareAndSet(int expectedValue, int newValue)
        /* 如果期望值达到了，就更新，否则不更新。*/

        // 值被改修后又改回来
        atomicInteger.compareAndSet(2025, 2026);
        System.out.println(atomicInteger.get());
        atomicInteger.compareAndSet(2026, 2025);
        System.out.println(atomicInteger.get());

        atomicInteger.compareAndSet(2025, 1024);
        System.out.println(atomicInteger.get());

    }
}
