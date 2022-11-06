package pl.umk.workshop.springintroduction.domain.models;

public class NumberLimitExceededException extends RuntimeException {
    public NumberLimitExceededException(Integer maxNumber) {
        super(String.format("Exceeded maximum number (limit=%s)", maxNumber));
    }
}
