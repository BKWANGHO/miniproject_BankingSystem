package bankingEnum;

import controller.BankingController;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum ApplicationEnum {
    JOIN("0",(i)-> {
        System.out.println("아이디, 비밀번호, 이름, 계좌번호를 입력하세요");
        try {
            System.out.println(BankingController.getInstance().join(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }),
    Login("1",(i)-> {
        System.out.println("아이디, 비밀번호 입력하세요");
        try {
            System.out.println(BankingController.getInstance().login(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }), Deposit("2",(i)-> {
        System.out.println("계좌번호와 금액을 입력하세요");
        try {
            System.out.println(BankingController.getInstance().deposit(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }), Withdraw("3",(i)-> {
        System.out.println("계좌번호, 비밀번호, 금액을 입력하세요");
        try {
            System.out.println(BankingController.getInstance().withdraw(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }), AccountList("4",(i)-> {
        try {
            System.out.println(BankingController.getInstance().accontList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }),BankingHistory("5",(i)-> {
        System.out.println("계좌번호를 입력하세요");
        try {
            System.out.println(BankingController.getInstance().bankingHistory(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }),AccountTransfer("6",(i)-> {
        System.out.println("수신계좌를 입력하세요");
        System.out.println("출금계좌 계좌번호,비밀번호,송금금액을 입력하세요");
        try {
            System.out.println(BankingController.getInstance().accountTransfer(i));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }),
    RETRY("retry",(i)->{
        return true;
    })

    ;

    private final String menu;
    private final Predicate<Scanner> predicate;

    ApplicationEnum(String menu, Predicate<Scanner> predicate) {
        this.menu = menu;
        this.predicate = predicate;
    }

    public static boolean getview(Scanner sc){
        System.out.println("0. 계좌생성\n1.로그인\n2.입금\n3.출금\n4.계좌리스트\n5.거래내역\n6.송금");
        String msg = sc.next();
        return Stream.of(values())
                .filter(i->i.menu.equals(msg))
                .findAny().orElse(RETRY)
                .predicate.test(sc);
    }
}
