import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RunningAThreadRepeatedlyAndAccessingSharedSpace {

    static AtomicBoolean continueRunning = new AtomicBoolean(true);
    static AtomicInteger numberOfTimesTheThreadHasRan = new AtomicInteger();
    static AtomicInteger Counter = new AtomicInteger(0);

    static void ModifyCounterBy(final int runThisManyTimes, final boolean inc){
        for (int i = 0; i < runThisManyTimes; i++) {
            if (inc){
                ThreadsAccessingSharedSpaceWithRaceConditionsRemovedByUsingAtomicVariables.Counter.addAndGet(1);
            }
            else{
                ThreadsAccessingSharedSpaceWithRaceConditionsRemovedByUsingAtomicVariables.Counter.addAndGet(-1);
            }
        }
    }

    static Thread t = new Thread(() -> {
        while (continueRunning.get()){
            ModifyCounterBy(100_000,true);
            numberOfTimesTheThreadHasRan.getAndIncrement();

            System.out.println("numberOfTimesTheThreadHasRan = " + numberOfTimesTheThreadHasRan);

            if (numberOfTimesTheThreadHasRan.get() > 5)
                continueRunning.set(false);
        }
    });

    static void main() {
        t.start();


    }
}
