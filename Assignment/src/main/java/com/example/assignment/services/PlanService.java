
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is PlanService, connecting PlanRepository to PlanRecipeController
 ******************************************************************************** */
package com.example.assignment.services;

import com.example.assignment.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlanService {
    @Autowired
    PlanRepository planRepository;
    public void deletePlan(String title) {
        planRepository.deleteByText(title);
    }
}
