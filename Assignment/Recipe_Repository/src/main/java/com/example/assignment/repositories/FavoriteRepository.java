
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is the standard way of persisting Java objects into Favorite databases
 ******************************************************************************** */
package com.example.assignment.repositories;

import com.example.assignment.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository <Favorite, Integer> {
    List<Favorite> findByUserEmail(String email);
}
