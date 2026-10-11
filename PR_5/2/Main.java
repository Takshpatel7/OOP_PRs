public class Main {

    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Rahul", 101, 50000),
            new PartTime("Amit", 102, 80, 300),
            new Intern("Priya", 103, 15000)
        };

        double total = 0;

        for (Employee e : employees) {

            double salary = e.monthlySalary();

            System.out.println(
                e.name + " (ID: " + e.id + ") Salary = Rs. " + salary
            );

            total += salary;

            if (e instanceof Intern) {
                System.out.println("Note: This employee is an Intern.");
            }
        }

        System.out.println("-------------------------");
        System.out.println("Total Payroll = Rs. " + total);
    }
}