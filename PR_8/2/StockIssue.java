import java.util.Arrays;
import java.util.List;

public class StockIssue {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        // Initial warehouse stock
        warehouse.addStock("Laptop", 10);
        warehouse.addStock("Mouse", 20);
        warehouse.addStock("Keyboard", 15);

        // List of issue requests
        List<Request> requests = Arrays.asList(
                new Request("Laptop", 5),
                new Request("Mouse", 25),
                new Request("Keyboard", 0),
                new Request("Monitor", 3),
                new Request("Keyboard", 10),
                new Request("Laptop", -2),
                new Request("Mouse", 5)
        );

        System.out.println("===== PROCESSING STOCK REQUESTS =====");

        // Process every request
        for (Request request : requests) {

            System.out.println(
                    "\nRequest: " +
                    request.quantity + " " +
                    request.item
            );

            try {
                warehouse.issue(
                        request.item,
                        request.quantity
                );
            }
            catch (OutOfStockException e) {
                System.out.println(
                        "FAILED: Not enough " + request.item +
                        ". Shortfall = " +
                        e.getShortfall()
                );
            }
            catch (InvalidQuantityException e) {
                System.out.println(
                        "FAILED: " + e.getMessage()
                );
            }
        }

        warehouse.displayStock();
    }
}
