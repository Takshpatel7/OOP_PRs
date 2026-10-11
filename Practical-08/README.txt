MINIBANK - PRACTICAL 8
Object Oriented Programming (CEUE203)

This folder is an updated version of the Practical-7 MiniBank project.
All previous Practical 1-7 source work is retained, and Practical 8
exception-handling work is added.

============================================================
PART A - PRACTICE PROGRAMS
============================================================

1. GuardedCalculator.java
- Reads two numbers and an operator.
- Uses custom checked DivideByZeroException.
- Separately catches invalid-number input using InputMismatchException.
- Handles invalid operators.
- Repeats until a valid calculation succeeds.
- finally logs that every attempt has completed.

2. Warehouse / Stock Issue
Files:
- Warehouse.java
- StockIssueDemo.java
- OutOfStockException.java
- InvalidQuantityException.java

- issue(item, qty) throws checked OutOfStockException when stock is too low.
- InvalidQuantityException is thrown when qty <= 0.
- A list of requests is processed.
- Every failure is caught and reported.
- One failed request does not stop later requests.
- OutOfStockException stores the shortfall.

3. AutoCloseable / try-with-resources
Files:
- TransactionLog.java
- AutoCloseableDemo.java

- TransactionLog models a bank transaction log as a resource.
- It implements AutoCloseable.
- try-with-resources closes it automatically.
- An exception is deliberately thrown inside the try block.
- The original exception remains the reported exception.
- This demonstrates automatic cleanup and exception handling.

============================================================
PART B - MINIBANK
============================================================

The Practical-7 MiniBank is extended with a checked exception hierarchy:

exception/BankException.java
    |
    +-- InsufficientFundsException.java
    +-- AccountNotFoundException.java
    +-- InvalidAmountException.java

1. Exception hierarchy
- BankException extends Exception.
- InsufficientFundsException, AccountNotFoundException and
  InvalidAmountException extend BankException.
- InsufficientFundsException stores the shortfall.

2. Account operations
- deposit(long amount) throws InvalidAmountException for amount <= 0.
- withdraw(long amount) declares:
    throws InsufficientFundsException, InvalidAmountException
- withdraw throws InvalidAmountException for amount <= 0.
- withdraw throws InsufficientFundsException when withdrawal is not allowed.

3. Transfer
- Account.transfer(Account to, long amount) declares throws BankException.
- It uses try, catch and finally.
- It rethrows BankException after reporting the failure.
- finally always reports that the transfer operation finished.
- BankService.transfer also propagates BankException.

4. Main program
- Uses try-catch blocks around operations.
- Uses friendly error messages.
- Uses finally blocks.
- Demonstrates invalid amount.
- Demonstrates insufficient funds and shortfall.
- Demonstrates account-not-found handling.
- Demonstrates a successful transfer.
- Retains important Practical 1-7 concepts.

============================================================
HOW TO COMPILE
============================================================

JDK 17 or newer is recommended.

From inside the Practical-8 folder:

javac MiniBank.java exception/*.java model/*.java model/annotation/*.java util/*.java service/*.java

Run:

java MiniBank

============================================================
PART A COMPILE AND RUN
============================================================

Guarded Calculator:

cd Part-A
javac DivideByZeroException.java GuardedCalculator.java
java GuardedCalculator

Warehouse:

javac OutOfStockException.java InvalidQuantityException.java Warehouse.java StockIssueDemo.java
java StockIssueDemo

AutoCloseable:

javac TransactionLog.java AutoCloseableDemo.java
java AutoCloseableDemo

============================================================
IMPORTANT
============================================================

The source files are intentionally kept simple and suitable for a
Practical 8 lab/project submission. Compiled .class files are not
included so the project can be compiled cleanly using the student's
own JDK.
