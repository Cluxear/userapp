package com.tw.userapp.service.impl;

import com.tw.userapp.service.SeniorityLevelService;
import com.tw.userapp.domain.SeniorityLevel;
import com.tw.userapp.repository.SeniorityLevelRepository;
import com.tw.userapp.service.dto.SeniorityLevelDTO;
import com.tw.userapp.service.mapper.SeniorityLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SeniorityLevel}.
 */
@Service
@Transactional
public class SeniorityLevelServiceImpl implements SeniorityLevelService {

    private final Logger log = LoggerFactory.getLogger(SeniorityLevelServiceImpl.class);

    private final SeniorityLevelRepository seniorityLevelRepository;

    private final SeniorityLevelMapper seniorityLevelMapper;

    public SeniorityLevelServiceImpl(SeniorityLevelRepository seniorityLevelRepository, SeniorityLevelMapper seniorityLevelMapper) {
        this.seniorityLevelRepository = seniorityLevelRepository;
        this.seniorityLevelMapper = seniorityLevelMapper;
    }

    @Override
    public SeniorityLevelDTO save(SeniorityLevelDTO seniorityLevelDTO) {
        log.debug("Request to save SeniorityLevel : {}", seniorityLevelDTO);
        SeniorityLevel seniorityLevel = seniorityLevelMapper.toEntity(seniorityLevelDTO);
        seniorityLevel = seniorityLevelRepository.save(seniorityLevel);
        return seniorityLevelMapper.toDto(seniorityLevel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeniorityLevelDTO> findAll() {
        log.debug("Request to get all SeniorityLevels");
        return seniorityLevelRepository.findAll().stream()
            .map(seniorityLevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SeniorityLevelDTO> findOne(Long id) {
        log.debug("Request to get SeniorityLevel : {}", id);
        return seniorityLevelRepository.findById(id)
            .map(seniorityLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeniorityLevel : {}", id);
        seniorityLevelRepository.deleteById(id);
    }
}
