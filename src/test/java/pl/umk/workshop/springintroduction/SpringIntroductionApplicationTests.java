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


    // Extract max number to application configuration
    // ATTENTION: You should remove constant value MAX_NUMBER
    // TIP: @ConfigurationProperties or @Value
    // Comment: Sometimes you want to change some constants in your application.
    // To make it easy avoiding hardcoded values we can use configuration files like `application.properties`.
    @Test
    void readingProperties() {
        // given
        var student = new Student("Amadeusz", "Zaradny");
        var items = List.of(JACKET);

        // when
        fillCloakroom(student, items);

        // then
        var deposits = umkCloakroomRepository.findAll();
        assertEquals(200, deposits.stream().map(Deposit::depositId).reduce(Integer::max).get());
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
