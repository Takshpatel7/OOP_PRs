import java.util.Scanner;

public class Driver1
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);

		String[] log={
			"10:05 alice Hello there",
			"10:06 bob How are you?",
			"10:07"
		};

		System.out.print("Enter keyword: ");
		String keyword=sc.nextLine();

		int matches=0;
		StringBuilder report=new StringBuilder();

		for(String line:log)
		{
			String[] parts=Chatfilter.splitLine(line);

			if(parts==null)
			{
				continue;
			}

			if(parts[2].toLowerCase().contains(keyword.toLowerCase()))
			{
				matches++;

				report.append(parts[0])
					  .append(" ")
					  .append(parts[1])
					  .append(": ")
					  .append(parts[2])
					  .append("\n");
			}
		}

		System.out.println("Matches: "+matches);
		System.out.println(report);

		sc.close();
	}
}