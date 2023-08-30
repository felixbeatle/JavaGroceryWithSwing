public class Grocery {
    private String articlename;
    private int quantity;
    private double price;
    private String type;

    public Grocery(String articlename, int quantity, double price, String type) {
        this.articlename = articlename;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void displayGrocery() {
        System.out.println("Articlename: " + articlename + " Quantity: " + quantity + " Price: " + price);
    }
}
