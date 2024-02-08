package com.zb.zbstockdividends.exception.impl;

import com.zb.zbstockdividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NoUserIdException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 ID 입니다.";
    }
}
