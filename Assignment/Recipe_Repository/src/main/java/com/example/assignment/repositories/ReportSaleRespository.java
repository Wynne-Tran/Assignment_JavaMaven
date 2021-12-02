package com.example.assignment.repositories;


import com.example.assignment.model.ReportSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportSaleRespository extends JpaRepository<ReportSale, Integer> {
    ReportSale findReportByDatePaymentAndUser_email(LocalDate date, String email);

}
