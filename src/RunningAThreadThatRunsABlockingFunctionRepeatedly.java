import java.util.concurrent.atomic.AtomicBoolean;

public class RunningAThreadThatRunsABlockingFunctionRepeatedly {
    private static AtomicBoolean running = new AtomicBoolean(true);

    static void functionThatBlocks() throws InterruptedException{
        System.out.println("Entered function that blocks");
        Thread.sleep(10);
        System.out.println("Leaving function that blocks");
    }

    static Thread t = new Thread(() -> {
        try {
            while (running.get())
                functionThatBlocks();
        } catch (InterruptedException ie){
            System.out.println("Thread interrupted");
            //running.set(false);
        }
    });

    static void main() throws InterruptedException {

        t.start();
        
        Thread.sleep(100);
        t.interrupt();
    }
}
