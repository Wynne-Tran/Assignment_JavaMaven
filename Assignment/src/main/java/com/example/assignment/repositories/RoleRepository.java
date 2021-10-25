package com.example.assignment.repositories;

import com.example.assignment.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Roles, String> {

}
