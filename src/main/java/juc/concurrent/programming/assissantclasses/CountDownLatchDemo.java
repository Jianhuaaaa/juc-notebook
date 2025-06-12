package juc.concurrent.programming.assissantclasses;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 总数是6，有必须要执行任务的时候使用！
        // 当前场景： 教室里面有6个学生，老师等所有人都走出教室后执行锁门操作。
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 走出教室");
                countDownLatch.countDown();
            }).start();
        }

        // 等待计数器归零，然后继续执行下面的代码
        countDownLatch.await();
        System.out.println("锁门");
    }
}
