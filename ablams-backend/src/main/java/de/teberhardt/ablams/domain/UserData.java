package de.teberhardt.ablams.domain;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * A Audiobook.
 */
@Entity
@UserDefinition
public class UserData implements Serializable {

    @Id
    @GeneratedValue
    public Long id;

    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;
}
