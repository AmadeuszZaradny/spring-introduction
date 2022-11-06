package pl.umk.workshop.springintroduction.domain;

import pl.umk.workshop.springintroduction.domain.models.NumberLimitExceededException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class DepositNumberManager {

    private final static Integer MAX_NUMBER = 100;

    private final Set<Integer> reservedNumbers = new HashSet<>();

    public Integer getNextFreeNumber() {
        var freeNumber = IntStream.rangeClosed(1, MAX_NUMBER)
                .filter(number -> !reservedNumbers.contains(number))
                .findFirst()
                .orElseThrow(() -> new NumberLimitExceededException(MAX_NUMBER));

        reservedNumbers.add(freeNumber);
        return freeNumber;
    }

    public void releaseNumber(Integer number) {
        reservedNumbers.remove(number);
    }
}
