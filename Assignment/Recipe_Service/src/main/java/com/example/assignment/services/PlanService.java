
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is PlanService, connecting PlanRepository to PlanRecipeController
 ******************************************************************************** */
package com.example.assignment.services;

import com.example.assignment.model.Plan_Recipe;
import com.example.assignment.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlanService {
    @Autowired
    PlanRepository planRepository;


    public void deletePlan(String title) {
        planRepository.deleteByText(title);
    }
    public List<Plan_Recipe> findByEmail(String email) {
        // TODO Auto-generated method stub
        return planRepository.findByUserEmail(email);
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

}
