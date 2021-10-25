package com.example.assignment.repositories;

import com.example.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository <Users, String> {
    List<Users> findByNameLike(String s);
}
