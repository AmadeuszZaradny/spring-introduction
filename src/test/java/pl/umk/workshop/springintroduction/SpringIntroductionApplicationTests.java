package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacadeImpl;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.umk.workshop.springintroduction.domain.models.Item.JACKET;

class SpringIntroductionApplicationTests extends TestsBase {

    private UmkCloakroomFacade umkCloakroomFacade;

    private UmkCloakroomRepository umkCloakroomRepository;

    private DepositNumberManager depositNumberManager;

    private ApplicationContext context;

    @Autowired
    public SpringIntroductionApplicationTests(
            UmkCloakroomFacade umkCloakroomFacade,
            UmkCloakroomRepository umkCloakroomRepository,
            DepositNumberManager depositNumberManager,
            ApplicationContext context
    ) {
        this.umkCloakroomFacade = umkCloakroomFacade;
        this.umkCloakroomRepository = umkCloakroomRepository;
        this.depositNumberManager = depositNumberManager;
        this.context = context;
    }

    // Create beans in the spring context and inject them to UmkCloakroomFacadeImpl using configuration class
    // TIP: Look at @Bean and @Configuration
    @Test
    void dependencyInjectionWithConfigurationClass() {
        // given
        var student = new Student("Amadeusz", "Zaradny");
        var items = List.of(JACKET);

        // when
        var result = umkCloakroomFacade.depositItems(student, items);

        // then
        var deposit = umkCloakroomRepository.findById(result.depositId());
        assertEquals(List.of(JACKET), deposit.items());
        assertEquals("Amadeusz", deposit.student().name());
        assertEquals("Zaradny", deposit.student().surname());

        // and
        assertNotConfiguredWithAnnotations(UmkCloakroomFacadeImpl.class);
        assertNotConfiguredWithAnnotations(UmkCloakroomFacade.class);
    }
}
