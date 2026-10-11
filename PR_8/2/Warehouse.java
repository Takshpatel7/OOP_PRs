import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private Map<String, Integer> stock = new HashMap<>();

    public void addStock(String item, int qty) {
        stock.put(item, stock.getOrDefault(item, 0) + qty);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Invalid quantity: " + qty +
                    ". Quantity must be greater than 0."
            );
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            int shortfall = qty - available;
            throw new OutOfStockException(shortfall);
        }

        stock.put(item, available - qty);

        System.out.println(
                "SUCCESS: Issued " + qty + " " + item +
                ". Remaining stock: " + (available - qty)
        );
    }

    public void displayStock() {
        System.out.println("\nCurrent Warehouse Stock:");

        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }
    }
}
