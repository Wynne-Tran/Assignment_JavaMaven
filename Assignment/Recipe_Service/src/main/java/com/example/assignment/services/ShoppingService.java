package com.example.assignment.services;
import com.example.assignment.model.Shopping_Cart;
import com.example.assignment.repositories.RecipeRepository;
import com.example.assignment.repositories.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingService {
    @Autowired
    private ShoppingRepository shoppingRepository;


    public void addToCart(Shopping_Cart cart){
        shoppingRepository.save(cart);
    }
    public void deleteCart(Integer id){
        shoppingRepository.deleteById(id);
    }
    public Shopping_Cart findOne(Integer id) {
        return shoppingRepository.findById(id).orElse(null);
    }

    public Shopping_Cart findCartId(Long id, String email) {
        return shoppingRepository.findShopping_CartByRecipe_idAndUser_email(id, email);
    }

    public List<Shopping_Cart> findByEmail(String email) {
        // TODO Auto-generated method stub
        return shoppingRepository.findByUserEmail(email);
    }

    public  Shopping_Cart findByRecipes_id(Long id){ return  shoppingRepository.findByRecipes_id(id);}
}
