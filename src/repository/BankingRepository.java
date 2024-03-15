package repository;

public class BankingRepository {
    private static BankingRepository instance = new BankingRepository();

    private BankingRepository() {
    }

    public static BankingRepository getInstance() {
        return instance;
    }
}
