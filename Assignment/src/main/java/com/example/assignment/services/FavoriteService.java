package com.example.assignment.services;

import com.example.assignment.model.Favorite;
import com.example.assignment.model.Recipes;
import com.example.assignment.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private  FavoriteRepository favoriteRepository;

    public void addToFavorite(Favorite favorite){
            favoriteRepository.save(favorite);
    }

    public List<Favorite> findByEmail(String email) {
        // TODO Auto-generated method stub
        return favoriteRepository.findByUserEmail(email);
    }

}
