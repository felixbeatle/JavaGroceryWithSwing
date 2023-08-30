public class SnackBuilder {
    private String articlename;
    private int quantity;
    private double price;
    private String type;
    private String expirationDate;

    public SnackBuilder(String articlename, int quantity, double price, String type, String expirationDate) {
        this.articlename = articlename;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.expirationDate = expirationDate;
    }
    public Snacks build() {
        return new Snacks(articlename, quantity, price, type, expirationDate);
    }

    public SnackBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public SnackBuilder withExpirationdate(String expirationdate) {
        this.expirationDate = expirationdate;
        return this;
    }
}