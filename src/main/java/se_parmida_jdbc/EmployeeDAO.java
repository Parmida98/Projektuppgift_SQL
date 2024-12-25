package se_parmida_jdbc;

import java.sql.*;

public class EmployeeDAO {
    private final String url = "jdbc:hsqldb:hsql://localhost/jdbclab";;
    private final String user = "sa";
    private final String password = "";

    // SQL satsen infogar en ny rad i tabellen. Vi skapar och hanterar Connection och Preparedstatement+
    // som stängs automatiskt. Utför sql satsen som infogar data.
    public void insertEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, email, password, role_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getPassword());
            pstmt.setInt(4, employee.getRoleId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hämta anställds och arbetsroll informationen genom mail och lösenord.
    public Employee login(String email, String password) {
        String sql = """
            SELECT employee.*, work_role.title, work_role.description, work_role.salary, work_role.creation_date
            FROM employee employee
            JOIN work_role work_role ON work_role.role_id = work_role.role_id
            WHERE employee.email = ? AND employee.password = ?
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Kontrollerar om resultatet finnns, skapar ett nytt objekt.
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setRoleId(rs.getInt("role_id"));

                System.out.println("Inloggad som: " + employee.getName());
                System.out.println("Arbetsroll: " + rs.getString("title"));
                System.out.println("Beskrivning: " + rs.getString("description"));
                System.out.println("Lön: " + rs.getDouble("salary"));
                System.out.println("Skapad: " + rs.getDate("creation_date"));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
