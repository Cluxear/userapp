package com.tw.userapp.repository;

import com.tw.userapp.domain.Certification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Certification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
