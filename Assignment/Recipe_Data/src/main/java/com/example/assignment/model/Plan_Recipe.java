
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a database of Plan_recipe
 ******************************************************************************** */

package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Plan_Recipe {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String text;
    @ManyToOne
    @JoinColumn(name="USER_EMAIL")
    @JsonBackReference
    private Users user;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime end;
    String color;


    public Plan_Recipe(Long id, String text, Users user, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.start = start;
        this.end = end;
    }

    public Plan_Recipe() {
    }

    public Plan_Recipe(String text, Users user, LocalDateTime start, LocalDateTime end) {
        this.text = text;
        this.user = user;
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(String datestart) {
        this.start = LocalDateTime.parse(datestart);
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(String  dateend) {
        this.end = LocalDateTime.parse(dateend);
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }

}
