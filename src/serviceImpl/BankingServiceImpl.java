package serviceImpl;

import repository.BankingRepository;
import service.BankingService;

public class BankingServiceImpl implements BankingService {
    private static BankingService instance = new BankingServiceImpl();

    private BankingRepository repository;
    private BankingServiceImpl() {
        this.repository = BankingRepository.getInstance();
    }

    public static BankingService getInstance() {
        return instance;
    }
}
