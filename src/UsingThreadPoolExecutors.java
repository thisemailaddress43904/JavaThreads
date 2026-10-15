import java.util.concurrent.*;

public class UsingThreadPoolExecutors {
    static volatile int Counter = 0;
    static final Object CounterLockingObject = new Object();

    static void ModifyCounterBy(final int runThisManyTimes, final boolean inc){
        for (int i = 0; i < runThisManyTimes; i++) {
            if (inc)
                synchronized (CounterLockingObject){
                    UsingThreadPoolExecutors.Counter++;
                }
            else
                synchronized (CounterLockingObject){
                    UsingThreadPoolExecutors.Counter--;
                }
        }
    }

    static void main() {
        int numberOfTimesEachThreadShouldRun = 10_000_000;
        Thread t1 = new Thread(() -> {
           ModifyCounterBy(numberOfTimesEachThreadShouldRun, true);
        });
        Thread t2 = new Thread(() -> {
            ModifyCounterBy(numberOfTimesEachThreadShouldRun, false);
        });

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,0L,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        executor.submit(t1);
        executor.submit(t2);

        executor.shutdown();

        StringBuilder sb = new StringBuilder().
                append("Counter should equal").
                append(0).
                append(" and equals ").
                append(Counter);

        System.out.println(sb);

    }
}
