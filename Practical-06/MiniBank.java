import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Premium;
import model.SavingsAccount;
import model.WithdrawRule;
import service.AccountService;

import static util.Validator.isValidMobile;

public class MiniBank {

    public static void main(String[] args) {

        System.out.println("========== MiniBank - Practical 6 ==========");

        Account savings = new SavingsAccount("Riya", 5000, 1000);
        Account current = new CurrentAccount("Aarav", 2000, 5000);
        Account fixedDeposit = new FixedDepositAccount("Meera", 10000);

        Account[] accounts = {savings, current, fixedDeposit};

        System.out.println("\n--- Account Details ---");
        for (Account account : accounts) {
            AccountService.printAccount(account);
        }

        System.out.println("--- WithdrawRule: Anonymous Class ---");

        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && amount <= account.getBalance();
            }
        };

        System.out.println(
                "Anonymous rule withdrawal: "
                        + AccountService.withdraw(savings, 1000, anonymousRule)
        );

        System.out.println("\n--- WithdrawRule: Lambda ---");

        WithdrawRule lambdaRule =
                (account, amount) -> amount > 0 && amount <= 2000;

        System.out.println(
                "Lambda rule withdrawal: "
                        + AccountService.withdraw(current, 1500, lambdaRule)
        );

        System.out.println("\n--- Premium Marker Interface ---");

        Premium premiumCustomer = new Premium() {
        };

        if (premiumCustomer instanceof Premium) {
            System.out.println("Premium object detected.");
        }

        System.out.println("\n--- Static Import Example ---");
        System.out.println("9876543210 valid mobile: " + isValidMobile("9876543210"));
        System.out.println("12345 valid mobile: " + isValidMobile("12345"));

        System.out.println("\n--- Final Balances ---");
        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\nMiniBank finished.");
    }
}
