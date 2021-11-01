package com.example.assignment.repositories;

import com.example.assignment.model.Plan_Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends CrudRepository<Plan_Recipe, Long> {
    @Query("from Plan_Recipe e where not(e.end < :from or e.start > :to)")
    List<Plan_Recipe> findBetween(@Param("from") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);
}
