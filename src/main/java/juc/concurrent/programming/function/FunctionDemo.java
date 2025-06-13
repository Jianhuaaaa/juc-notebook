package juc.concurrent.programming.function;

import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {
        Function<String, String> function = (str) -> str + "666";
        System.out.println(function.apply("You win! "));
    }
}
