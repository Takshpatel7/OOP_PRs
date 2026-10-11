import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingRace
{
    private static final int TOTAL_SEATS = 5;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args)
            throws InterruptedException
    {
        System.out.println("=== Practical 9: Seat Booking Race ===");
        System.out.println("Available seats: " + TOTAL_SEATS);
        System.out.println("Booking threads: " + THREAD_COUNT);

        runWithoutSynchronization();
        runWithSynchronization();
    }

    private static void runWithoutSynchronization()
            throws InterruptedException
    {
        UnsafeBooking booking =
                new UnsafeBooking(TOTAL_SEATS);

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            final int customerId = i + 1;

            threads[i] = new Thread(() ->
            {
                if (booking.bookSeat())
                {
                    System.out.println(
                            "Customer " + customerId
                            + " booked a seat.");
                }
            });
        }

        for (Thread thread : threads)
        {
            thread.start();
        }

        for (Thread thread : threads)
        {
            thread.join();
        }

        System.out.println("\n--- Without Synchronization ---");
        System.out.println(
                "Successful bookings: "
                + booking.getSuccessfulBookings());
        System.out.println(
                "Expected maximum: " + TOTAL_SEATS);

        if (booking.getSuccessfulBookings() > TOTAL_SEATS)
        {
            System.out.println(
                    "Oversell occurred: MORE THAN 5 bookings succeeded.");
        }
        else
        {
            System.out.println(
                    "This run did not visibly oversell, but the code "
                    + "is still unsafe.");
        }
    }

    private static void runWithSynchronization()
            throws InterruptedException
    {
        SafeBooking booking =
                new SafeBooking(TOTAL_SEATS);

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++)
        {
            final int customerId = i + 1;

            threads[i] = new Thread(() ->
            {
                if (booking.bookSeat())
                {
                    System.out.println(
                            "Customer " + customerId
                            + " booked a seat.");
                }
            });
        }

        for (Thread thread : threads)
        {
            thread.start();
        }

        for (Thread thread : threads)
        {
            thread.join();
        }

        System.out.println("\n--- With Synchronization ---");
        System.out.println(
                "Successful bookings: "
                + booking.getSuccessfulBookings());
        System.out.println(
                "Expected: exactly " + TOTAL_SEATS);
        System.out.println(
                "Exactly 5 succeeded: "
                + (booking.getSuccessfulBookings() == TOTAL_SEATS));
    }

    /*
     * Deliberately unsafe version.
     * The small pause between checking and booking makes the
     * check-then-act race easier to observe.
     */
    private static class UnsafeBooking
    {
        private final int totalSeats;
        private int successfulBookings = 0;

        UnsafeBooking(int totalSeats)
        {
            this.totalSeats = totalSeats;
        }

        boolean bookSeat()
        {
            if (successfulBookings < totalSeats)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }

                successfulBookings++;
                return true;
            }

            return false;
        }

        int getSuccessfulBookings()
        {
            return successfulBookings;
        }
    }

    /*
     * Synchronized version.
     * The complete check-and-update operation is atomic with
     * respect to the other booking threads.
     */
    private static class SafeBooking
    {
        private final AtomicInteger successfulBookings =
                new AtomicInteger(0);

        private final int totalSeats;

        SafeBooking(int totalSeats)
        {
            this.totalSeats = totalSeats;
        }

        synchronized boolean bookSeat()
        {
            if (successfulBookings.get() >= totalSeats)
            {
                return false;
            }

            successfulBookings.incrementAndGet();
            return true;
        }

        int getSuccessfulBookings()
        {
            return successfulBookings.get();
        }
    }
}
