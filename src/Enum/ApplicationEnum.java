package Enum;

import view.BankingView;

import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum ApplicationEnum {
    ACCOUNT("ac",(i)-> {
        BankingView.account(i);
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

    public boolean getview(Scanner sc){
        String msg = sc.next();
        return Stream.of(values())
                .filter(i->i.menu.equals(msg))
                .findAny().orElse(RETRY)
                .predicate.test(sc);
    }
}
