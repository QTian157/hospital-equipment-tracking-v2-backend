
package com.qt.hospital_equipment_tracking.model;

import com.qt.hospital_equipment_tracking.model.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 30)
    private UserRole userRole;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    public UserAccount() {}

    public UserAccount(String username, String password,
                       UserRole userRole, Boolean enabled,
                       Person person) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.enabled = enabled;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
