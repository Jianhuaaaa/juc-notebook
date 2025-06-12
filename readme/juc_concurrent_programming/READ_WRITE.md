### 读写锁(ReentrantReadWriteLock) - 更加细粒度的控制锁

- readWriteLock.writeLock(): 独占锁，一次只能被一个线程占有
- readWriteLock.readLock(): 共享锁，多个线程可以同时占有

示例代码：

```java
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
```

执行结果：

```html
"D:\Program Files\Java\jdk-17.0.2\bin\java.exe" "-javaagent:D:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=62393" -Dfile.encoding=UTF-8 -classpath F:\workspace\multiple-threads\target\classes;C:\Users\Administrator\.m2\repository\commons-io\commons-io\2.18.0\commons-io-2.18.0.jar;C:\Users\Administrator\.m2\repository\org\projectlombok\lombok\1.18.30\lombok-1.18.30.jar juc.concurrent.programming.rw.ReadWriteLockDemo
2写入2
2写入完成2
4写入4
4写入完成4
3写入3
3写入完成3
5写入5
5写入完成5
1读取1
1读取完成1
5读取5
5读取完成5
2读取2
3读取3
4读取4
3读取完成3
2读取完成2
4读取完成4
1写入1
1写入完成1

Process finished with exit code 0
```

