import java.util.Scanner;

public class TollBooth
{
	record Vehicle(String number,String type){}

	public static void main(String[] arg)
	{
		Scanner sc=new Scanner(System.in);

		int totalToll=0;
		int bikeCount=0;
		int carCount=0;
		int truckCount=0;
		System.out.println("If you don't want to continue then type 'done'");
		while(true)
		{
			System.out.print("Enter vehicle number  : ");
			String number=sc.nextLine();
			if(number.equalsIgnoreCase("done"))
			{
				break;
			}
			System.out.print("Enter vehicle type  : ");
			String type=sc.nextLine().toLowerCase();
			Vehicle vehicle=new Vehicle(number,type);
			int toll;

			switch(vehicle.type())
			{
				case "bike" ->
				{
					bikeCount++;
					toll=20;
				}
				case "car" ->
				{
					carCount++;
					toll=50;
				}
				case "truck" ->
				{
					truckCount++;
					toll=150;
				}
				default ->
				{
					System.out.println("Invalid vehicle");
					toll=0;
				}
			}
			totalToll+=toll;
		}

		String f;
		if(bikeCount>=carCount&&bikeCount>=truckCount)
		{
			f="bike";
		}
		else if(carCount>=bikeCount&&carCount>=truckCount)
		{
			f="car";
		}
		else
		{
			f="truck";
		}
		System.out.println("\n:::::::::::::: Toll Booth :::::::::::::");
		System.out.println("Bike Count     : "+bikeCount);
		System.out.println("Car Count      : "+carCount);
		System.out.println("Truck Count    : "+truckCount);
		System.out.println("Total Toll     : "+totalToll);
		System.out.println("Most Frequent  : "+f);

		sc.close();
	}
}