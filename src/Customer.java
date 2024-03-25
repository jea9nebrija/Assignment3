/*************************************************
 File: Customer.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing customer's application
 *************************************************/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

public class Customer {
    private int id=-1;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String sex;
    private LocalDate birthday;
    ObjectMapper objectMapper =new ObjectMapper();

    public Customer(int id){
        this.id = id;
        objectMapper.registerModule(new JavaTimeModule());
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

    public String toJson(){
        String string = "";
        try {string = objectMapper.writeValueAsString(this);}
        catch (JsonProcessingException jpe) {System.out.println(jpe.getMessage());}
        return string;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

}
