package com.tw.userapp.service.mapper;


import com.tw.userapp.domain.Country;
import com.tw.userapp.domain.ExperienceDuration;
import com.tw.userapp.service.dto.CountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExperienceDuration} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExperienceDurationMapper {


    CountryDTO toDto(Country country);


    Country toEntity(CountryDTO countryDTO);

    default ExperienceDuration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExperienceDuration experienceDuration = new ExperienceDuration();
        experienceDuration.setId(id);
        return experienceDuration;
    }
}
