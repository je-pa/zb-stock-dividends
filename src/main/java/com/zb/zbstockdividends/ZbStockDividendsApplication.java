package com.zb.zbstockdividends;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class ZbStockDividendsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ZbStockDividendsApplication.class, args);
        AutoComplete autoComplete = new AutoComplete();
        AutoComplete autoComplete1 = new AutoComplete();

        autoComplete.add("hello");

        System.out.println(autoComplete.get("hello"));
        System.out.println(autoComplete1.get("hello"));
    }

}
