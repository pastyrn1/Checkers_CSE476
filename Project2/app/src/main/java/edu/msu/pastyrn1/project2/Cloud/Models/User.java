package edu.msu.pastyrn1.project2.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

//TODO:Delete this class if it remains unused

@Root(name = "checkers")
public class User {
    @Attribute
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Attribute
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    @Attribute
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {}

    public User(Integer id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
