import model.Account;
import model.SavingsAccount;

public class MiniBankTransactionDemo
{
    public static void main(String[] args)
            throws Exception
    {
        Account account =
                new SavingsAccount(
                        "Transaction User",
                        1000,
                        0);

        TransactionProcessor processor =
                new TransactionProcessor();

        System.out.println(
                "=== MINIBANK TRANSACTION PROCESSOR ===");

        for (int i = 1; i <= 8; i++)
        {
            processor.submit(
                    new TransactionTask(
                            account,
                            100,
                            TransactionTask.Type.DEPOSIT));
        }

        for (int i = 1; i <= 3; i++)
        {
            processor.submit(
                    new TransactionTask(
                            account,
                            50,
                            TransactionTask.Type.WITHDRAW));
        }

        processor.stop();

        System.out.println(
                "\nFinal balance: "
                + account.getBalance());

        System.out.println(
                "Expected balance: 1650");

        System.out.println(
                "Correct: "
                + (account.getBalance() == 1650));
    }
}
