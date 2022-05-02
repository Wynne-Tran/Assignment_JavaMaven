
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  This page is service to save total payment and display in PDF file
 ******************************************************************************** */


package com.example.assignment.services;

import com.example.assignment.model.ReportSale;
import com.example.assignment.repositories.ReportSaleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportSaleService {
    @Autowired
    ReportSaleRespository reportSaleRespository;
    public void  addReportSale(ReportSale reportSale) { reportSaleRespository.save(reportSale);}
    public ReportSale findOne( LocalDate date, String email){
        return reportSaleRespository.findReportByDatePaymentAndUser_email(date, email);
    }

    public void deleteReport(LocalDate date, String email){
        reportSaleRespository.delete(reportSaleRespository.findReportByDatePaymentAndUser_email(date, email));
    }
}
