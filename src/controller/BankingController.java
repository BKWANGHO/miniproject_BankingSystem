package controller;

import DTO.Banking;
import bankingEnum.MESSENGER;
import service.BankingService;
import serviceImpl.BankingServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class BankingController {

    private static BankingController instance = new BankingController();

    private BankingService service;
    private BankingController() {
        this.service =BankingServiceImpl.getInstance();
    }

    public static BankingController getInstance() {
        return instance;
    }

    public MESSENGER join(Scanner sc) throws SQLException {
        return service.join(Banking.builder()
                        .username(sc.next())
                        .password(sc.next())
                        .name(sc.next())
                        .balance(0)
                .build());
    }


    public MESSENGER login(Scanner sc) throws SQLException {
        return service.login(Banking.builder()
                .username(sc.next())
                .password(sc.next())
                .build());
    }
}
