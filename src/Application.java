import bankingEnum.ApplicationEnum;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (ApplicationEnum.getview(sc));
    }
}