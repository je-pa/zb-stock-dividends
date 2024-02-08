package com.zb.zbstockdividends.scraper;


import com.zb.zbstockdividends.model.dto.Company;
import com.zb.zbstockdividends.model.dto.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
