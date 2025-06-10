package com.jsun.threads.communication;

public class ProducerConsumer {
    public static void main(String[] args) {
        ProductContainer container = new ProductContainer();
        new Producer(container).start();
        new Consumer(container).start();
    }
}

// 缓冲区
class ProductContainer {
    // 容器计数器
    int count = 0;
    // 创建并初始化容器
    Product[] products = new Product[10];

    // 生产者放入产品
    public synchronized void push(Product product) {
        // 如果容器满了，则需要等待消费者消费
        if (count == products.length) {
            // 通知消费者消费，生产者则等待。
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 如如果容器没有满，则加入产品
        products[count] = product;
        count++;

        // 通知消费者去消费
        this.notifyAll();
    }

    // 消费者消费产品
    public synchronized Product pull() {
        // 判断能否消费
        if (count == 0) {
            // 等待生产者生产，消费者则等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 如果可以消费
        count--;
        Product product = products[count];

        // 通知生产者生产
        this.notifyAll();
        return product;
    }

}

// 产品
class Product {
    int id;

    public Product(int id) {
        this.id = id;
    }
}

class Producer extends Thread {
    ProductContainer container;

    public Producer(ProductContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            container.push(new Product(i));
            System.out.println("生产了" + i + "个产品");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer extends Thread {
    ProductContainer container;

    public Consumer(ProductContainer container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了第" + container.pull().id + "个产品");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

