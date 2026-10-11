public class MiniBank {

    public static void main(String[] args) {

        System.out.println("Mobile valid: " +
            Validator.isValidMobile("9876543210"));

        System.out.println("Mobile valid: " +
            Validator.isValidMobile("12345"));

        System.out.println("Email valid: " +
            Validator.isValidEmail("riya@gmail.com"));

        System.out.println("PAN valid: " +
            Validator.isValidPan("ABCDE1234F"));

        System.out.println("IFSC valid: " +
            Validator.isValidIfsc("SBIN0001234"));

        Command command =
            CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("Type: " + command.type());
        System.out.println("Account: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());

        Account account = new Account("Riya", 5000);

        System.out.println(
            StatementFormatter.buildStatement(account)
        );
    }
}
