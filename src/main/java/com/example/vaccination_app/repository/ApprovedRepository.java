package com.example.vaccination_app.repository;

import com.example.vaccination_app.model.Approved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedRepository extends JpaRepository<Approved, Long> {}
