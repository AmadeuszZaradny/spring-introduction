package pl.umk.workshop.springintroduction.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

@Configuration
@EnableConfigurationProperties(UmkCloakroomConfiguration.DepositNumberManagerConfigurationProperties.class)
public class UmkCloakroomConfiguration {

    @Bean
    UmkCloakroomFacade cloakroomFacade(
            UmkCloakroomRepository umkCloakroomRepository,
            DepositNumberManager depositNumberManager
    ) {
        return new UmkCloakroomFacadeImpl(umkCloakroomRepository, depositNumberManager);
    }

    @ConfigurationProperties(prefix = "cloakroom")
    public record DepositNumberManagerConfigurationProperties(Integer maxNumber) {

    }
}