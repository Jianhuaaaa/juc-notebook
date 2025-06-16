### 什么是CAS - compareAndSet： 比较并交换

示例代码：

```java
package juc.concurrent.programming.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2025);
        // public final boolean compareAndSet(int expectedValue, int newValue)
        /* 如果期望值达到了，就更新，否则不更新。*/
        atomicInteger.compareAndSet(2025, 2026);
        System.out.println(atomicInteger.get());
    }
}
```

![Cplusplus_unsafe_method.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/Cplusplus_unsafe_method.png)

**通过C++调用底层原生方法。内存操作，效率很高！**
![AtomicInteger加一操作.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/AtomicInteger%E5%8A%A0%E4%B8%80%E6%93%8D%E4%BD%9C.png)

![AtomicInteger_getAndAddInt.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/AtomicInteger_getAndAddInt.png)

#### 缺点

1. 底层自旋锁，循环耗时
2. 一次性只能保证一个共享变量的原子性
3. 存在ABA问题

### ABA问题

**什么是ABA问题：**
如图： 线程A拿到A = 1, 期待值为1，并将它修改为二；
然后线程B更快，它拿到A = 1, 将值修改为3之后又改回1。而这个过程线程A并不知情。
![ABA问题.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/ABA%E9%97%AE%E9%A2%98.png)

示例代码：

```java
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
```

### 如何解决ABA问题： 带版本号的原子操作

![Integer赋值区间的坑.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/Integer%E8%B5%8B%E5%80%BC%E5%8C%BA%E9%97%B4%E7%9A%84%E5%9D%91.png)
此时将AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference<>(25, 1)
;初始值设置为超出复制范围的数字（如：2025）就会有这个问题。

示例代码：

```java
package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

// 乐观锁原理
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
```

### 自定义自旋锁

示例代码：

```java
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


package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;

public class CustomizedSpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        CustomizedSpinLock lock = new CustomizedSpinLock();

        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.myUnlock();
            }
        }, "T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.myUnlock();
            }
        }, "T2").start();
    }
}
```

### 死锁

![死锁.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/%E6%AD%BB%E9%94%81.png)

示例代码：

```java
package juc.concurrent.programming.cas;

import java.util.concurrent.TimeUnit;

// 模拟死锁
public class DeadLockTest {
    private static String strA = "String A";
    private static String strB = "String B";

    public static void main(String[] args) {
        new Thread(new MyThread(strA, strB), "T1").start();
        new Thread(new MyThread(strB, strA), "T1").start();
    }
}

class MyThread implements Runnable {
    private String strA;
    private String strB;

    public MyThread(String strA, String strB) {
        this.strA = strA;
        this.strB = strB;
    }

    @Override
    public void run() {
        synchronized (strA) {
            System.out.println(Thread.currentThread().getName() + "lock: " + strA + "- get" + strB);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (strB) {
                System.out.println(Thread.currentThread().getName() + "lock: " + strB + "- get" + strA);
            }
        }
    }
}
```

#### 死锁排查

1. 使用 `jps -l` 定位进程号
   ![jps -l查看进程号.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/jps%20-l%E6%9F%A5%E7%9C%8B%E8%BF%9B%E7%A8%8B%E5%8F%B7.png)
2. 使用 `jstack 进程号` 找到死锁问题
   ![使用jstack 进程号查看死锁问题.png](../../src/main/resources/pictures/juc_concurrent_programming/cas/%E4%BD%BF%E7%94%A8jstack%20%E8%BF%9B%E7%A8%8B%E5%8F%B7%E6%9F%A5%E7%9C%8B%E6%AD%BB%E9%94%81%E9%97%AE%E9%A2%98.png)

