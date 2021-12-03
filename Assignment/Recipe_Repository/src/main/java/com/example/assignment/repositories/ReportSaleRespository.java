
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a repository database of Report sale
 ******************************************************************************** */

package com.example.assignment.repositories;
import com.example.assignment.model.ReportSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportSaleRespository extends JpaRepository<ReportSale, Integer> {
    ReportSale findReportByDatePaymentAndUser_email(LocalDate date, String email);

}
