package service;

import DTO.Banking;
import bankingEnum.MESSENGER;

import java.sql.SQLException;

public interface BankingService {
    MESSENGER join(Banking banking) throws SQLException;

    MESSENGER login(Banking banking) throws SQLException;


    MESSENGER deposit(Banking banking) throws SQLException;

    MESSENGER withdraw(Banking banking) throws SQLException;

    MESSENGER accountList() throws SQLException;

    MESSENGER bankingHistory(Banking banking) throws SQLException;

    MESSENGER accountTransfer(String receiver, Banking banking) throws SQLException;
}
