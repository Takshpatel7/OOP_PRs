import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor
{
    private final ExecutorService executor;

    public TransactionProcessor()
    {
        // Fixed pool of exactly four worker threads.
        executor =
                Executors.newFixedThreadPool(4);
    }

    public void submit(Runnable transaction)
    {
        executor.execute(transaction);
    }

    public void stop()
            throws InterruptedException
    {
        executor.shutdown();

        if (!executor.awaitTermination(
                30,
                TimeUnit.SECONDS))
        {
            System.out.println(
                    "Timeout: stopping unfinished transactions.");

            executor.shutdownNow();
        }
        else
        {
            System.out.println(
                    "TransactionProcessor stopped cleanly.");
        }
    }
}
