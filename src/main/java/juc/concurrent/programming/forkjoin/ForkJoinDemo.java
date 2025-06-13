package juc.concurrent.programming.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinDemo extends RecursiveTask<Long> {
    private Long start;
    private Long end;
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /* 可以通过中间值调优：*/
        // Sum: 499934463999828390; Time: 9825
        forkJoinTest();

         /* Stream 流式计算：并行执行，效率更高 */
        // Sum: 499999999500000000; Time: 487
        long start = System.currentTimeMillis();
        long sum = LongStream.range(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("Sum: " + sum + "; Time: " + (end - start));
    }

    public static void forkJoinTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("Sum: " + sum + "; Time: " + (end - start));
    }

    @Override
    protected Long compute() {
        if (end - start < temp) {
            Long sum = 0L;
            for (Long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 使用ForkJoin
            long middle = (start + end) / 2;
            // 拆分任务，将任务压入线程队列（1分为2）
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            task1.fork();
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}
