/*************************************************
 File: AccountDAOConcrete.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing admin's data access object (DAO) for log in
 *************************************************/



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class AdminDAOConcrete implements AdminDAO {
    List<Admin> admins;
    static Connection connection = null;
    PreparedStatement pStatement;

    public AdminDAOConcrete(){
        try {
            connection=MySQLConnection.getDBConnection();

            pStatement = connection.prepareStatement(MySQLConnection.getAdminSelect());
            ResultSet rs = pStatement.executeQuery();

            admins = new ArrayList<Admin>();
            while(rs.next()){
                Admin u= new Admin(rs.getString("userid"),rs.getString("pwd"));
                admins.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(Admin admin) {}

    @Override
    public List<Admin> getAllUsers() {
        return admins;
    }

    @Override
    public Admin getUser(int id) {
        return admins.get(id);
    }

    public boolean authenticate(String username, String password) {
        for (Admin u : admins) {
            if(u.getUserName().equals(username) && u.getPassword().equals(password)) return true;
        }
        return false;
    }

    public int resetTable() throws SQLException {
        System.out.println("(Re-)creating User table");
        int res = -1;
        pStatement = connection.prepareStatement(MySQLConnection.getAdminDrop());
        res = pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getUseDatabase());
        res = pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getAdminCreate());
        res = pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getAdminLoad());
        res = pStatement.executeUpdate();
        return res;
    }

    public int insertUser(Admin admin) throws SQLException {
        pStatement = connection.prepareStatement(MySQLConnection.getAdminInsert(), Statement.RETURN_GENERATED_KEYS);
        pStatement.setString(1, admin.getUserName());
        pStatement.setString(2, admin.getPassword());
        pStatement.executeUpdate();

        int userId=-1;
        ResultSet rs = pStatement.getGeneratedKeys();
        if (rs.next()) {userId = rs.getInt(1);}
        return userId;
    }

    public void addUser(Admin u) {
        int userId= AdminDTO.performInsert(u);
        u.setId(userId);
        admins.add(u);
    }

    public int updateUser(Admin u) throws SQLException {
        int res = -1;
//        pStatement = connection.prepareStatement(MySQLConnection.getAdminUpdate());
//        pStatement.setString(1, u.getUserName());
//        pStatement.setString(2, u.getPassword());
//        pStatement.setString(3, Integer.toString(u.getId()));
//        res = pStatement.executeUpdate();
        return res;
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public int update(Admin admin) {
        return 0;
    }

}