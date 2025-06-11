package juc.concurrent.programming.producerconsumer;

public class Zero7ProducerConsumer_Sync {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.incremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Customized thread - A").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Customized thread - B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.incremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Customized thread - C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.decremental();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Customized thread - D").start();
    }
}

class Data {
    private int counter = 0;

    public synchronized void incremental() throws InterruptedException {
        // 判断等待
        while (counter != 0) {
            // 目前队列不为空，继续等待消费者消费
            this.wait();
        }
        // 执行业务
        counter++;
        System.out.println(Thread.currentThread().getName() + " -> " + counter);
        // 通知
        this.notifyAll();
    }

    public synchronized void decremental() throws InterruptedException {
        // 判断等待
        while (counter == 0) {
            // 目前队列为空，等待生产者生产
            this.wait();
        }
        // 执行业务
        counter--;
        System.out.println(Thread.currentThread().getName() + " -> " + counter);
        // 通知
        this.notifyAll();
    }
}