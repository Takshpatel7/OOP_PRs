import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner
{
    private static final int POOL_SIZE = 3;
    private static final int TASK_COUNT = 10;

    public static void main(String[] args)
            throws InterruptedException
    {
        System.out.println("====================================");
        System.out.println("PRACTICAL 10 - THREAD POOL RUNNER");
        System.out.println("====================================");

        ExecutorService executor =
                Executors.newFixedThreadPool(POOL_SIZE);

        List<String> taskNames = new ArrayList<>();

        for (int i = 1; i <= TASK_COUNT; i++)
        {
            final int taskNumber = i;
            String taskName = "Task-" + taskNumber;
            taskNames.add(taskName);

            executor.submit(() ->
            {
                String threadName =
                        Thread.currentThread().getName();

                System.out.println(
                        taskName + " STARTED by "
                        + threadName);

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();

                    System.out.println(
                            taskName
                            + " was interrupted by "
                            + threadName);
                    return;
                }

                System.out.println(
                        taskName + " FINISHED by "
                        + threadName);
            });
        }

        System.out.println(
                "\nSubmitted " + TASK_COUNT
                + " tasks to a fixed pool of "
                + POOL_SIZE + " threads.");

        executor.shutdown();

        if (executor.awaitTermination(30, TimeUnit.SECONDS))
        {
            System.out.println(
                    "\nAll submitted tasks completed.");
        }
        else
        {
            System.out.println(
                    "\nTasks did not finish within the time limit.");
            executor.shutdownNow();
        }

        System.out.println(
                "Thread pool runner completed.");
    }
}
