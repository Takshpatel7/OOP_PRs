public class FullTime extends Employee {

    private double salary;

    public FullTime(String name, int id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    @Override
    public double monthlySalary() {
        return salary;
    }
}