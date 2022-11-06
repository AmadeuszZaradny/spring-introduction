package pl.umk.workshop.springintroduction.domain;

import pl.umk.workshop.springintroduction.domain.models.Deposit;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;

import java.util.List;

public interface UmkCloakroomFacade {

    Deposit depositItems(Student student, List<Item> items);

    void collectDeposit(Integer depositId);

}
