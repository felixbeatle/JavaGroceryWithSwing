import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerInterface {
    protected List<String> cart;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JTextArea itemDetailsTextArea;
    private Connection connection;

    public void showBuyerMenu() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db");

            JFrame frame = new JFrame("Buyer Interface");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 800);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.LIGHT_GRAY);
            JLabel titleLabel = new JLabel("Welcome to FelixNeilZach Grocery");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titlePanel.add(titleLabel);

            JPanel searchPanel = new JPanel();
            searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            categoryComboBox = new JComboBox<>(new String[]{"alcool", "bread", "dairy", "frozenfood", "meat", "snacks"});
            searchPanel.add(new JLabel("Search by Name:"));
            searchPanel.add(searchField);
            searchPanel.add(new JLabel("Category:"));
            searchPanel.add(categoryComboBox);
            searchPanel.add(searchButton);

            JPanel cartPanel = new JPanel();
            cartPanel.setLayout(new BorderLayout());
            cartListModel = new DefaultListModel<>();
            cartList = new JList<>(cartListModel);
            JScrollPane cartScrollPane = new JScrollPane(cartList);
            cartScrollPane.setPreferredSize(new Dimension(600, 200));
            cartPanel.add(cartScrollPane, BorderLayout.CENTER);

            JPanel itemDetailsPanel = new JPanel();
            itemDetailsPanel.setLayout(new BorderLayout());
            itemDetailsTextArea = new JTextArea(200, 150);
            itemDetailsTextArea.setEditable(false);
            JScrollPane itemDetailsScrollPane = new JScrollPane(itemDetailsTextArea);
            itemDetailsPanel.add(itemDetailsScrollPane, BorderLayout.CENTER);



            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 1));
            JButton addToCartButton = new JButton("Add to Cart");
            JButton viewCartButton = new JButton("View Cart");
            JButton removeButton = new JButton("Remove from Cart");
            JButton payButton = new JButton("Pay");
            buttonPanel.add(addToCartButton);
            buttonPanel.add(viewCartButton);
            buttonPanel.add(removeButton);
            buttonPanel.add(payButton);

            mainPanel.add(titlePanel, BorderLayout.NORTH);
            mainPanel.add(searchPanel, BorderLayout.PAGE_START);
            mainPanel.add(itemDetailsPanel, BorderLayout.CENTER);
            mainPanel.add(cartPanel, BorderLayout.EAST);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            frame.getContentPane().add(mainPanel);

            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchKeyword = searchField.getText();
                    String category = (String) categoryComboBox.getSelectedItem();
                    List<String> searchResults = performSearch(searchKeyword, category);
                    cartListModel.clear();
                    for (String result : searchResults) {
                        cartListModel.addElement(result);
                    }
                }
            });

            viewCartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    itemDetailsTextArea.setText(getCartItemsDetails());
                }
            });

            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedElement = cartList.getSelectedValue();
                    if (selectedElement != null) {
                        cartListModel.removeElement(selectedElement);
                        try {
                            PreparedStatement statement = connection.prepareStatement("DELETE FROM Cart WHERE articlename = ?");
                            statement.setString(1, selectedElement.split(", Price: ")[0].substring(12));
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("Something went wrong: " + ex.getMessage());
                        }
                    }
                }
            });

            payButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double totalPrice = calculateTotalPrice();
                    double totalPriceWithTax = calculateTotalPriceWithTax();
                    double taxAmount = totalPriceWithTax - totalPrice;
                    double profit = calculateProfit(totalPrice);

                    String message = "Total Price (Without Tax): $" + totalPrice + "\n"
                            + "Total Price (With Tax): $" + totalPriceWithTax + "\n"
                            + "Tax Amount: $" + taxAmount;
                    JOptionPane.showMessageDialog(frame, message);

                    try {
                        PreparedStatement insertProfitStatement = connection.prepareStatement("INSERT INTO profit (profit) VALUES (?)");
                        insertProfitStatement.setDouble(1, profit);
                        insertProfitStatement.executeUpdate();
                        insertProfitStatement.close();
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong: " + ex.getMessage());
                    }


                    try {
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM Cart");
                        PreparedStatement insertSaleStatement = connection.prepareStatement("INSERT INTO sale (alcool_iditem, bread_iditem, dairy_iditem, frozenfood_iditem, meat_iditem, snacks_iditem, totalprice) VALUES (?, ?, ?, ?, ?, ?, ?)");

                        while (resultSet.next()) {
                            int iditem = resultSet.getInt("iditem");
                            double price = resultSet.getDouble("Price");
                            String itemType = resultSet.getString("type");

                            if (itemType != null) {
                                if (itemType.equalsIgnoreCase("alcool")) {
                                    insertSaleStatement.setInt(1, iditem);
                                    insertSaleStatement.setNull(2, Types.INTEGER);
                                    insertSaleStatement.setNull(3, Types.INTEGER);
                                    insertSaleStatement.setNull(4, Types.INTEGER);
                                    insertSaleStatement.setNull(5, Types.INTEGER);
                                    insertSaleStatement.setNull(6, Types.INTEGER);
                                } else if (itemType.equalsIgnoreCase("bread")) {
                                    insertSaleStatement.setNull(1, Types.INTEGER);
                                    insertSaleStatement.setInt(2, iditem);
                                    insertSaleStatement.setNull(3, Types.INTEGER);
                                    insertSaleStatement.setNull(4, Types.INTEGER);
                                    insertSaleStatement.setNull(5, Types.INTEGER);
                                    insertSaleStatement.setNull(6, Types.INTEGER);
                                } else if (itemType.equalsIgnoreCase("dairy")) {
                                    insertSaleStatement.setNull(1, Types.INTEGER);
                                    insertSaleStatement.setNull(2, Types.INTEGER);
                                    insertSaleStatement.setInt(3, iditem);
                                    insertSaleStatement.setNull(4, Types.INTEGER);
                                    insertSaleStatement.setNull(5, Types.INTEGER);
                                    insertSaleStatement.setNull(6, Types.INTEGER);
                                }
                                else if (itemType.equalsIgnoreCase("frozenfood")) {
                                    insertSaleStatement.setNull(1, Types.INTEGER);
                                    insertSaleStatement.setNull(2, Types.INTEGER);
                                    insertSaleStatement.setNull(3, Types.INTEGER);
                                    insertSaleStatement.setInt(4, iditem);
                                    insertSaleStatement.setNull(5, Types.INTEGER);
                                    insertSaleStatement.setNull(6, Types.INTEGER);
                                }
                                else if (itemType.equalsIgnoreCase("meat")) {
                                    insertSaleStatement.setNull(1, Types.INTEGER);
                                    insertSaleStatement.setNull(2, Types.INTEGER);
                                    insertSaleStatement.setNull(3, Types.INTEGER);
                                    insertSaleStatement.setNull(4, Types.INTEGER);
                                    insertSaleStatement.setInt(5, iditem);
                                    insertSaleStatement.setNull(6, Types.INTEGER);
                                }
                                else if (itemType.equalsIgnoreCase("snacks")) {
                                    insertSaleStatement.setNull(1, Types.INTEGER);
                                    insertSaleStatement.setNull(2, Types.INTEGER);
                                    insertSaleStatement.setNull(3, Types.INTEGER);
                                    insertSaleStatement.setNull(4, Types.INTEGER);
                                    insertSaleStatement.setNull(5, Types.INTEGER);
                                    insertSaleStatement.setInt(6, iditem);
                                }
                            } else {



                                insertSaleStatement.setNull(1, Types.INTEGER);
                                insertSaleStatement.setNull(2, Types.INTEGER);
                                insertSaleStatement.setNull(3, Types.INTEGER);
                                insertSaleStatement.setNull(4, Types.INTEGER);
                                insertSaleStatement.setNull(5, Types.INTEGER);
                                insertSaleStatement.setNull(6, Types.INTEGER);
                            }

                            insertSaleStatement.setDouble(7, price);
                            insertSaleStatement.addBatch();
                        }

                        insertSaleStatement.executeBatch();
                        resultSet.close();
                        statement.executeUpdate("DELETE FROM Cart");
                        statement.close();
                        insertSaleStatement.close();
                    } catch (SQLException ex) {
                        System.out.println("Something went wrong: " + ex.getMessage());
                    }




                    cart.clear();
                    cartListModel.clear();
                }
            });


            addToCartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedElement = cartList.getSelectedValue();
                    if (selectedElement != null) {
                        cart.add(selectedElement);
                        try {
                            PreparedStatement statement = connection.prepareStatement("INSERT INTO Cart (Articlename, Price) VALUES (?, ?)");
                            statement.setString(1, selectedElement.split(", Price: ")[0].substring(12));
                            statement.setDouble(2, Double.parseDouble(selectedElement.split(", Price: ")[1]));
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("Something went wrong: " + ex.getMessage());
                        }
                    }
                }
            });

            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private List<String> performSearch(String searchKeyword, String category) {
        List<String> results = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + category + " WHERE articlename LIKE ?");
            statement.setString(1, "%" + searchKeyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String articlename = resultSet.getString("articlename");
                double price = resultSet.getDouble("Price");

                String elementString = "Articlename: " + articlename + ", Price: " + (price+(price*0.1));

                results.add(elementString);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return results;
    }

    private String getCartItemsDetails() {
        StringBuilder details = new StringBuilder();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Cart");

            while (resultSet.next()) {
                String articlename = resultSet.getString("articlename");
                double price = resultSet.getDouble("Price");

                details.append("Articlename: ").append(articlename).append(", Price: ").append(price).append("\n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return details.toString();
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Cart");

            while (resultSet.next()) {
                double price = resultSet.getDouble("Price");
                totalPrice += price;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return totalPrice;
    }
    private double calculateProfit(double totalPrice) {
        double profitRate = 0.10; // 10% profit rate
        double profit = totalPrice * profitRate;
        return profit;
    }

    private double calculateTotalPriceWithTax() {
        double totalPrice = calculateTotalPrice();
        double taxRate = 0.10; // Assuming 10% tax rate
        double totalPriceWithTax = totalPrice + (totalPrice * taxRate);

        return totalPriceWithTax;
    }

    public static void main(String[] args) {
        BuyerInterface buyerInterface = new BuyerInterface();
        buyerInterface.cart = new ArrayList<>();
        buyerInterface.showBuyerMenu();
    }
}