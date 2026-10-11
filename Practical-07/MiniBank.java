import java.util.Scanner;
import model.Account;
import model.BankInfo;
import model.Command;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.MenuOption;
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

        BankInfo bankInfo = new BankInfo("MiniBank", "Charusat Branch");
        System.out.println("=================================");
        System.out.println(bankInfo);
        System.out.println("=================================");

        Customer customer = new Customer(
                "Riya",
                "riya@example.com",
                "9876543210"
        );
        customer.setAddress(
                new Customer.Address("University Road", "Anand", "388001")
        );

        Account savings = new SavingsAccount("Riya", 5000, 1000);
        Account current = new CurrentAccount("Rahul", 3000, 2000);
        Account fixed = new FixedDepositAccount("Amit", 10000);

        Account[] accounts = { savings, current, fixed };

        System.out.println("\n--- Accounts / Polymorphism ---");
        for (Account account : accounts)
        {
            System.out.println(account);
            System.out.println("Interest Rate: " + account.interestRate() + "%");
            System.out.println("Yearly Interest: " + account.yearlyInterest());

            if (account instanceof FixedDepositAccount)
            {
                System.out.println("Type: Fixed Deposit (locked)");
            }
        }

        System.out.println("\n--- Deposit / Withdraw ---");
        savings.deposit(2000);
        System.out.println("Savings balance after deposit: " + savings.getBalance());

        System.out.println("Withdraw 3000: " + savings.withdraw(3000));
        System.out.println("Savings balance: " + savings.getBalance());

        System.out.println("Withdraw 10000: " + savings.withdraw(10000));
        System.out.println("Savings balance: " + savings.getBalance());

        System.out.println("\n--- Interfaces ---");

        WithdrawRule anonymousRule = new WithdrawRule()
        {
            @Override
            public boolean allow(Account account, long amount)
            {
                return account.canWithdraw(amount);
            }
        };

        WithdrawRule lambdaRule =
                (account, amount) -> account.canWithdraw(amount);

        System.out.println("Anonymous WithdrawRule: "
                + anonymousRule.allow(savings, 500));

        System.out.println("Lambda WithdrawRule: "
                + lambdaRule.allow(savings, 500));

        System.out.println("Premium account? "
                + (savings instanceof model.Premium));

        System.out.println("\n--- Validation");
        System.out.println("Valid mobile: "
                + Validator.isValidMobile("9876543210"));
        System.out.println("Invalid mobile: "
                + Validator.isValidMobile("12345"));
        System.out.println("Valid email: "
                + Validator.isValidEmail("riya@example.com"));
        System.out.println("Invalid email: "
                + Validator.isValidEmail("wrong-email"));

        System.out.println("\n--- Command Parser ---");
        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        if (command != null)
        {
            System.out.println("Type: " + command.type());
            System.out.println("Account: " + command.accountNumber());
            System.out.println("Amount: " + command.amount());
        }

        System.out.println("\n--- Reflection");
        Account invalidAccount = new SavingsAccount("Test", -100, 0);
        String[] errors = AnnotationValidator.validate(invalidAccount);

        if (errors.length == 0)
        {
            System.out.println("No validation errors");
        }
        else
        {
            for (String error : errors)
            {
                System.out.println(error);
            }
        }

        System.out.println("\n--- Valid Account Validation ---");
        String[] validErrors = AnnotationValidator.validate(savings);

        if (validErrors.length == 0)
        {
            System.out.println("No validation errors");
        }
        else
        {
            for (String error : validErrors)
            {
                System.out.println(error);
            }
        }

        System.out.println("\n--- Statement ---");
        System.out.println(BankService.buildStatement(savings));

        System.out.println("\n--- Customer ---");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());

        Customer clonedCustomer = customer.clone();
        System.out.println("Clone created: "
                + (clonedCustomer != null));

        System.out.println("\n--- Menu Shell");
        runMenu(sc);

        sc.close();
    }

    private static void runMenu(Scanner sc)
    {
        boolean running = true;

        
    }
}
