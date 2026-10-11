public class MiniBank {

    public static void main(String[] args) {

        Account a1 = new Account("Riya", 4000);
        Account a2 = new Account(a1);

        Customer customer = new Customer(
            "Riya",
            "riya@gmail.com",
            "9876543210"
        );

        Customer.Address address = new Customer.Address(
            "12 Main Road",
            "Delhi",
            "110001"
        );

        customer.setAddress(address);

        System.out.println("Account 1: " + a1);
        System.out.println("Account 2: " + a2);
        System.out.println("a1.equals(a2): " + a1.equals(a2));

        Object obj = a1;
        System.out.println("obj instanceof Account: " + (obj instanceof Account));

        Customer copy = customer.clone();
        System.out.println("Customer clone: " + copy.getCustomerId());
        System.out.println("Address: " + customer.getAddress());
    }
}
