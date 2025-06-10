package com.jsun.threads.synchronization;

/* 线程不安全 */
public class UnsafeTicketBooking {
    public static void main(String[] args) {
        TicketBooking ticketBooking = new TicketBooking();
        new Thread(ticketBooking, "aaa").start();
        new Thread(ticketBooking, "bbb").start();
        new Thread(ticketBooking, "ccc").start();
    }
}

class TicketBooking implements Runnable {
    int ticketNumbers = 10;
    // 停止线程标识
    boolean flag = true;

    @Override
    public void run() {
        while (true) {
            book();
        }
    }

    /* synchronized 同步锁，所的对象是this */
    private synchronized void book() {
        // 判断是否邮票
        if (ticketNumbers <= 0) {
            flag = false;
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "买到" + ticketNumbers--);
    }
}