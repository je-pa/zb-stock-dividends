package com.zb.zbstockdividends.model;

import com.zb.zbstockdividends.persist.entity.CompanyEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Company {

    private String ticker;
    private String name;

    public static Company fromEntity(CompanyEntity entity){
        return Company.builder()
                .ticker(entity.getTicker())
                .name(entity.getName())
                .build();
    }
}
