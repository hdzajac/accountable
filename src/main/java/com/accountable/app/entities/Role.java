package com.accountable.app.entities;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles", catalog = "accountable",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "id" }))
public class Role {

    private Long id;
    private String role;
    private Set<User> users;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",
            unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long userRoleId) {
        this.id = userRoleId;
    }


    @Column(name = "role", nullable = false, length = 45)
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToMany(mappedBy = "role")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
