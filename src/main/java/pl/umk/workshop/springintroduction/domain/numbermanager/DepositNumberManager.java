package pl.umk.workshop.springintroduction.domain.numbermanager;

public interface DepositNumberManager {
    Integer getNextFreeNumber();
    void releaseNumber(Integer number);
    void releaseAll();
}
