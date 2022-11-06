package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacadeImpl;
import pl.umk.workshop.springintroduction.domain.models.Deposit;
import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.domain.numbermanager.EvenDepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.umk.workshop.springintroduction.domain.models.Item.JACKET;

// Tasks depend on each other. Please do them in order.
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

    // Create beans in the spring context and inject them to UmkCloakroomFacadeImpl
    // TIP: Look at @Component and @Autowired
    @Test
    void dependencyInjectionWithAnnotations() {
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

    // Get UmkCloakroomFacade bean from spring context
    // TIP: method getBean()
    @Test
    void gettingBeanFromSpringContext() {
        // when
        var result = context;

        // then
        UmkCloakroomFacade facadeFromContext = (UmkCloakroomFacade) result;
        facadeFromContext.depositItems(new Student("Amadeusz", "Zaradny"), List.of(JACKET));
    }

    // 1. Create second version of DepositNumberManager which generates only even numbers (extend class EvenDepositNumberManager)
    // 2. Create a spring bean with that implementation and use this as default (primary) implementation
    // ATTENTION: Do not change existing implementation (IncrementalDepositNumberManager)
    // TIP: @Primary
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

    // Use in application IncrementalDepositNumberManager
    // ATTENTION: Do not change existing implementations of DepositNumberManager
    // TIP: @Qualifier
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

    // Extract max number to application configuration
    // ATTENTION: You should remove constant value MAX_NUMBER
    // TIP: @ConfigurationProperties or @Value
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

    // Using class CloakroomFeeder add 10 deposits to database after the application has started
    // TIP: @PostConstruct
    // Note: @PreDestroy is interesting too :)
    @Test
    void postConstructing() {
        // expect
        assertEquals(10, umkCloakroomRepository.findAll().size());
    }

    // This code clears the database before each test :)
    @AfterEach
    void setUp() {
        umkCloakroomRepository.removeAll();
        depositNumberManager.releaseAll();
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
