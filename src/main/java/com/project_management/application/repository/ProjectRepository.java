package com.project_management.application.repository;

import com.project_management.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCreatorEmail(String email);
    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.email = :email")
    List<Project> findProjectsByUserEmail(@Param("email") String email);
}