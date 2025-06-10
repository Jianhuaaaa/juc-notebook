package com.jsun.threads.synchronization;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UnsafeCashing {
    public static void main(String[] args) {
        Account account = new Account("基金", 100);
        Drawing client = new Drawing(account, 50, "Client");
        Drawing clientSpouse = new Drawing(account, 80, "Client Spouse");
        client.start();
        clientSpouse.start();
    }
}

@AllArgsConstructor
class Drawing extends Thread {
    Account account;
    double drawingAmount;
    double cashing;

    public Drawing(Account account, double drawingAmount, String name) {
        super(name);
        this.account = account;
        this.drawingAmount = drawingAmount;
    }


    /*
        由于synchronized所的对象默认是this,
        而当前的this对象是Drawing，
        并不是Account，
        所以直接锁run()方法没用。
        此处可以可以选择同步代码块！
     */
    @Override
    public void run() {
        // 锁的对象就是变化的量（即：需要增、删、改的对象），当前变化的量是卡内的金额，因此要锁<卡>而非<银行>！
        /* 锁account对象，把代码块放入synchronized方法体即可解决问题。 */
        synchronized (account) {
            // 判断有没有钱
            if (account.balance - drawingAmount < 0) {
                System.out.println(Thread.currentThread().getName() + " - 余额不足");
                return;
            }

            // 模拟延时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 更新卡里的余额
            account.balance -= drawingAmount;
            // 更新手里的现金
            cashing += drawingAmount;
            System.out.println(account.name + "余额为： " + account.balance);
            System.out.println(this.getName() + "现金为： " + cashing);
        }
    }
}

@Data
@AllArgsConstructor
class Account {
    String name;
    double balance;
}