package com.platform.online_course.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    protected String username;
    protected String email;
    protected String password;

    // ----------------------------------------------------------------- //
    public BaseUser() {
    }

    public BaseUser(String username, String password, String email) {
        this();
        this.username = username;
        this.password = password;
        this.email = email == null ? null : email.toLowerCase();
    }

    //Getters 

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    // Abstract methods
    public abstract String getRole();

    public abstract String getDashboardURL();

    // Backward-compatible alias for older call sites.
    public String getDashboradURL() {
        return getDashboardURL();
    }
    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) throws Exception {
        password = password.trim();
        if (password.length() >= 8 ) {
            this.password = password;
        } else {
            throw new Exception("Password must have a proper length");
        }
    }

    public void setEmail(String email) throws Exception {
        email = email.trim();
        email = email.toLowerCase();
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Classic overriden methods
    @Override
    public int hashCode() {
        return Objects.hash(this.email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        BaseUser other = (BaseUser) obj;
        return Objects.equals(this.email, other.email);
    }
}