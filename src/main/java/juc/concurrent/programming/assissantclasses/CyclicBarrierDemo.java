package juc.concurrent.programming.assissantclasses;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("成功召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            // 补充知识点： 在Lambda表达式中，通过声明一个final类型的变量可以获取i的值。
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集到了<" + temp + ">号龙珠");
                try {
                    // 等待计数器达到7
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

}
