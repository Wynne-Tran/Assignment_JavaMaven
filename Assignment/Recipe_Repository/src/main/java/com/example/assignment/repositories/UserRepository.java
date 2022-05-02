
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is the standard way of persisting Java objects into Users databases
 ******************************************************************************** */

package com.example.assignment.repositories;

import com.example.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository <Users, String> {
    List<Users> findByNameLike(String s);
    Users findByEmail(String email);
}
