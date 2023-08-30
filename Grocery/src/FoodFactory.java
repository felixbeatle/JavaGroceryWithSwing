public class FoodFactory {

    public Alcool CreateAlcool(String articlename, int quantity, double price,String type, String expirationDate) {
        alcoolbuilder alcoolbuilder = new alcoolbuilder(articlename, quantity, price,type,expirationDate);
        return alcoolbuilder.build();

    }

    public Dairy CreateDairy(String articlename, int quantity, double price, String type, String expirationDate) {
        DairyBuilder dairybuilder = new DairyBuilder(articlename, quantity, price, type, expirationDate);
        return dairybuilder.build();

    }

    public Bread CreateBread(String articlename, int quantity, double price, String type, String expirationDate) {
        BreadBuilder breadbuilder = new BreadBuilder(articlename, quantity, price, type, expirationDate);
        return breadbuilder.build();

    }



    public FrozenFood CreateFrozenFood(String articlename, int quantity, double price, String type, String expirationDate) {
        FrozenFoodBuilder frozenfoodbuilder = new FrozenFoodBuilder(articlename, quantity, price, type, expirationDate);
        return frozenfoodbuilder.build();

    }

    public Meat CreateMeat(String articlename, int quantity, double price, String type, String expirationDate) {
        MeatBuilder meatbuilder = new MeatBuilder(articlename, quantity, price, type, expirationDate);
        return meatbuilder.build();

    }

    public Snacks CreateSnacks(String articlename, int quantity, double price, String type, String expirationDate) {
        SnackBuilder snackbuilder = new SnackBuilder(articlename, quantity, price, type, expirationDate);
        return snackbuilder.build();

    }




}
