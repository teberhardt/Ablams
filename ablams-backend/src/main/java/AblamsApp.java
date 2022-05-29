import de.teberhardt.ablams.repository.UserRepository;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@QuarkusMain
public class AblamsApp {

    private static final Logger log = LoggerFactory.getLogger(AblamsApp.class);

    public static void main(String ... args) {
        System.out.println("Running main method");
        Quarkus.run(args);
    }
}


@ApplicationScoped
class AppLifecycleBean {

    @Inject
    UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
    }

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        userRepository.deleteAll();
        userRepository.add("admin", "admin", "admin");
        userRepository.add("user", "user", "user");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}
