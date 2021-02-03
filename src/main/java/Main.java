
import service.Parser;

import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Parser p = new Parser();
        p.printAllCompany();
        p.expiredDate();
        System.out.println("Введите дату, что бы получить список компаний основанных после неё");
        p.checkDateOrg(scan.next());
        p.checkDateOrg("21.05.2005");
        System.out.println("Введите валюту, что бы получить список бумаг торгующихся в этой валюте");
        p.getCurrency(scan.next());
        p.getCurrency("EU");

    }
}

