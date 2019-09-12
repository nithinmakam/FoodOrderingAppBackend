package com.upgrad.FoodOrderingApp.api.controller.provider;

import com.upgrad.FoodOrderingApp.api.exception.UnauthorizedException;
import com.upgrad.FoodOrderingApp.api.exception.RestErrorCode;

public class BearerAuthDecoder {

    private final String accessToken;

    String BEARER_AUTH_PREFIX = "Bearer ";

    public BearerAuthDecoder(final String bearerToken) {
        if(!bearerToken.startsWith(BEARER_AUTH_PREFIX)) {
            throw new UnauthorizedException(RestErrorCode.ATH_003);
        }

        final String[] bearerTokens = bearerToken.split(BEARER_AUTH_PREFIX);
        if(bearerTokens.length != 2) {
            throw new UnauthorizedException(RestErrorCode.ATH_004);
        }
        this.accessToken = bearerTokens[1];
    }

    public String getAccessToken() {
        return accessToken;
    }

}