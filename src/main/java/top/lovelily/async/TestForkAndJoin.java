package top.lovelily.async;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Desc: TestForkAndJoin
 * Author: xuhe
 * Date: 2020/8/3 2:47 下午
 * Version: 1.0
 */
public class TestForkAndJoin {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         *
         *         100000000:
         *         34 -->54
         *         50000000000L:
         *         14143 -> 17606
         *
         */

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Instant start = Instant.now();

//        long result = forkJoinPool.invoke(new CalculateSumTask(0, 50000000000L));
//        System.out.println(result);

        long sum = 0L;
        for (long i = 0L; i < 50000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);


//        sum = LongStream.range(0, 50000000000L).parallel().reduce(0L, Long::sum);
//        System.out.println(sum);




        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }


}

class CalculateSumTask extends RecursiveTask<Long> {
    long start, end;
    static final long THRESHOLD = 10000l;
    CalculateSumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0l;
        if (end - start <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (end + start) / 2;
            CalculateSumTask task1 = new CalculateSumTask(start, middle);
            task1.fork();
            CalculateSumTask task2 = new CalculateSumTask(middle + 1, end);
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}
