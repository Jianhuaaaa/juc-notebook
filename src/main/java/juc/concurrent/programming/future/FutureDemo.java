package juc.concurrent.programming.future;

import java.util.concurrent.CompletableFuture;

public class FutureDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " supplyAsync");
            return 1024;
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t: " + t + ", u: " + u);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233;
        }));
    }
}
