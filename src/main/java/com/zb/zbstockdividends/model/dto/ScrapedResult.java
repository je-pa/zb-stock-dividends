package com.zb.zbstockdividends.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Component
public class ScrapedResult {

    private Company company;

    private List<Dividend> dividends;

    public ScrapedResult() {
        this.dividends = new ArrayList<>();
    }
}
