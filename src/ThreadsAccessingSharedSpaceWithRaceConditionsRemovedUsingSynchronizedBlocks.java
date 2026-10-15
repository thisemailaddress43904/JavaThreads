public class ThreadsAccessingSharedSpaceWithRaceConditionsRemovedUsingSynchronizedBlocks {
    static volatile int Counter = 0;
    static final Object CounterLockingObject = new Object();

    static void ModifyCounterBy(final int runThisManyTimes, final boolean inc){
        for (int i = 0; i < runThisManyTimes; i++) {
            if (inc)
                synchronized (CounterLockingObject){
                    ThreadsAccessingSharedSpaceWithRaceConditionsRemovedUsingSynchronizedBlocks.Counter++;
                }
            else
                synchronized (CounterLockingObject){
                    ThreadsAccessingSharedSpaceWithRaceConditionsRemovedUsingSynchronizedBlocks.Counter--;
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

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

        } catch (InterruptedException ie){}

        StringBuilder sb = new StringBuilder().append("Counter should equal " + 0 + " and equals " + Counter);
        System.out.println(sb);

    }
}
