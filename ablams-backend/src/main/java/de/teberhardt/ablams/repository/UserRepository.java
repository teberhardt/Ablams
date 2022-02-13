package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.Audiofile;
import de.teberhardt.ablams.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Audiofile entity.
 */
@SuppressWarnings("unused")
@Singleton
public class UserRepository implements PanacheRepository<User> {


}
