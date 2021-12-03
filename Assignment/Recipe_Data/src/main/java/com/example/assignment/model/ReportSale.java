
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a database of Report Sale
 * Database to sale total payment when user checkout, and export to PDF file
 ******************************************************************************** */


package com.example.assignment.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
public class ReportSale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="USER_EMAIL")
    private Users user;
    @NotNull
    LocalDate datePayment = LocalDate.now();
    double payment;

    public ReportSale(){}

    public ReportSale(Integer id, Users user, LocalDate datePayment, double payment) {
        this.id = id;
        this.user = user;
        this.datePayment = datePayment;
        this.payment = payment;
    }

    public ReportSale(Users user, LocalDate datePayment, double payment) {
        this.user = user;
        this.datePayment = datePayment;
        this.payment = payment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
