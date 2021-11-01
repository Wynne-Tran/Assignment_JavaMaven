package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Users implements Serializable {

    @Id
    @Email
    @NotEmpty(message = "email can't be empty !")
    @Column(unique = true)
    private String email;
    @NotEmpty (message = "Username can't be empty !")
    private String name;
    @NotEmpty(message = "password can't be empty !")
    @Size(min=4)
    private  String password;
    @Transient
    @Nullable
    private  String repeatPassword;
    @Lob
    private String image;
    @Transient
    @Nullable
    private MultipartFile multipartFile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipes> recipes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_NAME", referencedColumnName = "name")
    })
    private  List<Roles> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Plan_Recipe> plan_recipes;

    public Users(){};

    public Users(String email, String name, String password, String repeatPassword, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.image = image;
    }

    public Users(String email, String name, String password, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public List<Recipes> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipes> recipes) {
        this.recipes = recipes;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Plan_Recipe> getPlan_recipes() {
        return plan_recipes;
    }

    public void setPlan_recipes(List<Plan_Recipe> plan_recipes) {
        this.plan_recipes = plan_recipes;
    }
}
