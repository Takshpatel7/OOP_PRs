package model;

public class SavingsAccount extends Account implements Premium
{
    private long minBalance;

    public SavingsAccount(String ownerName, long balance, long minBalance)
    {
        super(ownerName, balance);
        this.minBalance = minBalance;
    }

    public SavingsAccount(String ownerName)
    {
        this(ownerName, 0, 1000);
    }

    @Override
    public double interestRate()
    {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount)
    {
        return amount >= 0 && balance - amount >= minBalance;
    }
}
