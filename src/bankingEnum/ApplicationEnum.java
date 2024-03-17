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
            System.out.println(BankingController.getInstance().Withdraw(i));
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
        System.out.println("0. 계좌생성\n1.로그인\n2.입금\n3.출금");
        String msg = sc.next();
        return Stream.of(values())
                .filter(i->i.menu.equals(msg))
                .findAny().orElse(RETRY)
                .predicate.test(sc);
    }
}
