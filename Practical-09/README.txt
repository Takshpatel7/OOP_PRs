PRACTICAL 9 - PART B: MINIBANK

This is an extension of Practical 8 MiniBank.

1. AccountWorker
----------------
AccountWorker implements Runnable and has:
    Account account
    int times
    long amount

run() loops for "times" and calls:
    account.deposit(amount)

2. Ten threads
--------------
MiniBankThreadDemo creates 10 AccountWorker threads on one shared
account and calls join() on every thread.

3. Race condition
-----------------
The first run uses RaceAccount. Its deposit() is intentionally
unsynchronized, so multiple threads can read the same old balance
and overwrite each other's updates.

4. Thread-safe MiniBank
-----------------------
The Practical 8 Account.java has been changed to:

    public synchronized void deposit(...)

and:

    public synchronized boolean withdraw(...)

The second run uses SavingsAccount, which inherits these synchronized
methods. The final balance is therefore correct.

5. Thread lifecycle
-------------------
Thread names are printed when:
    - the worker is created
    - the worker starts
    - the worker finishes
    - main joins the worker

COMPILE (JDK 17+)
-----------------
From Part-B-MiniBank:

javac MiniBankThreadDemo.java AccountWorker.java exception/*.java model/*.java model/annotation/*.java service/*.java util/*.java

RUN:
java MiniBankThreadDemo

Expected second-run balance:
10 threads x 1000 deposits x 10 = 100000

The exact first-run wrong balance can vary because thread scheduling
is controlled by the JVM/OS.
