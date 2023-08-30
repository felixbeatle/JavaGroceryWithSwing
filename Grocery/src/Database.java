import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db");

            Statement statement = conn.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS alcool (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS bread (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS dairy (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS frozenfood (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS meat (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS snacks (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename STRING, quantity INTEGER, price DOUBLE, type STRING, expirationDate STRING)");

            statement.execute("CREATE TABLE IF NOT EXISTS sale (idsale INTEGER PRIMARY KEY AUTOINCREMENT, alcool_iditem INTEGER, bread_iditem INTEGER, dairy_iditem INTEGER, frozenfood_iditem INTEGER, meat_iditem INTEGER, snacks_iditem INTEGER, totalprice DOUBLE, FOREIGN KEY (alcool_iditem) REFERENCES alcool(iditem) ON DELETE CASCADE, FOREIGN KEY (bread_iditem) REFERENCES bread(iditem) ON DELETE CASCADE, FOREIGN KEY (dairy_iditem) REFERENCES dairy(iditem) ON DELETE CASCADE, FOREIGN KEY (frozenfood_iditem) REFERENCES frozenfood(iditem) ON DELETE CASCADE, FOREIGN KEY (meat_iditem) REFERENCES meat(iditem) ON DELETE CASCADE, FOREIGN KEY (snacks_iditem) REFERENCES snacks(iditem) ON DELETE CASCADE)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS profit (profit DOUBLE)");
            statement.execute("CREATE TABLE IF NOT EXISTS Cart (iditem INTEGER PRIMARY KEY AUTOINCREMENT, articlename TEXT, Price DOUBLE, type TEXT)");

            statement.execute(" CREATE TABLE IF NOT EXISTS payment (payment_id INTEGER PRIMARY KEY AUTOINCREMENT,payment_date DATE,amount DOUBLE)");


            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
