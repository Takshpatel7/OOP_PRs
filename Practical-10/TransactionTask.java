import exception.BankException;
import model.Account;

public class TransactionTask implements Runnable
{
    public enum Type
    {
        DEPOSIT,
        WITHDRAW
    }

    private final Account account;
    private final long amount;
    private final Type type;

    public TransactionTask(
            Account account,
            long amount,
            Type type)
    {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public void run()
    {
        String threadName =
                Thread.currentThread().getName();

        try
        {
            if (type == Type.DEPOSIT)
            {
                account.deposit(amount);

                System.out.println(
                        threadName
                        + " -> DEPOSIT "
                        + amount
                        + " | balance="
                        + account.getBalance());
            }
            else
            {
                account.withdraw(amount);

                System.out.println(
                        threadName
                        + " -> WITHDRAW "
                        + amount
                        + " | balance="
                        + account.getBalance());
            }
        }
        catch (BankException e)
        {
            System.out.println(
                    threadName
                    + " -> transaction failed: "
                    + e.getMessage());
        }
    }
}
