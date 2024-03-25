/*************************************************
 File: AccountDTO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing admin's data transfer object (DTO) for log in
 *************************************************/



import java.sql.SQLException;

public class AdminDTO {

    private int id;
    private String username;
    private String password;
    private String role;

    static AdminDAOConcrete udc = new AdminDAOConcrete();

    public AdminDTO() {
        // Default constructor
    }

    public AdminDTO(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter and Setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getID() {
        return id;
    }

    public void setID(int anID) {
        this.id = anID;
    }

    public static int performInsert(Admin admin) {
        int userId = -1;

        try {userId = udc.insertUser(admin);}
        catch(SQLException se) {System.out.println(se.getMessage());}

        if(userId != -1) System.out.println("Inserted row to user database.");
        return userId;
    }
    public static int performUpdate(Admin admin){
        int updateResult = -1;
        try {
            updateResult = udc.updateUser(admin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(updateResult != -1) System.out.println("User database updated.");
        return updateResult;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
