package pl.umk.workshop.springintroduction.domain.numbermanager;

import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class IncrementalDepositNumberManager implements DepositNumberManager {

    private final Set<Integer> reservedNumbers = new HashSet<>();

    private final static Integer MAX_NUMBER = 100;

    @Override
    public Integer getNextFreeNumber() {
        var freeNumber = IntStream.rangeClosed(1, MAX_NUMBER)
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
