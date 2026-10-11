package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class RaceAccount extends Account
{
    public RaceAccount(String ownerName, long balance)
    {
        super(ownerName, balance);
    }

    // Deliberately UNSYNCHRONIZED for the first race-condition demo.
    @Override
    public void deposit(long amount)
            throws InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero.");
        }

        long oldBalance = balance;
        Thread.yield();
        balance = oldBalance + amount;
    }

    @Override
    public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero.");
        }

        if (!canWithdraw(amount))
        {
            long shortfall =
                    amount - Math.max(0, balance);

            throw new InsufficientFundsException(
                    "Insufficient funds.",
                    Math.max(0, shortfall));
        }

        balance -= amount;
        return true;
    }

    @Override
    public double interestRate()
    {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount)
    {
        return amount >= 0 && balance >= amount;
    }
}
