
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a database of Users
 ******************************************************************************** */

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

  //Profile
  private String jobTitle = "Student";
  private String about = "I'm a UI/UX Designer from Paris, in France. I really enjoy photography and mountains.";
  private  Integer recipeCount = 0;
  private  Integer likeCount = 0;
  private  Integer shoppingCount = 0;

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
    private List<Shopping_Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Plan_Recipe> plan_recipes;



    public Users(){};

    public Users(String email, String password, String repeatPassword) {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

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

    public Users(String email, String name, String password, @Nullable String repeatPassword, String image, @Nullable MultipartFile multipartFile, String jobTitle, String about, Integer recipeCount, Integer likeCount, Integer shoppingCount) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.image = image;
        this.multipartFile = multipartFile;
        this.jobTitle = jobTitle;
        this.about = about;
        this.recipeCount = recipeCount;
        this.likeCount = likeCount;
        this.shoppingCount = shoppingCount;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getRecipeCount() {
        return recipeCount;
    }

    public void setRecipeCount(Integer recipeCount) {
        this.recipeCount = recipeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getShoppingCount() {
        return shoppingCount;
    }

    public void setShoppingCount(Integer shoppingCount) {
        this.shoppingCount = shoppingCount;
    }
}
