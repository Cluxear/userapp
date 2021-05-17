package com.tw.userapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DegreeLevel.
 */
@Entity
@Table(name = "degree_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DegreeLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "degree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Candidate> candidates = new HashSet<>();

    @OneToMany(mappedBy = "degree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> employes = new HashSet<>();

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

    public DegreeLevel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public DegreeLevel candidates(Set<Candidate> candidates) {
        this.candidates = candidates;
        return this;
    }

    public DegreeLevel addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setDegree(this);
        return this;
    }

    public DegreeLevel removeCandidate(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setDegree(null);
        return this;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Employee> getEmployes() {
        return employes;
    }

    public DegreeLevel employes(Set<Employee> employees) {
        this.employes = employees;
        return this;
    }

    public DegreeLevel addEmploye(Employee employee) {
        this.employes.add(employee);
        employee.setDegree(this);
        return this;
    }

    public DegreeLevel removeEmploye(Employee employee) {
        this.employes.remove(employee);
        employee.setDegree(null);
        return this;
    }

    public void setEmployes(Set<Employee> employees) {
        this.employes = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DegreeLevel)) {
            return false;
        }
        return id != null && id.equals(((DegreeLevel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DegreeLevel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
