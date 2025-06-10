import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.*;

public class ReportGenerator {
    public static void generateAttendanceReport(String filename) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Gym Attendance Report", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.addCell("ID");
            table.addCell("Member Name");
            table.addCell("Date");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymnest", "root", "your_password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM attendance");

            while (rs.next()) {
                table.addCell(String.valueOf(rs.getInt("id")));
                table.addCell(rs.getString("member_name"));
                table.addCell(rs.getDate("date").toString());
            }

            document.add(table);
            document.close();
            conn.close();

            System.out.println("Attendance report generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}