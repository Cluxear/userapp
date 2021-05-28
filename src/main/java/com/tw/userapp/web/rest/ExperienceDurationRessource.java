package com.tw.userapp.web.rest;

import com.tw.userapp.domain.ExperienceDuration;
import com.tw.userapp.service.ExperienceDurationService;
import com.tw.userapp.service.dto.CountryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExperienceDurationRessource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "userappExperienceDuration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienceDurationService experienceDurationService;


    public ExperienceDurationRessource(ExperienceDurationService experienceDurationService) {
        this.experienceDurationService = experienceDurationService;
    }

    @GetMapping("/experience-durations")
    public List<ExperienceDuration> getAllCountries() {
        log.debug("REST request to get all Countries");
        return experienceDurationService.findAll();
    }
}
