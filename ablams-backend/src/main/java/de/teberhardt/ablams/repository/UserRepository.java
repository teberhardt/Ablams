package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;


/**
 * Spring Data  repository for the Audiofile entity.
 */
@SuppressWarnings("unused")
@Singleton
public class UserRepository implements PanacheRepository<User> {

    /**
     * Adds a new user in the database
     * @param username the user name
     * @param password the unencrypted password (it will be encrypted with bcrypt)
     * @param role the comma-separated roles
     */
    public void add(String username, String password, String role) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        persist(user);
    }
}
