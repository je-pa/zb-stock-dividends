package com.zb.zbstockdividends.service;

import com.zb.zbstockdividends.model.Company;
import com.zb.zbstockdividends.model.Dividend;
import com.zb.zbstockdividends.model.ScrapedResult;
import com.zb.zbstockdividends.model.constants.CacheKey;
import com.zb.zbstockdividends.persist.CompanyRepository;
import com.zb.zbstockdividends.persist.DividendRepository;
import com.zb.zbstockdividends.persist.entity.CompanyEntity;
import com.zb.zbstockdividends.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    // 요청이 자주 들어오는가?
    // 자주 변경이 되는 데이터인가?
    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search company ->"+companyName);
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity companyEntity = this.companyRepository.findByName(companyName)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 회사명입니다."));

        // 2. 조회된 회사 ID 로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(companyEntity.getId());

        // 3. 결과 조합 후 반환
        return ScrapedResult.builder()
                .company(Company.fromEntity(companyEntity))
                .dividends(dividendEntities.stream().map(Dividend::fromEntity).toList())
                .build();

//        throw new NotYetImplementedException();
    }
}
