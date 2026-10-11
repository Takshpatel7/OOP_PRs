package model;

import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

public abstract class Account
        implements Transactable, InterestBearing
{
    @Id
    private final String accountNumber;

    @MaxLength(30)
    private String ownerName;

    @Positive
    protected long balance;

    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber()
    {
        return String.format("AC%04d", accountCounter++);
    }

    public Account(String ownerName, long openingBalance)
    {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName)
    {
        this(ownerName, 0);
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public long getBalance()
    {
        return balance;
    }

    public boolean isActive()
    {
        return active;
    }

    @Override
    public void deposit(long amount)
    {
        if (amount > 0)
        {
            balance += amount;
        }
    }

    @Override
    public boolean withdraw(long amount)
    {
        if (amount > 0 && canWithdraw(amount))
        {
            balance -= amount;
            return true;
        }

        return false;
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public double balanceForInterest()
    {
        return balance;
    }

    @Override
    public String toString()
    {
        return accountNumber + " | "
                + ownerName + " | "
                + balance + " | active=" + active;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof Account))
        {
            return false;
        }

        Account other = (Account) obj;

        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode()
    {
        return accountNumber.hashCode();
    }
}
