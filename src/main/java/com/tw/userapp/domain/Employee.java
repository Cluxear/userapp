package com.tw.userapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String code;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "phone")
    private Long phone;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Position position;

    @ManyToOne
    @JsonIgnoreProperties(value = "employes", allowSetters = true)
    private DegreeLevel degree;

    @ManyToOne
    @JsonIgnoreProperties(value = "employes", allowSetters = true)
    private SeniorityLevel seniorityLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public Employee salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getPhone() {
        return phone;
    }

    public Employee phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Employee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public Employee position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public DegreeLevel getDegree() {
        return degree;
    }

    public Employee degree(DegreeLevel degreeLevel) {
        this.degree = degreeLevel;
        return this;
    }

    public void setDegree(DegreeLevel degreeLevel) {
        this.degree = degreeLevel;
    }

    public SeniorityLevel getSeniorityLevel() {
        return seniorityLevel;
    }

    public Employee seniorityLevel(SeniorityLevel seniorityLevel) {
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
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            ", phone=" + getPhone() +
            "}";
    }
}
