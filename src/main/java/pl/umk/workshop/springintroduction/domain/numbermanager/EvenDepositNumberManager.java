package pl.umk.workshop.springintroduction.domain.numbermanager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty(
        value="cloakroom.manager",
        havingValue = "even",
        matchIfMissing = false
)
public class EvenDepositNumberManager implements DepositNumberManager {

    private final static Integer MAX_NUMBER = 100;

    private final Set<Integer> reservedNumbers = new HashSet<>();

    @Override
    public Integer getNextFreeNumber() {
        var freeNumber = IntStream.iterate(2, i -> i + 2)
                .filter(number -> !reservedNumbers.contains(number))
                .findFirst()
                .orElseThrow(() -> new ExceededMaxNumberException(MAX_NUMBER));

        reservedNumbers.add(freeNumber);
        return freeNumber;
    }

    @Override
    public void releaseNumber(Integer number) {
        reservedNumbers.remove(number);
    }

    @Override
    public void releaseAll() {
        reservedNumbers.clear();
    }
}
