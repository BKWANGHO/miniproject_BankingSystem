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
}
