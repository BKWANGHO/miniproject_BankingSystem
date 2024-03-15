package service;

import DTO.Banking;
import bankingEnum.MESSENGER;

import java.sql.SQLException;

public interface BankingService {
    MESSENGER join(Banking banking) throws SQLException;

    MESSENGER login(Banking banking) throws SQLException;
}
