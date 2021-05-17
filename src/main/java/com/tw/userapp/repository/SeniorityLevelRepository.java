package com.tw.userapp.repository;

import com.tw.userapp.domain.SeniorityLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SeniorityLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeniorityLevelRepository extends JpaRepository<SeniorityLevel, Long> {
}
