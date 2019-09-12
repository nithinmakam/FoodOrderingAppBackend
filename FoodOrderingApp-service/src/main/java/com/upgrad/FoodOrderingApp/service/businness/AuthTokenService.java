package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;


public interface AuthTokenService {

    void invalidateToken(@NotNull String accessToken) throws AuthorizationFailedException;


}
