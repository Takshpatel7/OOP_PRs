public class EmailNotifier implements Notifier, Urgent
{
    public void send(String message)
    {
        System.out.println("Email: " + message);
    }
}