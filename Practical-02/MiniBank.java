public class MiniBank {

    public static void main(String[] args) {

        Customer c1 = new Customer("Riya", "riya@gmail.com", "9876543210");

        Account[] accounts = {
            new Account("Riya", 5000),
            new Account("Aman", 3000),
            new Account("Neha")
        };

        accounts[0].deposit(2000);
        accounts[0].withdraw(3000);
        boolean result = accounts[0].withdraw(10000);

        for (Account account : accounts) {
            System.out.println(
                account.getAccountNumber() + " | " +
                account.getOwnerName() + " | Balance: Rs." +
                account.getBalance()
            );
        }

        System.out.println("Failed withdrawal: " + !result);
        System.out.println("Customer: " + c1.getCustomerId() + " | " + c1.getName());
    }
}
