import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerPanel {
    private Connection connection;
    private JTable table;

    public ManagerPanel() {
        // Initialize database connection
        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\database\\Grocery.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayManagerMenu() {
        JFrame frame = new JFrame("Manager Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(100, 50);
        frame.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton payButton = new JButton("Pay Employees");

        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payEmployees();
            }
        });

        buttonPanel.add(payButton);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);

        // Add a window listener to close the connection when the manager panel is closed
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void payEmployees() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(payment_date) FROM payment");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Check if a valid result is returned
            if (resultSet.next() && resultSet.getString(1) != null) {
                LocalDateTime lastPaymentDate = LocalDateTime.parse(resultSet.getString(1), formatter);
                LocalDateTime currentDateTime = LocalDateTime.now();
                Duration duration = Duration.between(lastPaymentDate, currentDateTime);
                long minutesPassed = duration.toMinutes();
                double amountToPay = minutesPassed * 2600;

                statement.execute("INSERT INTO payment (payment_date, amount) VALUES ('" +
                        currentDateTime.format(formatter) + "', " + amountToPay + ")");

                JOptionPane.showMessageDialog(null, "Paid $" + amountToPay + " to employees.");
            } else {
                JOptionPane.showMessageDialog(null, "No previous payment found. Unable to calculate payment amount.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ManagerPanel managerPanel = new ManagerPanel();
        managerPanel.displayManagerMenu();
    }
}
