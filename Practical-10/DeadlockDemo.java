import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;

import model.Account;
import model.SavingsAccount;

public class DeadlockDemo
{
    public static void main(String[] args)
            throws Exception
    {
        // Use separate accounts for the unsafe demonstration.
        // They become intentionally locked, so they must not be
        // reused for the corrected transfer.
        Account unsafeA =
                new SavingsAccount("Unsafe-A", 1000, 0);

        Account unsafeB =
                new SavingsAccount("Unsafe-B", 1000, 0);

        System.out.println(
                "=== DEADLOCK: OPPOSITE LOCK ORDER ===");

        System.out.println(
                "Unsafe A: "
                + unsafeA.getAccountNumber());

        System.out.println(
                "Unsafe B: "
                + unsafeB.getAccountNumber());

        demonstrateDeadlock(
                unsafeA,
                unsafeB);

        // Fresh accounts for the corrected version.
        Account safeA =
                new SavingsAccount("Safe-A", 1000, 0);

        Account safeB =
                new SavingsAccount("Safe-B", 1000, 0);

        System.out.println(
                "\n=== FIX: SMALLER ACCOUNT NUMBER FIRST ===");

        Thread t1 = new Thread(() ->
        {
            try
            {
                safeTransfer(
                        safeA,
                        safeB,
                        100);
            }
            catch (Exception e)
            {
                System.out.println(
                        "T1 failed: "
                        + e.getMessage());
            }
        }, "Safe-Transfer-A-to-B");

        Thread t2 = new Thread(() ->
        {
            try
            {
                safeTransfer(
                        safeB,
                        safeA,
                        100);
            }
            catch (Exception e)
            {
                System.out.println(
                        "T2 failed: "
                        + e.getMessage());
            }
        }, "Safe-Transfer-B-to-A");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(
                "\nBoth safe transfers completed.");

        System.out.println(
                safeA.getAccountNumber()
                + " balance = "
                + safeA.getBalance());

        System.out.println(
                safeB.getAccountNumber()
                + " balance = "
                + safeB.getBalance());
    }

    private static void demonstrateDeadlock(
            Account a,
            Account b)
            throws InterruptedException
    {
        CountDownLatch bothFirstLocks =
                new CountDownLatch(2);

        Thread t1 = new Thread(() ->
        {
            synchronized (a)
            {
                System.out.println(
                        Thread.currentThread().getName()
                        + " locked "
                        + a.getAccountNumber());

                bothFirstLocks.countDown();
                await(bothFirstLocks);

                // Opposite order: a -> b.
                synchronized (b)
                {
                    System.out.println(
                            "T1 acquired both locks.");
                }
            }
        }, "Transfer-A-to-B");

        Thread t2 = new Thread(() ->
        {
            synchronized (b)
            {
                System.out.println(
                        Thread.currentThread().getName()
                        + " locked "
                        + b.getAccountNumber());

                bothFirstLocks.countDown();
                await(bothFirstLocks);

                // Opposite order: b -> a.
                synchronized (a)
                {
                    System.out.println(
                            "T2 acquired both locks.");
                }
            }
        }, "Transfer-B-to-A");

        // Daemon threads make the intentional deadlock harmless.
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        Thread.sleep(500);

        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();

        long[] ids =
                bean.findDeadlockedThreads();

        if (ids != null)
        {
            System.out.println(
                    "Deadlock detected: "
                    + ids.length
                    + " threads.");

            ThreadInfo[] info =
                    bean.getThreadInfo(ids);

            for (ThreadInfo thread : info)
            {
                if (thread != null)
                {
                    System.out.println(
                            "Blocked: "
                            + thread.getThreadName());
                }
            }

            System.out.println(
                    "Reason: each thread holds one account "
                    + "lock and waits for the other.");
        }
        else
        {
            System.out.println(
                    "Deadlock was not detected in this run.");
        }
    }

    private static void await(
            CountDownLatch latch)
    {
        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    /*
     * Correct solution:
     * both directions lock the smaller accountNumber first.
     */
    public static void safeTransfer(
            Account from,
            Account to,
            long amount)
            throws Exception
    {
        Account first;
        Account second;

        if (from.getAccountNumber()
                .compareTo(to.getAccountNumber()) < 0)
        {
            first = from;
            second = to;
        }
        else
        {
            first = to;
            second = from;
        }

        synchronized (first)
        {
            synchronized (second)
            {
                from.withdraw(amount);
                to.deposit(amount);

                System.out.println(
                        Thread.currentThread().getName()
                        + " transferred "
                        + amount
                        + " | lock order: "
                        + first.getAccountNumber()
                        + " -> "
                        + second.getAccountNumber());
            }
        }
    }
}
