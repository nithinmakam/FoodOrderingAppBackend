package com.upgrad.FoodOrderingApp.service.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provider for date time objects.
 */
public final class DateTimeProvider {

    private DateTimeProvider() {
        // prohibit instantiation
    }

    public static ZonedDateTime currentProgramTime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

}

