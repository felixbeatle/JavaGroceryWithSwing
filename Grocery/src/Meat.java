public class Meat extends Grocery {
    private String expirationDate;



    public Meat(String articlename, int quantity, double price, String type, String expirationDate) {
        super(articlename, quantity, price,type);
        this.expirationDate = expirationDate;
    }


    public String getExpirationdate() {
        return expirationDate;
    }


    @Override
    public void displayGrocery() {
        System.out.println("Articlename: " + getArticlename() + " Quantity: " + getQuantity() +
                " Price: " + getPrice() + " Type: " + getType() + " Expiration date: " + expirationDate);
    }
}