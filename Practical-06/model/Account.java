package model;

public abstract class Account implements Transactable, InterestBearing {
    private static int counter = 1;

    private final String accountNumber;
    private String ownerName;
    protected long balance;
    private boolean active;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", counter++);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    @Override
    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    @Override
    public synchronized boolean withdraw(long amount) {
        if (amount <= 0 || !active || !canWithdraw(amount)) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public long balanceForInterest() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + balance;
    }
}
