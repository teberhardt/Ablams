package de.teberhardt.ablams.domain;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A Audiobook.
 */
@Entity
@Table(name = "users")
@UserDefinition
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

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
