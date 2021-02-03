package service;

import com.google.gson.Gson;
import model.Orgs;
import model.Params;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class Parser {

    public List<Params> parseJson()  {
        String way = "last.json";

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(way));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Orgs orgs = gson.fromJson(bufferedReader, Orgs.class);

        return orgs.getOrgs();
    }

    public void printAllCompany() {
        parseJson().forEach(elem -> { System.out.println(elem.getOrgName() +
                " Дата основания " + elem.getDateOfFoundation()); });
    }
    public void expiredDate()  {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat();

        String startDate = dateFormat.format(currentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/yy");

        Date start = null;
        try {
            start = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Parser p = new Parser();

        AtomicInteger reuslt = new AtomicInteger(0);
        Date finalStart = start;
        p.parseJson().stream()
                .map(elem -> {
                    System.out.println("Компании " + elem.getOrgName() + " принадлежат бумаги с истёкшим сроком");
                    return elem.getSecurities();
                })
                .flatMap(List::stream)
                .filter(elem -> {
                    try {
                        return sdf.parse(elem.getTimeEnd()).before(finalStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .forEach(elem -> {
                    reuslt.getAndIncrement();
                    System.out.println("Название " + elem.getName() + " Код " + elem.getCode() + " Дата истечения "
                            + elem.getTimeEnd());
                });
        System.out.println("Всего просроченных ценных бумаг " + reuslt);
    }
    public void checkDateOrg(String date)  {
        Parser p = new Parser();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/yy");
        Date datte = null;
        if(date.contains("/")) {
            try {
                datte = sdf1.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            try {
                datte = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date finalDatte = datte;
        p.parseJson().stream().filter(elem-> {
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd/mm/yy");
            try {
                Date dFn3 = sdf3.parse(elem.getDateOfFoundation());
                return finalDatte.before(dFn3);
            } catch (ParseException e) {
                return false;
            }
        }).forEach(elem -> {
            System.out.println(elem.getOrgName() + " Основан после " + date);
        });
    }

    public void getCurrency(String currency)  {
        Parser p = new Parser();
        p.parseJson().stream()
                .map(elem -> {
                    return elem.getSecurities();
                })
                .flatMap(List::stream)
                .filter(elem -> currency.equals(elem.getCurrency()))
                .forEach(elem -> {
                    System.out.println("В валюте " + currency + " торгуются бумаги " + elem.getName()
                            + " Код " + elem.getCode() + " " + elem.getCurrency() + " id "
                            + elem.getId());
                });
    }
}

