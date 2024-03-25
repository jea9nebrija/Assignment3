/*************************************************
 File: Account.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements the admin's log in account
 *************************************************/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Admin {
    private int id=-1;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private List<Account> accounts;
    ObjectMapper objectMapper =new ObjectMapper();

    public Admin(String userName, String password){
        this.userName = userName;
        this.password=password;
    }

    public static Admin parseJson(String s) {
        ObjectMapper om=new ObjectMapper();
        JsonNode jn = null;
        try {jn = om.readTree(s);}
        catch (JsonProcessingException e) {throw new RuntimeException(e);}
        return new Admin(jn.get("userName").textValue(),jn.get("role").textValue());
    }

    public int getId(){
        return this.id;
    }

    public String getUserName(){
        return this.userName;
    }
    public void setUserName(String userName){this.userName=userName;}
    public void setPassword(String password){
        this.password=password;
        int res= AdminDTO.performUpdate(this);
    }

    public String toJson(){
        String string = "";
        try {string = objectMapper.writeValueAsString(this);}
        catch (JsonProcessingException jpe) {System.out.println(jpe.getMessage());}
        return string;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id=id;
    }
}
