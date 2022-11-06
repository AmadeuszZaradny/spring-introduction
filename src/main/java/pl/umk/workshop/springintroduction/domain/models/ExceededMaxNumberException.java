package pl.umk.workshop.springintroduction.domain.models;

public class ExceededMaxNumberException extends RuntimeException {
    public ExceededMaxNumberException(Integer maxNumber) {
        super(String.format("Exceeded maximum number (maxNumber=%s)", maxNumber));
    }
}
