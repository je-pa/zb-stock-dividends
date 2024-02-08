package com.zb.zbstockdividends.exception.impl;

import com.zb.zbstockdividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UnexpectedMonthException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Month가 이상합니다.";
    }
}
