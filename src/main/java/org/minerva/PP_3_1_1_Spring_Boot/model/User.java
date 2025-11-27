package org.minerva.PP_3_1_1_Spring_Boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "users")
@Component
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (unique = true, nullable = false, updatable = false)
    private String businessKey;

    @Column
    @NotNull
    @NotEmpty (message = "Field name can't be empty")
    @Size (min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column
    @NotNull
    @NotEmpty (message = "Field lastname can't be empty")
    @Size (min = 2, max = 30, message = "Lastname should be between 2 and 30 characters")
    private String lastname;

    @Column (unique = true)
    @NotNull
    @NotEmpty (message = "Field e-mail can't be empty")
    @Size (min = 5, max = 50, message = "E-mail should be between 2 and 50 characters")
    private String email;

    public User () {
        this.businessKey = UUID.randomUUID().toString();
    }

    public User (String name, String lastname, String email) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getLastname () {
        return lastname;
    }

    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    @Override
    public String toString () {
        return "user id: " + id + ", name: " + name + ", lastname: " + lastname + ", email: " + email;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(businessKey, user.businessKey);
    }

    @Override
    public int hashCode () {
        return Objects.hash(businessKey);
    }
}
