package controller;

import DTO.Banking;
import bankingEnum.MESSENGER;
import service.BankingService;
import serviceImpl.BankingServiceImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class BankingController {

    private static BankingController instance = new BankingController();

    private BankingService service;

    private BankingController() {
        this.service = BankingServiceImpl.getInstance();
    }

    public static BankingController getInstance() {
        return instance;
    }

    public MESSENGER join(Scanner sc) throws SQLException {
        return service.join(Banking.builder()
                .username(sc.next())
                .password(sc.next())
                .name(sc.next())
                .accountNumber(sc.next())
                .balance(0)
                .build());
    }


    public MESSENGER login(Scanner sc) throws SQLException {
        return service.login(Banking.builder()
                .username(sc.next())
                .password(sc.next())
                .build());
    }

    public MESSENGER deposit(Scanner sc) throws SQLException {
        return service.deposit(Banking.builder()
                .accountNumber(sc.next())
                .balance(sc.nextInt())
                .transation("입금")
                .build());
    }

    public MESSENGER withdraw(Scanner sc) throws SQLException {
        return service.withdraw(Banking.builder()
                .accountNumber(sc.next())
                .password(sc.next())
                .balance(sc.nextInt())
                .transation("출금")
                .build());
    }

    public MESSENGER accontList() throws SQLException {
        return service.accountList();
    }

    public MESSENGER bankingHistory(Scanner sc) throws SQLException {
        return service.bankingHistory(Banking.builder()
                .accountNumber(sc.next())
                .build());
    }

    public MESSENGER accountTransfer(Scanner sc) throws SQLException {
        int balance = sc.nextInt();
        Banking receiver = Banking.builder()
                .accountNumber(sc.next())
                .balance(balance)
                .transation("입금")
                .build();

        Banking banking = Banking.builder()
                .accountNumber(sc.next())
                .password(sc.next())
                .balance(balance)
                .transation("송금")
                .build();

    return service.accountTransfer(receiver,banking);
    }
}
