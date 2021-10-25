package com.example.assignment.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Roles {

    @Id
    private  String name;
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<Users> users;

    public  Roles(){}
    public Roles(String name, List<Users> users) {
        this.name = name;
        this.users = users;
    }

    public Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
