package com.zb.zbstockdividends.service;

import com.zb.zbstockdividends.model.Company;
import com.zb.zbstockdividends.model.ScrapedResult;
import com.zb.zbstockdividends.persist.CompanyRepository;
import com.zb.zbstockdividends.persist.DividendRepository;
import com.zb.zbstockdividends.persist.entity.CompanyEntity;
import com.zb.zbstockdividends.persist.entity.DividendEntity;
import com.zb.zbstockdividends.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Trie;
//import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final Trie trie;
    private final Scraper yahooFinanceScraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        if(this.companyRepository.existsByTicker(ticker)){
            throw new RuntimeException("already exists ticker -> " + ticker);
        }
//        throw new NotYetImplementedException();
        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
//        throw new NotYetImplementedException();
        return this.companyRepository.findAll(pageable);
    }

    private Company storeCompanyAndDividend(String ticker) {
        // 1. ticker 를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if(ObjectUtils.isEmpty(company)){
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }
        // 2. 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 3. 스크래핑 결과 반환
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntityList = scrapedResult.getDividends().stream()
                .map(e -> new DividendEntity(companyEntity.getId(), e))
                .collect(Collectors.toList());
        this.dividendRepository.saveAll(dividendEntityList);
//        throw new NotYetImplementedException();
        return company;
    }

//    public List<String> getCompanyNamesByKeyword(String keyword) {
////        throw new NotYetImplementedException();
//    }

    public void addAutocompleteKeyword(String keyword) {
        this.trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        return (List<String>) this.trie.prefixMap(keyword).keySet()
                .stream()
                .collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }

//    public String deleteCompany(String ticker) {
//        // 1. 배당금 정보 삭제
//        // 2. 회사 정보 삭제
////        throw new NotYetImplementedException();
//    }

}
