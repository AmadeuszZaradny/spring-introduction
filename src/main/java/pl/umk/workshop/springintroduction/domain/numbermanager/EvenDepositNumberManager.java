package pl.umk.workshop.springintroduction.domain.numbermanager;

import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class EvenDepositNumberManager implements DepositNumberManager {

    private final Set<Integer> reservedNumbers = new HashSet<>();

    private final Integer maxNumber;

    public EvenDepositNumberManager(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public Integer getNextFreeNumber() {
        var freeNumber = IntStream.rangeClosed(1, maxNumber)
                .filter(number -> !reservedNumbers.contains(number))
                .filter(number -> number % 2 == 0)
                .findFirst()
                .orElseThrow(() -> new ExceededMaxNumberException(maxNumber));

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
