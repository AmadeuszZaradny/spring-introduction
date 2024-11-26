package pl.umk.workshop.springintroduction.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.umk.workshop.springintroduction.domain.models.Student;

import java.util.List;

import static pl.umk.workshop.springintroduction.domain.models.Item.JACKET;
import static pl.umk.workshop.springintroduction.domain.models.Item.SCARF;

@Component
public class CloakroomFeeder {

    private final UmkCloakroomFacade umkCloakroomFacade;

    public CloakroomFeeder(UmkCloakroomFacade umkCloakroomFacade) {
        this.umkCloakroomFacade = umkCloakroomFacade;
    }

    @PostConstruct
    void feed() {
        var me = new Student("Amadeusz", "Zaradny");
        var items = List.of(JACKET, SCARF);
        for (int i = 0; i < 10; i++) {
            umkCloakroomFacade.depositItems(me, items);
        }
    }
}
