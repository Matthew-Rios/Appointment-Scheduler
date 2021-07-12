package com.mattrios.customers;

import java.time.ZonedDateTime;

public class NoContactCustomer extends Customer {
    @Override
    public String getContactFrequency() {
        return "Do Not Contact";
    }

    @Override
    public boolean contactFrequencyViolation(ZonedDateTime start, ZonedDateTime end) {
        return true;
    }
}
