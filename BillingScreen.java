import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BillingScreen extends JFrame {
    private JComboBox<String> memberDropdown;
    private JTextField amountField;
    private JButton generateButton;

    public BillingScreen() {
        setTitle("Generate Bill");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel memberLabel = new JLabel("Member:");
        JLabel amountLabel = new JLabel("Amount:");

        memberDropdown = new JComboBox<>();
        amountField = new JTextField();
        generateButton = new JButton("Generate");

        loadMembers();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(memberLabel);
        panel.add(memberDropdown);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(new JLabel());
        panel.add(generateButton);

        add(panel, BorderLayout.CENTER);

        generateButton.addActionListener(e -> {
            String member = (String) memberDropdown.getSelectedItem();
            String amount = amountField.getText();
            JOptionPane.showMessageDialog(this, "Bill generated for " + member + ": ₹" + amount);
        });
    }

    private void loadMembers() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymnest", "root", "your_password");
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM members");
            while (rs.next()) {
                memberDropdown.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load members.");
        }
    }
}