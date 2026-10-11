public class CounterRace
{
    private static int counter = 0;

    private static final int THREAD_COUNT = 10;
    private static final int INCREMENTS_PER_THREAD = 100_000;

    public static void main(String[] args)
            throws InterruptedException
    {
        Thread[] threads = new Thread[THREAD_COUNT];

        System.out.println("=== Practical 9: Counter Race ===");
        System.out.println("Threads: " + THREAD_COUNT);
        System.out.println(
                "Increments per thread: " + INCREMENTS_PER_THREAD);

        // Unsynchronized run: demonstrates the race condition.
        counter = 0;

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            threads[i] = new Thread(() ->
            {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++)
                {
                    counter++;
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads)
        {
            thread.join();
        }

        int expected =
                THREAD_COUNT * INCREMENTS_PER_THREAD;

        System.out.println("\n--- Without Synchronization ---");
        System.out.println("Expected counter: " + expected);
        System.out.println("Actual counter:   " + counter);
        System.out.println(
                "Race condition occurred: " + (counter != expected));

        // Synchronized run: every increment is protected.
        counter = 0;

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            threads[i] = new Thread(() ->
            {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++)
                {
                    incrementSynchronized();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads)
        {
            thread.join();
        }

        System.out.println("\n--- With Synchronization ---");
        System.out.println("Expected counter: " + expected);
        System.out.println("Actual counter:   " + counter);
        System.out.println(
                "Exactly right: " + (counter == expected));
    }

    private static synchronized void incrementSynchronized()
    {
        counter++;
    }
}
