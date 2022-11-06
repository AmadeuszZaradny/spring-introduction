package pl.umk.workshop.springintroduction.domain.numbermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomConfiguration;
import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty(
        value="cloakroom.manager",
        havingValue = "incremental",
        matchIfMissing = true
)
public class IncrementalDepositNumberManager implements DepositNumberManager {

    private final Set<Integer> reservedNumbers = new HashSet<>();

    private final UmkCloakroomConfiguration.DepositNumberManagerConfigurationProperties properties;

    @Autowired
    IncrementalDepositNumberManager(
            UmkCloakroomConfiguration.DepositNumberManagerConfigurationProperties properties
    ) {
        this.properties = properties;
    }

    @Override
    public Integer getNextFreeNumber() {
        var freeNumber = IntStream.rangeClosed(1, properties.maxNumber())
                .filter(number -> !reservedNumbers.contains(number))
                .findFirst()
                .orElseThrow(() -> new ExceededMaxNumberException(properties.maxNumber()));

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
