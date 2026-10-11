import exception.BankException;
import model.Account;
import model.RaceAccount;
import model.SavingsAccount;

public class MiniBank
{
    private static final int THREAD_COUNT = 10;
    private static final int TIMES = 1_000;
    private static final long AMOUNT = 10;

    public static void main(String[] args)
            throws InterruptedException
    {
        long expected =
                (long) THREAD_COUNT * TIMES * AMOUNT;

        System.out.println("==================================");
        System.out.println("PRACTICAL 9 - PART B: MINIBANK");
        System.out.println("==================================");
        System.out.println("Threads: " + THREAD_COUNT);
        System.out.println("Deposits per thread: " + TIMES);
        System.out.println("Amount per deposit: " + AMOUNT);
        System.out.println("Expected balance: " + expected);

        // RUN 1: unsafe account to demonstrate race condition.
        Account unsafeAccount =
                new RaceAccount("Race User", 0);

        System.out.println(
                "\n--- RUN 1: WITHOUT SYNCHRONIZATION ---");

        runWorkers(unsafeAccount);

        System.out.println(
                "Expected final balance: " + expected);
        System.out.println(
                "Actual final balance:   "
                + unsafeAccount.getBalance());
        System.out.println(
                "Race condition observed: "
                + (unsafeAccount.getBalance() != expected));

        // RUN 2: Practical 8 Account now has synchronized
        // deposit() and withdraw().
        Account safeAccount =
                new SavingsAccount("Safe User", 0, 0);

        System.out.println(
                "\n--- RUN 2: WITH SYNCHRONIZATION ---");

        runWorkers(safeAccount);

        System.out.println(
                "Expected final balance: " + expected);
        System.out.println(
                "Actual final balance:   "
                + safeAccount.getBalance());
        System.out.println(
                "Balance is correct: "
                + (safeAccount.getBalance() == expected));

        // Also verify synchronized withdraw().
        System.out.println(
                "\n--- SYNCHRONIZED WITHDRAW TEST ---");

        try
        {
            safeAccount.withdraw(100);
            System.out.println("Withdrawal successful.");
            System.out.println(
                    "Balance after withdrawal: "
                    + safeAccount.getBalance());
        }
        catch (BankException e)
        {
            System.out.println(
                    "Withdrawal failed: " + e.getMessage());
        }

        System.out.println(
                "\nPractical 9 Part B completed.");
    }

    private static void runWorkers(Account account)
            throws InterruptedException
    {
        Thread[] threads =
                new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            AccountWorker worker =
                    new AccountWorker(
                            account,
                            TIMES,
                            AMOUNT);

            threads[i] =
                    new Thread(
                            worker,
                            "BankWorker-" + (i + 1));

            System.out.println(
                    "Creating "
                    + threads[i].getName());

            threads[i].start();
        }

        // join() makes main wait for every worker.
        for (Thread thread : threads)
        {
            thread.join();

            System.out.println(
                    thread.getName() + " joined.");
        }
    }
}
