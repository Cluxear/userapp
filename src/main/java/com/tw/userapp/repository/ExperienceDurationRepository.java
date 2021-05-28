package com.tw.userapp.repository;

import com.tw.userapp.domain.Employee;
import com.tw.userapp.domain.ExperienceDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceDurationRepository extends JpaRepository<ExperienceDuration, Long> {
}
