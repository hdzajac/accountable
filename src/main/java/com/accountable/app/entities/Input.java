package com.accountable.app.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "inputs", catalog = "accountable",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "id" }))
public class Input {

    private Long id;
    @NotNull
    private float workingTime;
    private LocalDateTime inputDate;
    @NotNull
    private Company company;
    private User user;

    public Input() {
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "startTime", nullable = false)
    public float getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(float workingTime) {
        this.workingTime = workingTime;
    }

    @Column(name ="inputDate", nullable = false)
    public LocalDateTime getInputDate() {
        return inputDate;
    }

    public void setInputDate(LocalDateTime inputDate) {
        this.inputDate = inputDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
