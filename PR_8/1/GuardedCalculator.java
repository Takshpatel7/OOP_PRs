import java.util.Scanner;

class DivideByZeroException extends Exception
{
    public DivideByZeroException(String message)
    {
        super(message);
    }
}

public class GuardedCalculator
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        boolean success = false;
        int attempt = 0;

        while (!success)
        {
            attempt++;

            try
            {
                System.out.println("\n===== Calculator Attempt " + attempt + " =====");

                System.out.print("Enter first number: ");
                double num1 = Double.parseDouble(sc.nextLine());

                System.out.print("Enter second number: ");
                double num2 = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                char operator = sc.nextLine().charAt(0);

                double result;

                switch (operator)
                {
                    case '+':
                        result = num1 + num2;
                        break;

                    case '-':
                        result = num1 - num2;
                        break;

                    case '*':
                        result = num1 * num2;
                        break;

                    case '/':
                        if (num2 == 0)
                        {
                            throw new DivideByZeroException(
                                "Cannot divide by zero."
                            );
                        }

                        result = num1 / num2;
                        break;

                    default:
                        throw new IllegalArgumentException(
                            "Invalid operator. Use +, -, * or /."
                        );
                }

                System.out.println("Result = " + result);

                success = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println(
                    "Invalid number! Please enter valid numeric values."
                );
            }
            catch (DivideByZeroException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            finally
            {
                System.out.println("Attempt " + attempt + " completed.");
            }
        }

        System.out.println("\nCalculation completed successfully!");

        sc.close();
    }
}