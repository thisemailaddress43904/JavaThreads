import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunningAThreadThatRunsABlockingFunctionRepeatedlyWithFuturesOfWildcardType {
    private static AtomicBoolean running = new AtomicBoolean(true);

    static void functionThatBlocks() throws InterruptedException{
        System.out.println("Entered function that blocks");
        System.out.print("Enter some text: ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Leaving function that blocks");
    }

    static Thread t = new Thread(() -> {

            ;

    });

    static void main() throws InterruptedException {

        ExecutorService es = Executors.newSingleThreadExecutor();

        Future<?> future = es.submit(() -> {
           try {
               while (!Thread.currentThread().isInterrupted()){
                   functionThatBlocks();
               }
           } catch (InterruptedException ie){
               System.out.println("Task interrupted");
               Thread.currentThread().interrupt();
           }
        });

        Thread.sleep(2000);
        future.cancel(true);
        es.shutdown();
    }
}
