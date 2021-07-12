package com.mattrios.appointments;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Matt Rios
 */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Empty constructor method.
     */
    public Appointment() {
    }

    /**
     * getter for appointmentID.
     *
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * setter for appointmentID.
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * getter for title of the appointment.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for appointment title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for appointment description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for location of the appointment.
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter for appointment location.
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter for type of appointment.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * setter for appointment type.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for start timestamp. Converts from UTC to local time.
     *
     * @return the localized start time/date
     */
    public Timestamp getStart() {
        ZonedDateTime localZonedDateTime = start.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedDateTime.toLocalDateTime());
    }

    /**
     * getter for start timestamp in UTC.
     *
     * @return start, the start time/date in UTC
     */
    public Timestamp getStartUTC() {
        return Timestamp.valueOf(start.toLocalDateTime());
    }

    public ZonedDateTime getStartZDT() {
        return start;
    }

    /**
     * setter for appointment start timestamp.
     *
     * @param startDateTime the ZoneDateTime for the end of the appointment in UTC
     */
    public void setStart(ZonedDateTime startDateTime) {
        this.start = startDateTime;
    }

    /**
     * getter for end timestamp. Converts from UTC to local time.
     *
     * @return the localized end time/date
     */
    public Timestamp getEnd() {
        ZonedDateTime localZoneDateTime = end.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZoneDateTime.toLocalDateTime());
    }

    /**
     * getter for end timestamp in UTC.
     *
     * @return the end time/date in UTC
     */
    public Timestamp getEndUTC() {
        return Timestamp.valueOf(end.toLocalDateTime());
    }

    /**
     * setter for appointment end timestamp.
     *
     * @param endDateTime the ZoneDateTime for the end of the appointment in UTC
     */
    public void setEnd(ZonedDateTime endDateTime) {
        this.end = endDateTime;
    }

    /**
     * getter for create date timestamp, Converts from UTC to local time.
     *
     * @return the localized create time/date
     */
    public Timestamp getCreateDate() {
        ZonedDateTime localZonedCreateDate = createDate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedCreateDate.toLocalDateTime());
    }

    /**
     * getter for create date timestamp in UTC.
     *
     * @return the create time/date in UTC
     */
    public Timestamp getCreateDateUTC() {
        return Timestamp.valueOf(createDate.toLocalDateTime());
    }

    /**
     * setter for appointment creation date/time timestamp.
     *
     * @param createDateTime in UTC
     */
    public void setCreateDate(ZonedDateTime createDateTime) {
        this.createDate = createDateTime;
    }

    /**
     * getter for who created the appointment.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setter for the name of who created the appointment.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getter for the localized timestamp of the last update.
     *
     * @return lastUpdate converted to local time
     */
    public Timestamp getLastUpdate() {
        ZonedDateTime localZonedLastUpdate = lastUpdate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedLastUpdate.toLocalDateTime());
    }

    /**
     * getter for the timestamp of the last update in UTC.
     *
     * @return lastUpdate in UTC
     */
    public Timestamp getLastUpdateUTC() {
        return Timestamp.valueOf(lastUpdate.toLocalDateTime());
    }

    /**
     * setter for timestamp of the last update to this appointment in UTC.
     *
     * @param lastUpdateDateTime The ZoneDateTime of the lastUpdate in UTC.
     */
    public void setLastUpdate(ZonedDateTime lastUpdateDateTime) {
        this.lastUpdate = lastUpdateDateTime;
    }

    /**
     * getter for the user who last updated this appointment.
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * setter for the name of who last updated this appointment.
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * getter for customerID associated with this appointment.
     *
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter for customerID of the customer associated with this appointment.
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * getter for userID associated with this appointment.
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * setter for userID of the user associated with this appointment.
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * getter for contactID associated with this appointment.
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setter for contactID of the contact associated with this appointment.
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}