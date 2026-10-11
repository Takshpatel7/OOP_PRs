public class MiniBank {

    public static void main(String[] args) {

        Account[] accounts = {
            new SavingsAccount("Riya", 10000, 2000),
            new CurrentAccount("Aman", 5000, 3000),
            new FixedDepositAccount("Neha", 20000)
        };

        double totalInterestRate = 0;

        for (Account account : accounts) {

            System.out.println(
                account.getAccountNumber() +
                " | " +
                account.getOwnerName() +
                " | Interest Rate: " +
                account.interestRate()
            );

            totalInterestRate += account.interestRate();

            if (account instanceof FixedDepositAccount) {
                System.out.println("Note: Fixed deposit is locked.");
            }
        }

        System.out.println("Total of rates: " + totalInterestRate);

        System.out.println(
            "Savings withdrawal: " +
            accounts[0].withdraw(7000)
        );

        System.out.println(
            "Current withdrawal: " +
            accounts[1].withdraw(7000)
        );

        System.out.println(
            "Fixed deposit withdrawal: " +
            accounts[2].withdraw(1000)
        );
    }
}
