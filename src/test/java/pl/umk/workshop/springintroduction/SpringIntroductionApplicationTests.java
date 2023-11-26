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


    // Use in application IncrementalDepositNumberManager
    // ATTENTION: Do not change existing implementations of DepositNumberManager
    // TIP: @Qualifier
    // Comment: Like in the previous test we have two implementations in the Spring Context. Moreover, one of them is
    // our default implementation. So we have to choose explicitly desired implementation.
    @Test
    void qualifyingBeans() {
        // given
        var student = new Student("Amadeusz", "Zaradny");
        var items = List.of(JACKET);

        // when
        fillCloakroom(student, items);

        // then
        var deposits = umkCloakroomRepository.findAll();

        assertEquals(5050, deposits.stream().map(Deposit::depositId).reduce(Integer::sum).get());
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
