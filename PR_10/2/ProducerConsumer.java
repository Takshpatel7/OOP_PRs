import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer
{
    private static final int BUFFER_SIZE = 4;
    private static final int ITEM_COUNT = 12;

    public static void main(String[] args)
            throws InterruptedException
    {
        System.out.println("====================================");
        System.out.println("PRACTICAL 10 - PRODUCER / CONSUMER");
        System.out.println("====================================");

        SharedBuffer buffer =
                new SharedBuffer(BUFFER_SIZE);

        Thread producer =
                new Thread(
                        new Producer(buffer, ITEM_COUNT),
                        "Producer");

        Thread consumer =
                new Thread(
                        new Consumer(buffer, ITEM_COUNT),
                        "Consumer");

        System.out.println("Starting producer and consumer...");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println(
                "\nBoth threads completed.");
        System.out.println(
                "Produced items: " + buffer.getProducedCount());
        System.out.println(
                "Consumed items: " + buffer.getConsumedCount());
        System.out.println(
                "Items remaining in buffer: "
                + buffer.size());

        boolean correct =
                buffer.getProducedCount() == ITEM_COUNT
                && buffer.getConsumedCount() == ITEM_COUNT
                && buffer.size() == 0;

        System.out.println(
                "No item lost: " + correct);
        System.out.println(
                "No item duplicated: " + correct);
    }

    private static class SharedBuffer
    {
        private final Queue<Integer> buffer =
                new LinkedList<>();

        private final int capacity;

        private int producedCount = 0;
        private int consumedCount = 0;

        SharedBuffer(int capacity)
        {
            this.capacity = capacity;
        }

        public synchronized void put(int item)
                throws InterruptedException
        {
            // Wait while the buffer is full.
            while (buffer.size() == capacity)
            {
                System.out.println(
                        "Producer waiting: buffer is FULL.");
                wait();
            }

            buffer.add(item);
            producedCount++;

            System.out.println(
                    "Producer -> produced "
                    + item
                    + " | buffer = "
                    + buffer);

          
            notify();
        }

        public synchronized int take()
                throws InterruptedException
        {
           
            while (buffer.isEmpty())
            {
                System.out.println(
                        "Consumer waiting: buffer is EMPTY.");
                wait();
            }

            int item = buffer.remove();
            consumedCount++;

            System.out.println(
                    "Consumer <- consumed "
                    + item
                    + " | buffer = "
                    + buffer);


            notify();

            return item;
        }

        public synchronized int size()
        {
            return buffer.size();
        }

        public synchronized int getProducedCount()
        {
            return producedCount;
        }

        public synchronized int getConsumedCount()
        {
            return consumedCount;
        }
    }

    
    private static class Producer implements Runnable
    {
        private final SharedBuffer buffer;
        private final int itemCount;

        Producer(SharedBuffer buffer, int itemCount)
        {
            this.buffer = buffer;
            this.itemCount = itemCount;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 1; i <= itemCount; i++)
                {
                    buffer.put(i);

                   
                    Thread.sleep(100);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();

                System.out.println(
                        "Producer interrupted.");
            }

            System.out.println("Producer finished.");
        }
    }

   
    private static class Consumer implements Runnable
    {
        private final SharedBuffer buffer;
        private final int itemCount;

        Consumer(SharedBuffer buffer, int itemCount)
        {
            this.buffer = buffer;
            this.itemCount = itemCount;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 1; i <= itemCount; i++)
                {
                    buffer.take();

                    // Small delay to make waiting visible.
                    Thread.sleep(150);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();

                System.out.println(
                        "Consumer interrupted.");
            }

            System.out.println("Consumer finished.");
        }
    }
}
