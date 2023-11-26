package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.models.Deposit;
import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    // 1. Create second version of DepositNumberManager which generates only even numbers (extend class EvenDepositNumberManager)
    // 2. Create a spring bean with that implementation and use this as default (primary) implementation
    // ATTENTION: Do not change existing implementation (IncrementalDepositNumberManager)
    // TIP: @Primary
    // Comment: We have two implementations in the Spring Context now, namely:
    // 1. IncrementalDepositNumberManager
    // 2. EvenDepositNumberManager
    // We have to point a default implementation to help Spring Framework choose which one inject to our application.
    @Test
    void primaryBeans() {
        // given
        var student = new Student("Amadeusz", "Zaradny");
        var items = List.of(JACKET);

        // when
        fillCloakroom(student, items);

        // then
        var deposits = umkCloakroomRepository.findAll();
        var depositsNumbers = deposits.stream().map(Deposit::depositId);
        assertTrue(depositsNumbers.allMatch(number -> number % 2 == 0));
    }

    private void fillCloakroom(Student student, List<Item> items) {
        while (true) {
            try {
                umkCloakroomFacade.depositItems(student, items);
            } catch (ExceededMaxNumberException ex) {
                break;
            }
        }
    }
}
