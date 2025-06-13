package juc.concurrent.programming.threadpool;

import java.util.concurrent.*;

public class PoolDemo01 {

    public static void main(String[] args) {


        // 自定义线程池！工作中一定是自己创建 （阿里巴巴手册已经指出，Executors直接创建不安全）
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                // 银行满了还有人进来 - 队列满了则尝试和最早的竞争（成功则被执行，失败会被丢弃），不抛出异常
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        try {
            // 同时最大处理业务的量： maximumPoolSize(5) + LinkedBlockingDeque<>(3) = 8
            for (int i = 1; i <= 9; i++) {
                // 使用线程池来创建线程，而不再是new Thread().start().
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " ready");
                });
            }
        } finally {
            // 关闭线程池
            threadPool.shutdown();
        }
    }
}
