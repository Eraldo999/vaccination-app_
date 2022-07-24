package com.example.vaccination_app.repository;

import com.example.vaccination_app.model.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, Integer> {}
