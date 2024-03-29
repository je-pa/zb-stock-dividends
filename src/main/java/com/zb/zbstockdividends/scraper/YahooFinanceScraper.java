package com.zb.zbstockdividends.scraper;

import com.zb.zbstockdividends.exception.impl.UnexpectedMonthException;
import com.zb.zbstockdividends.model.constants.Month;
import com.zb.zbstockdividends.model.dto.Company;
import com.zb.zbstockdividends.model.dto.Dividend;
import com.zb.zbstockdividends.model.dto.ScrapedResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class YahooFinanceScraper implements Scraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    private static final long START_TIME = 86400;   // 60 * 60 * 24

    private final ScrapedResult scrapedResult;

    @Override
    public ScrapedResult scrap(Company company) {
        scrapedResult.setCompany(company);

        try {
            long now = System.currentTimeMillis() / 1000; // 초단위로

            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, now);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);  // table 전체

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends = new ArrayList<>();
            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if (month < 0) {
                    throw new UnexpectedMonthException();
                }

                dividends.add(Dividend.builder()
                        .date(LocalDateTime.of(year,month,day,0,0))
                        .dividend(dividend)
                        .build());

            }
            scrapedResult.setDividends(dividends);

        } catch (IOException e) {
            // TODO error handling
            e.printStackTrace();
        }

        return scrapedResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().replaceAll("\\(.*", "").trim();

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
