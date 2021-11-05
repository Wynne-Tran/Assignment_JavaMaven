
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  the repository with all logical read and write operations for Recipes entity
 ******************************************************************************** */

package com.example.assignment.repositories;

import com.example.assignment.model.Recipes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Recipes, Long> {

}
