package controller;

import service.BankingService;
import serviceImpl.BankingServiceImpl;

public class BankingController {

    private static BankingController instance = new BankingController();

    private BankingService service;
    private BankingController() {
        this.service =BankingServiceImpl.getInstance();
    }

    public static BankingController getInstance() {
        return instance;
    }
}
