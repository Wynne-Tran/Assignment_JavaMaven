
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a database of Shopping cart
 * Database when user click add to cart
 ******************************************************************************** */


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

    @Column(name="Quantity")
    private  Integer quantity = 1;

    @Column(name="Total")
    private  double totalPrice ;

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

    public Shopping_Cart(String user_email, Long recipe_id, Integer  quantity, double totalPrice, Recipes recipes) {
        this.user_email = user_email;
        this.recipe_id = recipe_id;
        this.quantity = quantity;
        this.recipes = recipes;
        this.totalPrice =totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice ;
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
