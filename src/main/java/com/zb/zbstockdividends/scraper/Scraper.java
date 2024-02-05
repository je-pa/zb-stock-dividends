package com.zb.zbstockdividends.scraper;


import com.zb.zbstockdividends.model.Company;
import com.zb.zbstockdividends.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
