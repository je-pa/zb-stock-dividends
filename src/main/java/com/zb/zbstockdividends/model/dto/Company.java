package com.zb.zbstockdividends.model.dto;

import com.zb.zbstockdividends.persist.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
