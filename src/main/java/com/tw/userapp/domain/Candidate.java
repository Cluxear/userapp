package com.tw.userapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Candidate entity.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String code;

    /**
     * The firstname attribute.
     */
    @Column(name = "personal_statement")
    private String personalStatement;

    @Column(name = "phone")
    private Long phone;

    @OneToOne(cascade=CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne(cascade=CascadeType.ALL)
    @MapsId
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ProfessionalExperience> professionalExperiences = new HashSet<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AcademicExperience> academicExperiences = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Certification> certifications = new HashSet<>();

    @ManyToOne
    private Country country;

    @ManyToOne
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private ExperienceDuration experienceDuration;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private DegreeLevel degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private SeniorityLevel seniorityLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public ExperienceDuration getExperienceDuration() {
        return experienceDuration;
    }

    public void setExperienceDuration(ExperienceDuration experienceDuration) {
        this.experienceDuration = experienceDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public Candidate personalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
        return this;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }

    public Long getPhone() {
        return phone;
    }

    public Candidate phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Candidate user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public Candidate address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<ProfessionalExperience> getProfessionalExperiences() {
        return professionalExperiences;
    }

    public Candidate professionalExperiences(Set<ProfessionalExperience> professionalExperiences) {
        this.professionalExperiences = professionalExperiences;
        return this;
    }

    public Candidate addProfessionalExperience(ProfessionalExperience professionalExperience) {
        this.professionalExperiences.add(professionalExperience);
        professionalExperience.setCandidate(this);
        return this;
    }

    public Candidate removeProfessionalExperience(ProfessionalExperience professionalExperience) {
        this.professionalExperiences.remove(professionalExperience);
        professionalExperience.setCandidate(null);
        return this;
    }

    public void setProfessionalExperiences(Set<ProfessionalExperience> professionalExperiences) {
        this.professionalExperiences = professionalExperiences;
    }

    public Set<AcademicExperience> getAcademicExperiences() {
        return academicExperiences;
    }

    public Candidate academicExperiences(Set<AcademicExperience> academicExperiences) {
        this.academicExperiences = academicExperiences;
        return this;
    }

    public Candidate addAcademicExperience(AcademicExperience academicExperience) {
        this.academicExperiences.add(academicExperience);
        academicExperience.setCandidate(this);
        return this;
    }

    public Candidate removeAcademicExperience(AcademicExperience academicExperience) {
        this.academicExperiences.remove(academicExperience);
        academicExperience.setCandidate(null);
        return this;
    }

    public void setAcademicExperiences(Set<AcademicExperience> academicExperiences) {
        this.academicExperiences = academicExperiences;
    }

    public Set<Certification> getCertifications() {
        return certifications;
    }

    public Candidate certifications(Set<Certification> certifications) {
        this.certifications = certifications;
        return this;
    }

    public Candidate addCertifications(Certification certification) {
        this.certifications.add(certification);
        certification.setCandidate(this);
        return this;
    }

    public Candidate removeCertifications(Certification certification) {
        this.certifications.remove(certification);
        certification.setCandidate(null);
        return this;
    }

    public void setCertifications(Set<Certification> certifications) {
        this.certifications = certifications;
    }




    public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public DegreeLevel getDegree() {
        return degree;
    }

    public Candidate degree(DegreeLevel degreeLevel) {
        this.degree = degreeLevel;
        return this;
    }

    public void setDegree(DegreeLevel degreeLevel) {
        this.degree = degreeLevel;
    }

    public SeniorityLevel getSeniorityLevel() {
        return seniorityLevel;
    }

    public Candidate seniorityLevel(SeniorityLevel seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
        return this;
    }

    public void setSeniorityLevel(SeniorityLevel seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", personalStatement='" + getPersonalStatement() + "'" +
            ", phone=" + getPhone() +
            "}";
    }
}
