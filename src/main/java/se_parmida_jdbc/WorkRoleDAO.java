package se_parmida_jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRoleDAO {
    private static final String INSERT_WORK_ROLE = "INSERT INTO work_roles (title, description, salary, creation_date) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_WORK_ROLES = "SELECT * FROM work_roles";
    private JDBCUtil DatabaseConnection;

    // hämtar anslutning. förbereder sql frågan men insert_work_role. Lägger till arbetsrollen i databasen.
    public void insertWorkRole(WorkRole role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(INSERT_WORK_ROLE);
            pstmt.setString(1, role.getTitle());
            pstmt.setString(2, role.getDescription());
            pstmt.setDouble(3, role.getSalary());
            pstmt.setDate(4, new java.sql.Date(role.getCreationDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    // hämtar alla roller och gör en lista. Går igenom resultatet med en while-loop.
    //lägger alla uppgifter i sin egna kolumn. Lägger objekt i listan roles.
    public List<WorkRole> getAllWorkRoles() {
        List<WorkRole> roles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(SELECT_ALL_WORK_ROLES);
            rs = stmt.executeQuery();
            while (rs.next()) {
                WorkRole role = new WorkRole();
                role.setRoleId(rs.getInt("id"));
                role.setTitle(rs.getString("title"));
                role.setDescription(rs.getString("description"));
                role.setSalary(rs.getDouble("salary"));
                role.setCreationDate(rs.getDate("creation_date"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closeConnection(conn);
        }
        return roles;
    }
}
