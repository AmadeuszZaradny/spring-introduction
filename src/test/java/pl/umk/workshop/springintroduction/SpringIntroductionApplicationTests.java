package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.numbermanager.EvenDepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


    // Add to the application.properties a configuration with path 'cloakroom.manager'. When this configuration
    // is set to 'incremental' application should use IncrementalDepositNumberManager, but when it is set to 'even'
    // application should use EvenDepositNumberManager.
    // ATTENTION: Set property to 'cloakroom.manager=even'
    // TIP: @ConditionalOnProperty
    @Test
    void conditionalBeanCreation() {
        // expect
        var property = context.getEnvironment().getProperty("cloakroom.manager");
        var manager = context.getBean(DepositNumberManager.class);

        assertEquals("even", property);
        assertEquals(EvenDepositNumberManager.class, manager.getClass());
    }
}
