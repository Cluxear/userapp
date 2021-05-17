package com.tw.userapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "experience_duration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExperienceDuration implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    public long id;

    public String value;

    @OneToMany(mappedBy = "experienceDuration")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Candidate> candidates = new HashSet<>();


    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
