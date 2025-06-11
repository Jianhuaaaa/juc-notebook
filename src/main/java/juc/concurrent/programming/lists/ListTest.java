package juc.concurrent.programming.lists;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

// java.util.ConcurrentModificationException 并发修改异常
// 并发下List不安全

/**
 * 解决方案：
 * 1. 使用Collections.synchronizedList(new ArrayList<>())转换成安全的list；
 */
public class ListTest {
    public static void main(String[] args) {
        // 方案一： 转换成安全的list
        // List<String> list = Collections.synchronizedList(new ArrayList<>());
        // 方案二： 使用JUC包下的安全list替代
        List<String> list = new CopyOnWriteArrayList<>();
        Collections.synchronizedSet(new HashSet<>());
        new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
