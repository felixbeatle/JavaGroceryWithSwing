public class alcoolbuilder {
    private String articlename;
    private int quantity;
    private double price;
    private String type;
    private String expirationDate;

    public alcoolbuilder(String articlename, int quantity, double price, String type, String expirationDate) {
        this.articlename = articlename;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.expirationDate = expirationDate;

    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Alcool build() {
        return new Alcool(articlename, quantity, price, type, expirationDate);
    }
}
