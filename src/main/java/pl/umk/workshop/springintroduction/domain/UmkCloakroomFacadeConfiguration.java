package pl.umk.workshop.springintroduction.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.EvenDepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.IncrementalDepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

@Configuration
public class UmkCloakroomFacadeConfiguration {

    @Bean
    UmkCloakroomFacade umkCloakroomFacade(
            UmkCloakroomRepository umkCloakroomRepository,
            DepositNumberManager depositNumberManager
    ) {
        return new UmkCloakroomFacadeImpl(umkCloakroomRepository, depositNumberManager);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloakroom", name = "manager", havingValue = "incremental")
    DepositNumberManager incrementalDepositNumberManager(
            @Value("${depositManager.maxNumber}") Integer maxNumber
    ) {
        return new IncrementalDepositNumberManager(maxNumber);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloakroom", name = "manager", havingValue = "even")
    DepositNumberManager evenDepositNumberManager(
            @Value("${depositManager.maxNumber}") Integer maxNumber
    ) {
        return new EvenDepositNumberManager(maxNumber);
    }
}