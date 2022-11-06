package pl.umk.workshop.springintroduction.infrastructure;

import pl.umk.workshop.springintroduction.domain.models.Deposit;

public interface UmkCloakroomRepository {
    Deposit save(Deposit deposit);

    Deposit findById(Integer integer);

    void removeById(Integer integer);
}
