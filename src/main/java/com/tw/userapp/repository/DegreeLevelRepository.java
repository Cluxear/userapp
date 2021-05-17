package com.tw.userapp.repository;

import com.tw.userapp.domain.DegreeLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DegreeLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegreeLevelRepository extends JpaRepository<DegreeLevel, Long> {
}
