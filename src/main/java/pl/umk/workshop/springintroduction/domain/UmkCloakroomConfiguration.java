package pl.umk.workshop.springintroduction.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

@Configuration
public class UmkCloakroomConfiguration {

    @Bean
    UmkCloakroomFacade umkCloakroomFacade(
            UmkCloakroomRepository umkCloakroomRepository
    ) {
        return new UmkCloakroomFacadeImpl(
            umkCloakroomRepository, new DepositNumberManager()
        );
    }
}
