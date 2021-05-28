package com.tw.userapp.service.impl;

import com.tw.userapp.domain.ExperienceDuration;
import com.tw.userapp.repository.ExperienceDurationRepository;
import com.tw.userapp.service.ExperienceDurationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExperienceDurationImpl implements ExperienceDurationService {

    private final ExperienceDurationRepository experienceDurationRepository;

    public ExperienceDurationImpl(ExperienceDurationRepository experienceDurationRepository) {
        this.experienceDurationRepository = experienceDurationRepository;
    }

    @Override
    public List<ExperienceDuration> findAll() {
        return experienceDurationRepository.findAll();
    }
}
