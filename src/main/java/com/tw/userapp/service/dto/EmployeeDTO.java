package com.tw.userapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tw.userapp.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private String id;

    private Double salary;

    private Long phone;


    private String userId;

    private Long positionId;

    private Long degreeId;

    private Long seniorityLevelId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeLevelId) {
        this.degreeId = degreeLevelId;
    }

    public Long getSeniorityLevelId() {
        return seniorityLevelId;
    }

    public void setSeniorityLevelId(Long seniorityLevelId) {
        this.seniorityLevelId = seniorityLevelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            ", phone=" + getPhone() +
            ", userId='" + getUserId() + "'" +
            ", positionId=" + getPositionId() +
            ", degreeId=" + getDegreeId() +
            ", seniorityLevelId=" + getSeniorityLevelId() +
            "}";
    }
}
