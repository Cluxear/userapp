package com.tw.userapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tw.userapp.domain.ProfessionalExperience} entity.
 */
public class ProfessionalExperienceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String place;

    private long positionId;

    private String description;

    private Instant startDate;

    private Instant endDate;



    private String candidateId;

    private String positionTitle;


    public String getPositionTitle() {
        return positionTitle;
    }

    @Override
    public String toString() {
        return "ProfessionalExperienceDTO{" +
            "id=" + id +
            ", place='" + place + '\'' +
            ", positionId=" + positionId +
            ", description='" + description + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", candidateId='" + candidateId + '\'' +
            ", positionTitle='" + positionTitle + '\'' +
            '}';
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public Long getId() {

        return id;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long position_id) {
        this.positionId = position_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {

    	return this.startDate;
    }

    public void setStartDate(Instant startDate) {

    		this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfessionalExperienceDTO)) {
            return false;
        }

        return id != null && id.equals(((ProfessionalExperienceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
