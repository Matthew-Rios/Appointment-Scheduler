package com.mattrios.customers;

import com.mattrios.Database;
import com.mattrios.appointments.Appointment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeeklyContactCustomer extends Customer {
    @Override
    public String getContactFrequency() {
        return "Weekly Contact";
    }

    @Override
    public boolean contactFrequencyViolation(ZonedDateTime start, ZonedDateTime end) {
        ArrayList<Appointment> allAppointments = Database.queryCustomerAppointments(getCustomerID());
        Calendar newAppointmentStart = GregorianCalendar.from(start);
        int newStartYear = newAppointmentStart.get(newAppointmentStart.YEAR);
        int newStartWeek = newAppointmentStart.get(newAppointmentStart.WEEK_OF_YEAR);
        Calendar currentAppointmentStart;

        for (Appointment appointment : allAppointments) {
            currentAppointmentStart = GregorianCalendar.from(appointment.getStartZDT());
            int currentStartYear = currentAppointmentStart.get(currentAppointmentStart.YEAR);
            int currentStartWeek = currentAppointmentStart.get(currentAppointmentStart.WEEK_OF_YEAR);

            if (newStartYear == currentStartYear && newStartWeek == currentStartWeek) {
                System.out.println("New Start Year = " + newStartYear + " New Start Week = " + newStartWeek);
                System.out.println("Current Start Year = " + currentStartYear + " Current Start Week = " + currentStartWeek);
                return true;
            }
        }
        return false;
    }
}
