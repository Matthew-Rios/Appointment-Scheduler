package com.mattrios.customers;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Matt Rios
 */
public class Customer {

    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String division;

    /**
     * Empty constructor for the Customer object.
     */
    public Customer() {
    }

    /**
     * Getter for the customer ID.
     *
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for the customer ID.
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the customer name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the address.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for the customer address.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the postal code.
     *
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for the postal code.
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for the phone number.
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for the phone number.
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for the localized create date/time.
     *
     * @return create date/time in local time
     */
    public Timestamp getCreateDate() {
        ZonedDateTime localZonedCreateDate = createDate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedCreateDate.toLocalDateTime());
    }

    /**
     * Getter for the created date/time in UTC.
     *
     * @return create date/time in UTC
     */
    public Timestamp getCreateDateUTC() {
        return Timestamp.valueOf(createDate.toLocalDateTime());
    }

    /**
     * Setter for the create date/time of the object in UTC.
     *
     * @param createDate the ZoneDateTime of when the object was created in UTC
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for the user who created this object.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for the user name which created this object.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for the localized time/date of the last updated.
     *
     * @return time/date of the last update in local time
     */
    public Timestamp getLastUpdate() {
        ZonedDateTime localZonedLastUpdate = lastUpdate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedLastUpdate.toLocalDateTime());
    }

    /**
     * Getter for the time/date of the last updated in UTC.
     *
     * @return time/date of the last updated in UTC
     */
    public Timestamp getLastUpdateUTC() {
        return Timestamp.valueOf(lastUpdate.toLocalDateTime());
    }

    /**
     * Setter for the date/time this object was last updated in UTC.
     *
     * @param lastUpdate the ZoneDateTime this object was last updated in UTC.
     */
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for the user name who last updated this object
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for the user name that last updated this object.
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for the divisionID
     *
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for the division ID.
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter for the division name.
     *
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for the division name.
     *
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    public String getContactFrequency() {
        return "Customer Class";
    }

    public boolean contactFrequencyViolation(ZonedDateTime start, ZonedDateTime end) {
        return true;
    }
}
