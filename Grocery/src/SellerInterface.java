import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SellerInterface {
    private FoodFactory foodFactory;
    private Cart cart;

    public SellerInterface() {
        // Create an instance of the FoodFactory class
        foodFactory = new FoodFactory();
        cart = new Cart();
    }

    public void showSellerMenu() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            JFrame frame = new JFrame("Seller Interface");
            JPanel panel = new JPanel();

            panel.setLayout(new GridLayout(0, 1));
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);


            String[] categories = {"Alcool", "Bread", "Dairy", "FrozenFood", "Meat", "Snacks"};
            JComboBox<String> categoryMenu = new JComboBox<>(categories);

            JTextField nameField = new JTextField();
            JTextField quantityField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField expirationDateField = new JTextField();
            JButton addButton = new JButton("Add Element");

            setFieldsVisibility(nameField, quantityField, priceField, typeField, expirationDateField, categories[0]);

            categoryMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedCategory = (String) categoryMenu.getSelectedItem();
                    setFieldsVisibility(nameField, quantityField, priceField, typeField, expirationDateField, selectedCategory);
                }
            });

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String articlename = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    String type = typeField.getText();
                    String expirationDate = expirationDateField.getText();
                    String selectedCategory = (String) categoryMenu.getSelectedItem();

                    if (selectedCategory.equals("Alcool")) {
                        Alcool alcool = foodFactory.CreateAlcool(articlename, quantity, price, type, expirationDate);
                        InsertAlcoolInTheDatabase(alcool);
                    } else if (selectedCategory.equals("Bread")) {
                        Bread bread = foodFactory.CreateBread(articlename, quantity, price, type, expirationDate);
                        InsertBreadInTheDatabase(bread);
                    } else if (selectedCategory.equals("Dairy")) {
                        Dairy dairy = foodFactory.CreateDairy(articlename, quantity, price, type, expirationDate);
                        InsertDairyInTheDatabase(dairy);
                    } else if (selectedCategory.equals("FrozenFood")) {
                        FrozenFood frozenFood = foodFactory.CreateFrozenFood(articlename, quantity, price, type, expirationDate);
                        InsertFrozenfoodInTheDatabase(frozenFood);
                    } else if (selectedCategory.equals("Meat")) {
                        Meat meat = foodFactory.CreateMeat(articlename, quantity, price, type, expirationDate);
                        InsertMeatInTheDatabase(meat);
                    } else if (selectedCategory.equals("Snacks")) {
                        Snacks snacks = foodFactory.CreateSnacks(articlename, quantity, price, type, expirationDate);
                        InsertsnacksInTheDatabase(snacks);
                    }

                    CartItem cartItem = new CartItem(articlename, price);
                    cart.addItem(cartItem);


                }
            });

            panel.add(new JLabel("Select Category:"));
            panel.add(categoryMenu);
            panel.add(new JLabel("Article Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);
            panel.add(new JLabel("Expiration Date:"));
            panel.add(expirationDateField);
            panel.add(addButton);

            frame.getContentPane().add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private void setFieldsVisibility(JTextField nameField, JTextField quantityField, JTextField priceField, JTextField typeField, JTextField expirationDateField, String selectedCategory) {
            nameField.setVisible(true);
            quantityField.setVisible(true);
            priceField.setVisible(true);
            typeField.setVisible(true);
            expirationDateField.setVisible(true);

    }


    private void InsertAlcoolInTheDatabase(Alcool alcool) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO alcool (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, alcool.getArticlename());
            statement.setInt(2, alcool.getQuantity());
            statement.setDouble(3, alcool.getPrice());
            statement.setString(4, alcool.getType());
            statement.setString(5, alcool.getExpirationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting alcool into the database: " + e.getMessage());
        }
    }

    private void InsertBreadInTheDatabase(Bread bread) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bread (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, bread.getArticlename());
            statement.setInt(2, bread.getQuantity());
            statement.setDouble(3, bread.getPrice());
            statement.setString(4, bread.getType());
            statement.setString(5, bread.getExpirationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting bread into the database: " + e.getMessage());
        }
    }

    private void InsertDairyInTheDatabase(Dairy dairy) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO dairy (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, dairy.getArticlename());
            statement.setInt(2, dairy.getQuantity());
            statement.setDouble(3, dairy.getPrice());
            statement.setString(4, dairy.getType());
            statement.setString(5, dairy.getExpirationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting dairy into the database: " + e.getMessage());
        }
    }

    private void InsertFrozenfoodInTheDatabase(FrozenFood frozenFood) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO frozenfood (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, frozenFood.getArticlename());
            statement.setInt(2, frozenFood.getQuantity());
            statement.setDouble(3, frozenFood.getPrice());
            statement.setString(4, frozenFood.getType());
            statement.setString(5, frozenFood.getExpirationdate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting frozen food into the database: " + e.getMessage());
        }
    }

    private void InsertMeatInTheDatabase(Meat meat) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO meat (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, meat.getArticlename());
            statement.setInt(2, meat.getQuantity());
            statement.setDouble(3, meat.getPrice());
            statement.setString(4, meat.getType());
            statement.setString(5, meat.getExpirationdate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting meat into the database: " + e.getMessage());
        }
    }

    private void InsertsnacksInTheDatabase(Snacks snacks) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db")) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO snacks (articlename, quantity, price, type, expirationDate) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, snacks.getArticlename());
            statement.setInt(2, snacks.getQuantity());
            statement.setDouble(3, snacks.getPrice());
            statement.setString(4, snacks.getType());
            statement.setString(5, snacks.getExpirationdate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting snacks into the database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SellerInterface sellerInterface = new SellerInterface();
        sellerInterface.showSellerMenu();
    }
}
