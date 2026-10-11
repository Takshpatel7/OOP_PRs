public class Thermostate {
    private String location;
    private int temperature;
    private static final int MIN=16;
    private static final int MAX=30;
    private static int activeCount=0;
    Thermostate(String l,int t)
    {
        activeCount++;
        this.location=l;
        temperature=t;
        if(temperature<=MAX && temperature>=MIN){}
        else
            temperature=22;
    }
    Thermostate(String location)
    {
        this(location,22);
    }
    public void raise()
    {
        if(temperature<=MAX)
            temperature++;
        else
            System.out.println("maximum temperature");
    }
    public void lower()
    {
        if(temperature>=MIN)
            temperature--;
        else
            System.out.println("minimum temperature");
    }
    public int getTemprature()
    {
        return temperature;
    }
    public static int getActiveCounts()
    {
        return activeCount;
    }
    public static void main(String[] args) {
        Thermostate t1 = new Thermostate("HALL",22);
        Thermostate t2 = new Thermostate("KITCHEN");
        System.out.println("Temprature Increases  : ");
        for(int i=0;i<=10;i++)
        {
            t1.raise();
            System.out.println(t1.getTemprature());
        } 
        System.out.println("Temprature Decreases  : ");
        for(int i=0;i<=20;i++)
        {
            t1.lower();
            System.out.println(t1.getTemprature());
        }
        System.out.println("Total Active counts  : "+t1.getActiveCounts());   
    }
}
