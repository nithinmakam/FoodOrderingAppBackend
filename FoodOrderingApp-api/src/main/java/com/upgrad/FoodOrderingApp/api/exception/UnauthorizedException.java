package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.service.common.ErrorCode;

public class UnauthorizedException extends RestException {

    public UnauthorizedException(final ErrorCode errorCode, final Object... parameters){
        super(errorCode, parameters);
    }

}

