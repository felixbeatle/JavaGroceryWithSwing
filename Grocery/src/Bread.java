public class Bread extends Grocery {

    private String expirationDate;



    public Bread(String articlename, int quantity, double price, String type, String expirationDate) {
        super(articlename, quantity, price,type);
        this.expirationDate = expirationDate;
    }



    public String getExpirationDate() {
        return expirationDate;
    }


    @Override
    public void displayGrocery() {
        System.out.println("Articlename: " + getArticlename() + " Quantity: " + getQuantity() +
                " Price: " + getPrice() + " Type: " + getType() + " Expirationdate: " + expirationDate);
    }
}
