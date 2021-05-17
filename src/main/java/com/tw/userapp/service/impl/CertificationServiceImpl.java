package com.tw.userapp.service.impl;

import com.tw.userapp.service.CertificationService;
import com.tw.userapp.domain.Certification;
import com.tw.userapp.repository.CertificationRepository;
import com.tw.userapp.service.dto.CertificationDTO;
import com.tw.userapp.service.mapper.CertificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Certification}.
 */
@Service
@Transactional
public class CertificationServiceImpl implements CertificationService {

    private final Logger log = LoggerFactory.getLogger(CertificationServiceImpl.class);

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    public CertificationServiceImpl(CertificationRepository certificationRepository, CertificationMapper certificationMapper) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
    }

    @Override
    public CertificationDTO save(CertificationDTO certificationDTO) {
        log.debug("Request to save Certification : {}", certificationDTO);
        Certification certification = certificationMapper.toEntity(certificationDTO);
        certification = certificationRepository.save(certification);
        return certificationMapper.toDto(certification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificationDTO> findAll() {
        log.debug("Request to get all Certifications");
        return certificationRepository.findAll().stream()
            .map(certificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CertificationDTO> findOne(Long id) {
        log.debug("Request to get Certification : {}", id);
        return certificationRepository.findById(id)
            .map(certificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Certification : {}", id);
        certificationRepository.deleteById(id);
    }
}
