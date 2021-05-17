package com.tw.userapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SeniorityLevel.
 */
@Entity
@Table(name = "seniority_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SeniorityLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "seniorityLevel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> employes = new HashSet<>();

    @OneToMany(mappedBy = "seniorityLevel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Candidate> candidates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SeniorityLevel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployes() {
        return employes;
    }

    public SeniorityLevel employes(Set<Employee> employees) {
        this.employes = employees;
        return this;
    }

    public SeniorityLevel addEmploye(Employee employee) {
        this.employes.add(employee);
        employee.setSeniorityLevel(this);
        return this;
    }

    public SeniorityLevel removeEmploye(Employee employee) {
        this.employes.remove(employee);
        employee.setSeniorityLevel(null);
        return this;
    }

    public void setEmployes(Set<Employee> employees) {
        this.employes = employees;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public SeniorityLevel candidates(Set<Candidate> candidates) {
        this.candidates = candidates;
        return this;
    }

    public SeniorityLevel addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setSeniorityLevel(this);
        return this;
    }

    public SeniorityLevel removeCandidate(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setSeniorityLevel(null);
        return this;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeniorityLevel)) {
            return false;
        }
        return id != null && id.equals(((SeniorityLevel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeniorityLevel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
