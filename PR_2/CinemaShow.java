public class CinemaShow {

    private String title;
    private int availableseats;
    private final int capacity;
    private static int booked=0;

    CinemaShow(String title,int capacity)
    {
        this.title=title;
        this.capacity=capacity;
        this.availableseats=capacity;
    }
    CinemaShow(String title)
    {
        this(title,100);
    }
    public String book(int n) 
    {
        if (n<=availableseats)
        {
            availableseats-= n;
            booked+= n;
            return "Booked";
        } 
        else 
        {
            return "Not Available";
        }
    }
    public void cancel(int n) 
    {
        availableseats+= n;
        if (availableseats>capacity) 
        {
            availableseats=capacity;
        }
    }

    public int getSeatsAvailable() 
    {
        return availableseats;
    }

    public static int getbooked() 
    {
        return booked;
    }

    public static void main(String[] args) {

        CinemaShow show = new CinemaShow("Avengers", 100);

        System.out.println("Movie: "+show.title);
        System.out.println("Initial Seats: "+show.getSeatsAvailable());

        System.out.println("Book 30 Seats : "+show.book(30));
        System.out.println("Seats Available : "+show.getSeatsAvailable());

        System.out.println("Book 50 Seats : "+show.book(50));
        System.out.println("Seats Available : "+show.getSeatsAvailable());

        System.out.println("Book 40 Seats : "+show.book(40));
        System.out.println("Seats Available : "+show.getSeatsAvailable());

        show.cancel(20);
        System.out.println("Cancel 20 Seats");
        System.out.println("Seats Available : " + show.getSeatsAvailable());

        show.cancel(50);
        System.out.println("Cancel 50 Seats");
        System.out.println("Seats Available : " + show.getSeatsAvailable());

        System.out.println("Total booked seats : " + CinemaShow.getbooked());
    }
}