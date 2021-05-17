package com.tw.userapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tw.userapp.domain.AcademicExperience} entity.
 */
public class AcademicExperienceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String place;

    @NotNull
    @Size(min = 3)
    private String degreeName;

    private String description;

    private Instant startDate;

    private Instant endDate;


    private String candidateId;

    public Long getId() {
        return id;
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

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
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

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicExperienceDTO)) {
            return false;
        }

        return id != null && id.equals(((AcademicExperienceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicExperienceDTO{" +
            "id=" + getId() +
            ", place='" + getPlace() + "'" +
            ", degreeName='" + getDegreeName() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", candidateId=" + getCandidateId() +
            "}";
    }
}
