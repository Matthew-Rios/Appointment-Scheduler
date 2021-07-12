package com.mattrios.customers;

import java.time.ZonedDateTime;

public class NoContactLimitCustomer extends Customer {
    @Override
    public String getContactFrequency() {
        return "No Contact Limit";
    }

    @Override
    public boolean contactFrequencyViolation(ZonedDateTime start, ZonedDateTime end) {
        return false;
    }
}
