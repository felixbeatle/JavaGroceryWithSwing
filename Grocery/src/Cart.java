import java.util.ArrayList;
import java.util.List;


public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    public double getTotalPriceWithTax() {
        double totalPrice = getTotalPrice();
        double taxRate = 0.10; // Assuming tax rate of 10%
        double totalTax = totalPrice * taxRate;
        return totalPrice + totalTax;
    }
}
