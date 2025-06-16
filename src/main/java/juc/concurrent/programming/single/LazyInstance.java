package juc.concurrent.programming.single;

public class LazyInstance {
    private LazyInstance() {
        System.out.println(Thread.currentThread().getName() + " up");
    }

    private volatile static LazyInstance lazyInstance;

    /* 懒汉式单例 - 双重检测锁模式 - 又叫DCL懒汉式 */
    public static LazyInstance getInstance() {
        if (lazyInstance == null) {
            /**
             * 由于lazyDemo = new LazyDemo();不是原子操作，它分为三步走，可能发生指令重排。
             * 为防止问题的发生，需要使用volatile关键字修饰实例！！
             * 1. 分配内存空间
             * 2. 执行构造方法，初始化对象
             * 3. 把对象指向分配的空间
             */
            lazyInstance = new LazyInstance();
        }
        return lazyInstance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LazyInstance.getInstance();
            }).start();
        }
    }
}
