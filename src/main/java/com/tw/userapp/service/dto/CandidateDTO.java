package com.tw.userapp.service.dto;

import com.tw.userapp.service.models.Skill;
import com.tw.userapp.service.models.Skills;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.tw.userapp.domain.Candidate} entity.
 */
@ApiModel(description = "The Candidate entity.")
public class CandidateDTO implements Serializable {

    private String id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    private String personalStatement;

    private Long phone;

    private String login;

    private String code;

    private AddressDTO address;

    private CountryDTO country;
    private List<ProfessionalExperienceDTO> professionalExperience;

    private List<AcademicExperienceDTO> academicExperience;

    private List<Skill> skills;

    private String firstName;

    private String lastName;

    private String email;

    private List<Long> skillId;

    private int yearsOfExperience;

    private long degreeId;

    private String degreeName;

    public CandidateDTO() {

    }

    public List<AcademicExperienceDTO> getAcademicExperience() {
        return academicExperience;
    }

    public void setAcademicExperience(List<AcademicExperienceDTO> academicExperience) {
        this.academicExperience = academicExperience;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(long degreeId) {
        this.degreeId = degreeId;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<Long> getSkillId() {
        return skillId;
    }

    public void setSkillId(List<Long> skillId) {
        this.skillId = skillId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }

    public Long getPhone() {
        return phone;
    }

    public CountryDTO getCountry() {
		return country;
	}

	public void setCountry(CountryDTO country) {
		this.country = country;
	}

	public void setPhone(Long phone) {
        this.phone = phone;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateDTO)) {
            return false;
        }

        return login != null && login.equals(((CandidateDTO) o).login);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {

        String result = "CandidateDTO{" +
            ", id='"+ getId() + "'" +
            ", personalStatement='" + getPersonalStatement() + "'" +
            ", phone=" + getPhone();
        if(address != null)
            result += address.toString();
        if(skills != null) {
            result += skills.toString();
        }
        return result;
    }

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public List<ProfessionalExperienceDTO> getProfessionalExperience() {
		return professionalExperience;
	}

	public void setProfessionalExperience(List<ProfessionalExperienceDTO> professionalExperience) {
		this.professionalExperience = professionalExperience;
	}

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
