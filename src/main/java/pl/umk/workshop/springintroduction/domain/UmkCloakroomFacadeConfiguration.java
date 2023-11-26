package pl.umk.workshop.springintroduction.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.EvenDepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.IncrementalDepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

@Configuration
public class UmkCloakroomFacadeConfiguration {

    @Bean
    UmkCloakroomFacade umkCloakroomFacade(
            UmkCloakroomRepository umkCloakroomRepository,
            @Qualifier("incrementalDepositNumberManager") DepositNumberManager depositNumberManager
    ) {
        return new UmkCloakroomFacadeImpl(umkCloakroomRepository, depositNumberManager);
    }

    @Bean
    DepositNumberManager incrementalDepositNumberManager() {
        return new IncrementalDepositNumberManager();
    }

    @Primary
    @Bean
    DepositNumberManager evenDepositNumberManager() {
        return new EvenDepositNumberManager();
    }
}