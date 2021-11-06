
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a database of Recipes
 ******************************************************************************** */

package com.example.assignment.model;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Recipes {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Title must not be empty")
    private String date;
    @NotEmpty(message = "Date must not be empty")
    private String title;
    @NotEmpty(message = "Description must not be empty")
    @Column(length=10000)
    private String description;

    @ManyToOne
    @JoinColumn(name="USER_EMAIL")
    private Users user;

    private String image;
    @Transient
    @Nullable
    private MultipartFile multipartFile;

    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL)
    private List<Favorite> favorite;

    private String creater;
    private String favorite_like;

    public Recipes(){};

    public Recipes(String date, String title, String description, String image,String creater, String favorite_like,  Users user) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.image = image;
        this.user = user;
        this.creater = creater;
        this.favorite_like= favorite_like;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Favorite> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Favorite> favorite) {
        this.favorite = favorite;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getFavorite_like() {
        return favorite_like;
    }

    public void setFavorite_like(String favorite_like) {
        this.favorite_like = favorite_like;
    }
}
