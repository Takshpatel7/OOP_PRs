public class TransactionBufferDemo
{
    private static final int BUFFER_SIZE = 4;
    private static final int TRANSACTION_COUNT = 12;

    public static void main(String[] args)
            throws InterruptedException
    {
        TransactionBuffer buffer =
                new TransactionBuffer(BUFFER_SIZE);

        Thread producer =
                new Thread(
                        new Producer(buffer),
                        "Transaction-Producer");

        Thread consumer =
                new Thread(
                        new Consumer(buffer),
                        "Transaction-Consumer");

        System.out.println(
                "=== TRANSACTION PRODUCER-CONSUMER ===");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println(
                "\nProduced: "
                + buffer.getProducedCount());

        System.out.println(
                "Consumed: "
                + buffer.getConsumedCount());

        System.out.println(
                "Remaining: " + buffer.size());

        boolean correct =
                buffer.getProducedCount()
                        == TRANSACTION_COUNT
                && buffer.getConsumedCount()
                        == TRANSACTION_COUNT
                && buffer.size() == 0;

        System.out.println(
                "Correct result: " + correct);
    }

    private static class Transaction
    {
        private final int id;
        private final long amount;

        Transaction(int id, long amount)
        {
            this.id = id;
            this.amount = amount;
        }

        @Override
        public String toString()
        {
            return "Transaction{id="
                    + id
                    + ", amount="
                    + amount
                    + "}";
        }
    }

    /*
     * Fixed-size ARRAY buffer.
     * head/tail make it a circular FIFO queue.
     */
    private static class TransactionBuffer
    {
        private final Transaction[] buffer;

        private int head;
        private int tail;
        private int count;

        private int produced;
        private int consumed;

        TransactionBuffer(int capacity)
        {
            buffer = new Transaction[capacity];
        }

        public synchronized void put(
                Transaction transaction)
                throws InterruptedException
        {
            while (count == buffer.length)
            {
                System.out.println(
                        "Producer waits: buffer FULL.");
                wait();
            }

            buffer[tail] = transaction;
            tail = (tail + 1) % buffer.length;
            count++;
            produced++;

            System.out.println(
                    "Produced -> "
                    + transaction
                    + " | bufferCount="
                    + count);

            notify();
        }

        public synchronized Transaction take()
                throws InterruptedException
        {
            while (count == 0)
            {
                System.out.println(
                        "Consumer waits: buffer EMPTY.");
                wait();
            }

            Transaction transaction =
                    buffer[head];

            buffer[head] = null;
            head = (head + 1) % buffer.length;
            count--;
            consumed++;

            System.out.println(
                    "Consumed <- "
                    + transaction
                    + " | bufferCount="
                    + count);

            notify();

            return transaction;
        }

        public synchronized int size()
        {
            return count;
        }

        public synchronized int getProducedCount()
        {
            return produced;
        }

        public synchronized int getConsumedCount()
        {
            return consumed;
        }
    }

    private static class Producer implements Runnable
    {
        private final TransactionBuffer buffer;

        Producer(TransactionBuffer buffer)
        {
            this.buffer = buffer;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 1;
                        i <= TRANSACTION_COUNT;
                        i++)
                {
                    buffer.put(
                            new Transaction(
                                    i,
                                    i * 100L));

                    Thread.sleep(60);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }

            System.out.println(
                    "Transaction producer finished.");
        }
    }

    private static class Consumer implements Runnable
    {
        private final TransactionBuffer buffer;

        Consumer(TransactionBuffer buffer)
        {
            this.buffer = buffer;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 1;
                        i <= TRANSACTION_COUNT;
                        i++)
                {
                    buffer.take();
                    Thread.sleep(90);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }

            System.out.println(
                    "Transaction consumer finished.");
        }
    }
}
