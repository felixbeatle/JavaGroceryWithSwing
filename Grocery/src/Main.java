import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));

        JLabel welcomeLabel = new JLabel("Welcome to FelixNeilZach Grocery");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton manageButton = new JButton("Manage");

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BuyerInterface buyerInterface = new BuyerInterface();
                buyerInterface.cart = new ArrayList<>();
                buyerInterface.showBuyerMenu();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SellerInterface sellerInterface = new SellerInterface();
                sellerInterface.showSellerMenu();
            }
        });

        manageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerPanel managerPanel = new ManagerPanel();
                managerPanel.displayManagerMenu();
            }
        });


        panel.add(welcomeLabel);
        panel.add(buyButton);
        panel.add(sellButton);
        panel.add(manageButton);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
