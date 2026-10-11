public class NotificationDemo
{
	public static void main(String[] args)
	{
		Notifier email=(message)->
			System.out.println("Email: "+message);

		Notifier sms=(message)->
			System.out.println("SMS: "+message);

		Notifier[] senders=
		{
			email,
			sms
		};

		String message="Your payment was successful.";

		System.out.println("----- NORMAL BROADCAST -----");

		for(Notifier sender:senders)
		{
			sender.send(message);
		}

		System.out.println("\n----- URGENT SENDERS -----");

		Notifier urgentEmail=new EmailNotifier();

		Notifier[] allSenders=
		{
			urgentEmail,
			sms
		};

		for(Notifier sender:allSenders)
		{
			sender.send(message);

			if(sender instanceof Urgent)
			{
				sender.send(message);
			}
		}
	}
}