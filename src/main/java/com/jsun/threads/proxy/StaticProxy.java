package com.jsun.threads.proxy;

public class StaticProxy {
    public static void main(String[] args) {
        // Thread类静态代理：
        new Thread(() -> System.out.println("对比Thread与静态代理"))
                .start();
        // 自己实现的静态代理
        new EducationInstruction(new Student())
                .doTest();
    }
}

interface HighSchoolTest {
    void doTest();
}

class Student implements HighSchoolTest {

    @Override
    public void doTest() {
        System.out.println("我要考试了，祝自己一切顺利");
    }
}

class EducationInstruction implements HighSchoolTest {

    private HighSchoolTest target;

    public EducationInstruction(HighSchoolTest target) {
        this.target = target;
    }


    @Override
    public void doTest() {
        before();
        this.target.doTest();
        after();
    }

    private void after() {
        System.out.println("Cheers, the test is over!!!");
    }

    private void before() {
        System.out.println("OMG, the test comes");
    }
}
