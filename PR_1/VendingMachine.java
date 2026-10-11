import java.util.Scanner;

public class VendingMachine {
    enum Coins{
        ONE,
        TWO,
        FIVE,
        TEN
    }

    public static void main(String[] args) {

        Coins coin1 = Coins.ONE;
        Coins coin2 = Coins.TWO;
        Coins coin5 = Coins.FIVE;
        Coins coin10 = Coins.TEN;

        Scanner sc = new Scanner(System.in);
        int total = 0,k;
        int snake_price = 15;

        System.out.println(".()");

        while(total < snake_price)
        {
            System.out.println("Enter Coins : ");

            Coins coin = Coins.valueOf(sc.nextLine().toUpperCase());

            switch(coin)
            {
                case ONE:
                    total += 1;
                    System.out.println("You pressed ONE");
                    break;

                case TWO:
                    total += 2;
                    System.out.println("You pressed TWO");
                    break;

                case FIVE:
                    total += 5;
                    System.out.println("You pressed FIVE");
                    break;

                case TEN:
                    total += 10;
                    System.out.println("You pressed TEN");
                    break;
                default:
                    System.out.println("Invalid Coin. Try Again! ");
            }
        }

        System.out.println("Paid . Change : " + (total - snake_price));

        sc.close();
    }
}