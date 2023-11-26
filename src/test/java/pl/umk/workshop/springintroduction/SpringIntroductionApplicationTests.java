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


    // Using class CloakroomFeeder add 10 deposits to database after the application has started
    // TIP: @PostConstruct
    // Note: @PreDestroy is interesting too :)
    @Test
    void postConstructing() {
        // expect
        assertEquals(10, umkCloakroomRepository.findAll().size());
    }
}
