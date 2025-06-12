package juc.concurrent.programming.lists;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
        // Exception in thread "55" java.util.ConcurrentModificationException
        // Map<String, String> map = new HashMap<>();
        // 使用线程安全的Map：
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 50; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
