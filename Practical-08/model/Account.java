package model;

import exception.BankException;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;
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
    public void deposit(long amount) throws InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero.");
        }

        balance += amount;
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero.");
        }

        if (!canWithdraw(amount))
        {
            long available = Math.max(0, balance);
            long shortfall = amount - available;

            throw new InsufficientFundsException(
                    "Insufficient funds. Balance: " + balance
                    + ", requested: " + amount,
                    Math.max(0, shortfall));
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException
    {
        try
        {
            if (to == null)
            {
                throw new exception.AccountNotFoundException(
                        "Destination account was not found.");
            }

            withdraw(amount);
            to.deposit(amount);

            System.out.println(
                    "Transfer successful: " + amount
                    + " from " + getAccountNumber()
                    + " to " + to.getAccountNumber());
        }
        catch (BankException e)
        {
            System.out.println(
                    "Transfer failed: " + e.getMessage());
            throw e;
        }
        finally
        {
            System.out.println(
                    "Transfer operation finished for "
                    + getAccountNumber());
        }
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
