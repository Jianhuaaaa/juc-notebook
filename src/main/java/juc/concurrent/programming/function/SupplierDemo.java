package juc.concurrent.programming.function;

import java.util.function.Supplier;

public class SupplierDemo {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> {
            return "Supplier interface";
        };
        System.out.println(supplier.get());
    }
}
