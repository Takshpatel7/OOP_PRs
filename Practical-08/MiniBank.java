import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.util.Scanner;
import model.Account;
import model.BankInfo;
import model.Command;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.WithdrawRule;
import service.BankService;
import util.AnnotationValidator;
import util.CommandParser;
import util.Validator;

public class MiniBank
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        BankInfo bankInfo =
                new BankInfo("MiniBank", "Charusat Branch");

        System.out.println("=================================");
        System.out.println(bankInfo);
        System.out.println("MINIBANK");
        System.out.println("=================================");

        Customer customer = new Customer(
                "Riya",
                "riya@example.com",
                "9876543210"
        );

        customer.setAddress(
                new Customer.Address(
                        "University Road",
                        "Anand",
                        "388001"
                )
        );

        Account savings =
                new SavingsAccount("Riya", 5000, 1000);

        Account current =
                new CurrentAccount("Rahul", 3000, 2000);

        Account fixed =
                new FixedDepositAccount("Amit", 10000);

        Account[] accounts = { savings, current, fixed };

        System.out.println("\n--- Accounts / Polymorphism ---");

        for (Account account : accounts)
        {
            System.out.println(account);
            System.out.println(
                    "Interest Rate: "
                    + account.interestRate() + "%");

            System.out.println(
                    "Yearly Interest: "
                    + account.yearlyInterest());

            if (account instanceof FixedDepositAccount)
            {
                System.out.println(
                        "Type: Fixed Deposit (locked)");
            }
        }

        System.out.println("\n--- Exception Handling ---");

        try
        {
            savings.deposit(2000);

            System.out.println(
                    "Savings balance after deposit: "
                    + savings.getBalance());

            savings.withdraw(3000);

            System.out.println(
                    "Savings balance after withdrawal: "
                    + savings.getBalance());
        }
        catch (InvalidAmountException e)
        {
            System.out.println(
                    "Invalid amount: " + e.getMessage());
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(
                    "Insufficient funds: " + e.getMessage());
            System.out.println(
                    "Shortfall: " + e.getShortfall());
        }
        finally
        {
            System.out.println(
                    "Deposit/withdraw operation completed.");
        }

        try
        {
            savings.withdraw(0);
        }
        catch (BankException e)
        {
            System.out.println(
                    "Handled invalid withdrawal: "
                    + e.getMessage());
        }
        finally
        {
            System.out.println(
                    "Invalid-withdrawal attempt completed.");
        }

        // Insufficient funds example.
        try
        {
            savings.withdraw(10000);
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(
                    "Handled insufficient funds: "
                    + e.getMessage());
            System.out.println(
                    "Shortfall: " + e.getShortfall());
        }
        catch (InvalidAmountException e)
        {
            System.out.println(
                    "Handled invalid amount: "
                    + e.getMessage());
        }
        finally
        {
            System.out.println(
                    "Large-withdrawal attempt completed.");
        }

        System.out.println("\n--- Transfer ---");

        try
        {
            BankService.transfer(
                    savings,
                    current,
                    1000
            );
        }
        catch (BankException e)
        {
            System.out.println(
                    "Transfer could not be completed: "
                    + e.getMessage());
        }
        finally
        {
            System.out.println("Transfer request completed.");
        }

        // Account-not-found example.
        try
        {
            Account missingAccount = findAccount(
                    accounts, "AC9999");

            BankService.transfer(
                    savings,
                    missingAccount,
                    500
            );
        }
        catch (AccountNotFoundException e)
        {
            System.out.println(
                    "Account error: " + e.getMessage());
        }
        catch (BankException e)
        {
            System.out.println(
                    "Bank error: " + e.getMessage());
        }
        finally
        {
            System.out.println(
                    "Account search/transfer attempt completed.");
        }

 

        WithdrawRule anonymousRule = new WithdrawRule()
        {
            @Override
            public boolean allow(Account account, long amount)
            {
                return account.canWithdraw(amount);
            }
        };

        WithdrawRule lambdaRule =
                (account, amount) ->
                        account.canWithdraw(amount);

        System.out.println(
                "Anonymous WithdrawRule: "
                + anonymousRule.allow(savings, 500));

        System.out.println(
                "Lambda WithdrawRule: "
                + lambdaRule.allow(savings, 500));

        System.out.println(
                "Premium account? "
                + (savings instanceof model.Premium));

        System.out.println("\n--- Validation ---");

        System.out.println(
                "Valid mobile: "
                + Validator.isValidMobile("9876543210"));

        System.out.println(
                "Invalid mobile: "
                + Validator.isValidMobile("12345"));

        System.out.println(
                "Valid email: "
                + Validator.isValidEmail(
                        "riya@example.com"));

        System.out.println(
                "Invalid email: "
                + Validator.isValidEmail(
                        "wrong-email"));

        System.out.println("\n--- Command Parser ---");

        Command command =
                CommandParser.parse(
                        "DEPOSIT AC0001 500");

        if (command != null)
        {
            System.out.println(
                    "Type: " + command.type());
            System.out.println(
                    "Account: " + command.accountNumber());
            System.out.println(
                    "Amount: " + command.amount());
        }

        System.out.println("\n--- Reflection Validation ---");

        Account invalidAccount =
                new SavingsAccount("Test", -100, 0);

        String[] errors =
                AnnotationValidator.validate(
                        invalidAccount);

        for (String error : errors)
        {
            System.out.println(error);
        }

        System.out.println("\n--- Statement ---");
        System.out.println(
                BankService.buildStatement(savings));

        System.out.println("\n--- Customer ---");
        System.out.println(
                "Customer ID: " + customer.getCustomerId());
        System.out.println(
                "Name: " + customer.getName());
        System.out.println(
                "Address: " + customer.getAddress());

        Customer clonedCustomer =
                customer.clone();

        System.out.println(
                "Clone created: "
                + (clonedCustomer != null));


        sc.close();
    }

    private static Account findAccount(
            Account[] accounts,
            String accountNumber)
            throws AccountNotFoundException
    {
        for (Account account : accounts)
        {
            if (account.getAccountNumber()
                    .equals(accountNumber))
            {
                return account;
            }
        }

        throw new AccountNotFoundException(
                "Account " + accountNumber
                + " was not found.");
    }
}
