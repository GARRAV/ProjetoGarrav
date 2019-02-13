package br.com.garrav.projetogarrav.model;

import java.util.Date;

public class User {

    private long id;
    private String name;
    private String fic_name;
    private String email;
    private Date date_account;
    private String password;
    private String type_user;

    //Actual User
    private static User uniqueUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFic_name() {
        return fic_name;
    }

    public void setFic_name(String fic_name) {
        this.fic_name = fic_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_account() {
        return date_account;
    }

    public void setDate_account(Date date_account) {
        this.date_account = date_account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType_user() {
        return type_user;
    }

    public void setType_user(String type_user) {
        this.type_user = type_user;
    }

    //Actual User
    public static User getUniqueUser() {
        return uniqueUser;
    }

    public static void setUniqueUser(User uniqueUser) {
        User.uniqueUser = uniqueUser;
    }
}
