package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.data.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AuthTokenValidityService {

    public boolean isExpired(final CustomerAuthEntity customerAuthEntity) {
        final ZonedDateTime now = DateTimeProvider.currentProgramTime();
        return customerAuthEntity != null && (customerAuthEntity.getExpires_at().isBefore(now) || customerAuthEntity.getExpires_at().isEqual(now));
    }

    public boolean isLoggedOut(final CustomerAuthEntity customerAuthEntity) {
        return customerAuthEntity != null && customerAuthEntity.getLogout_at() != null;
    }
}
