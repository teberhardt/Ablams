package de.teberhardt.ablams.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Ablams.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
