package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import java.util.List;

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

    // Get UmkCloakroomFacade bean from spring context
    // TIP: method getBean()
    // Comment: You can define a name of the Bean using "name" attribute in @Bean annotation.
    // If you do not define a 'name' attribute Spring framework will use a method's name.
    // In our case a method in `UmkCloakroomFacadeConfiguration` class that injects `UmkCloakroomFacade`
    // is named `umkCloakroomFacade` so Spring will name our Bean like that.
    @Test
    void gettingBeanFromSpringContext() {
        // when
        var result = context.getBean("umkCloakroomFacade");

        // then
        UmkCloakroomFacade facadeFromContext = (UmkCloakroomFacade) result;
        facadeFromContext.depositItems(new Student("Amadeusz", "Zaradny"), List.of(JACKET));
    }
}
