package pl.umk.workshop.springintroduction.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.umk.workshop.springintroduction.domain.models.Deposit;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.infrastructure.UmkCloakroomRepository;

import java.util.List;

public class UmkCloakroomFacadeImpl implements UmkCloakroomFacade {

    private static final Logger logger = LoggerFactory.getLogger(UmkCloakroomFacadeImpl.class);

    private final UmkCloakroomRepository umkCloakroomRepository;

    private final DepositNumberManager depositNumberManager;

    public UmkCloakroomFacadeImpl(
            UmkCloakroomRepository umkCloakroomRepository,
            DepositNumberManager depositNumberManager
    ) {
        this.umkCloakroomRepository = umkCloakroomRepository;
        this.depositNumberManager = depositNumberManager;
    }

    @Override
    public Deposit depositItems(Student student, List<Item> items) {
        logger.info("Depositing items {} for student {}", items, student);
        var deposit = new Deposit(depositNumberManager.getNextFreeNumber(), student, items);
        umkCloakroomRepository.save(deposit);
        return deposit;
    }

    @Override
    public void collectDeposit(Integer depositId) {
        logger.info("Collecting deposit {}", depositId);
        umkCloakroomRepository.removeById(depositId);
        depositNumberManager.releaseNumber(depositId);
    }
}
