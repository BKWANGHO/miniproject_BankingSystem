package serviceImpl;

import DTO.Banking;
import bankingEnum.MESSENGER;
import repository.BankingRepository;
import service.BankingService;

import java.sql.SQLException;

public class BankingServiceImpl implements BankingService {
    private static BankingServiceImpl instance = new BankingServiceImpl();

    private BankingRepository repository;
    private BankingServiceImpl() {
        this.repository = BankingRepository.getInstance();
    }

    public static BankingServiceImpl getInstance() {
        return instance;
    }

    @Override
    public MESSENGER join(Banking banking) throws SQLException {
        return repository.join(banking);
    }

    @Override
    public MESSENGER login(Banking banking) throws SQLException {
        return repository.login(banking);
    }

    @Override
    public MESSENGER deposit(Banking banking) throws SQLException {
        repository.historySave(banking);
        return repository.deposit(banking);
    }

    @Override
    public MESSENGER withdraw(Banking banking) throws SQLException {
        repository.historySave(banking);
        return repository.withdraw(banking);
    }

    @Override
    public MESSENGER accountList() throws SQLException {
        return repository.accountList();
    }

    @Override
    public MESSENGER bankingHistory(Banking banking) throws SQLException {
        return repository.bankingHistory(banking);
    }


}
