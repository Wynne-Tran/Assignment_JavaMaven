package com.example.assignment.model;

import javax.persistence.*;

@Entity
public class Shopping_Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="USER_EMAIL")
    private String user_email;

    @Column(name="recipe_id")
    private  Long recipe_id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false )
    private Recipes recipes;

    @ManyToOne
    @JoinColumn(name="USER_EMAIL", referencedColumnName = "email", insertable = false, updatable = false)
    private Users user;

    public Shopping_Cart(){}

    public Shopping_Cart(String user_email, Long recipe_id) {
        this.user_email = user_email;
        this.recipe_id = recipe_id;
    }

    public Shopping_Cart(String user_email, Long recipe_id, Recipes recipes) {
        this.user_email = user_email;
        this.recipe_id = recipe_id;
        this.recipes = recipes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipes recipes) {
        this.recipes = recipes;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
