package juc.concurrent.programming.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 写入：
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(String.valueOf(temp), temp + "value");
            }, String.valueOf(i)).start();
        }

        // 读取：
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(String.valueOf(temp));
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * 自定义缓存
 */

class MyCache {
    /* 更加细粒度的控制锁 */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private volatile Map<String, Object> map = new HashMap<>();

    // 确保同一时间只有一个线程写入
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成" + key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 允许同一时间多个线程读取
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取完成" + key);
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

}