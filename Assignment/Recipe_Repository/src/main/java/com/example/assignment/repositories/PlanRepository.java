
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  the repository with all logical read and write operations for Plan_Recipes entity
 ******************************************************************************** */

package com.example.assignment.repositories;
import com.example.assignment.model.Favorite;
import com.example.assignment.model.Plan_Recipe;
import com.example.assignment.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends CrudRepository<Plan_Recipe, Long> {

    @Query("from Plan_Recipe e where e.user.email = :email and not(e.end < :from or e.start > :to)")
     List<Plan_Recipe> findBetween(
            @Param("email") String email,
            @Param("from") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Transactional
    void deleteByText(String title);

    List<Plan_Recipe> findByUserEmail(String email);



}
