package com.tw.userapp.service;

import com.tw.userapp.domain.ExperienceDuration;
import com.tw.userapp.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExperienceDurationService {
    /**
     * Get all experience durations.
     *

     * @return the list of entities.
     */
    List<ExperienceDuration> findAll();
}
