public class FrozenFood extends Grocery {
    private String expirationDate;



    public FrozenFood(String articlename, int quantity, double price, String type, String expirationDate) {
        super(articlename, quantity, price,type);
        this.expirationDate = expirationDate;
    }



    public String getExpirationdate() {
        return expirationDate;
    }


    @Override
    public void displayGrocery() {
        System.out.println("Articlename: " + getArticlename() + " Quantity: " + getQuantity() +
                " Price: " + getPrice() + " Type: " + getType() + " Expirationdate: " + expirationDate);
    }
}