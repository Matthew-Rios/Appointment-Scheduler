package com.mattrios.customers;

import com.mattrios.Database;
import com.mattrios.appointments.Appointment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MonthlyContactCustomer extends Customer {
    @Override
    public String getContactFrequency() {
        return "Monthly Contact";
    }

    @Override
    public boolean contactFrequencyViolation(ZonedDateTime start, ZonedDateTime end) {
        ArrayList<Appointment> allAppointments = Database.queryCustomerAppointments(getCustomerID());
        Calendar newAppointmentStart = GregorianCalendar.from(start);
        int newStartYear = newAppointmentStart.get(newAppointmentStart.YEAR);
        int newStartMonth = newAppointmentStart.get(newAppointmentStart.MONTH);
        Calendar currentAppointmentStart;

        for (Appointment appointment : allAppointments) {
            currentAppointmentStart = GregorianCalendar.from(appointment.getStartZDT());
            int currentStartYear = currentAppointmentStart.get(currentAppointmentStart.YEAR);
            int currentStartMonth = currentAppointmentStart.get(currentAppointmentStart.MONTH);

            if (newStartYear == currentStartYear && newStartMonth == currentStartMonth) {
                return true;
            }
        }
        return false;
    }
}
