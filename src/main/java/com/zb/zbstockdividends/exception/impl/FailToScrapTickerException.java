package com.zb.zbstockdividends.exception.impl;

import com.zb.zbstockdividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class FailToScrapTickerException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Ticker 스크랩에 실패하였습니다.";
    }
}
