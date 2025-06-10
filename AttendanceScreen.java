import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AttendanceScreen extends JFrame {
    private JComboBox<String> memberDropdown;
    private JButton markAttendanceButton;

    public AttendanceScreen() {
        setTitle("Mark Attendance");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel memberLabel = new JLabel("Select Member:");
        memberDropdown = new JComboBox<>();

        loadMembers();

        markAttendanceButton = new JButton("Mark Present");

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(memberLabel);
        panel.add(memberDropdown);
        panel.add(new JLabel());
        panel.add(markAttendanceButton);

        add(panel, BorderLayout.CENTER);

        markAttendanceButton.addActionListener(e -> {
            String selectedMember = (String) memberDropdown.getSelectedItem();
            markAttendance(selectedMember);
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

    private void markAttendance(String memberName) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymnest", "root", "your_password");
             PreparedStatement pst = conn.prepareStatement("INSERT INTO attendance (member_name, date) VALUES (?, CURDATE())")) {
            pst.setString(1, memberName);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendance marked for " + memberName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to mark attendance.");
        }
    }
}