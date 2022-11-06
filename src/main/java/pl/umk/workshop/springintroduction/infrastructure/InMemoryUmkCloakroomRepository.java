package pl.umk.workshop.springintroduction.infrastructure;

import org.springframework.stereotype.Repository;
import pl.umk.workshop.springintroduction.domain.models.Deposit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryUmkCloakroomRepository implements UmkCloakroomRepository {

    private final Map<Integer, Deposit> deposits = new HashMap<>();

    @Override
    public Deposit save(Deposit deposit) {
        deposits.put(deposit.depositId(), deposit);
        return deposit;
    }

    @Override
    public Deposit findById(Integer integer) {
        return deposits.get(integer);
    }

    @Override
    public void removeById(Integer integer) {
        deposits.remove(integer);
    }

    @Override
    public List<Deposit> findAll() {
        return deposits.values().stream().toList();
    }

    @Override
    public void removeAll() {
        deposits.clear();
    }
}
