package juc.concurrent.programming.function;

import java.util.function.Predicate;

/**
 * 判断式接口： 接收一个参数，返回一个boolean值
 */
public class PredicateDemo {
    public static void main(String[] args) {
        Predicate<String> predicate = String::isEmpty;
        System.out.println(predicate.test("Not an empty string"));
    }
}
