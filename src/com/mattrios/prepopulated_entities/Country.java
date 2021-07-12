package com.mattrios.prepopulated_entities;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Matt Rios
 */
public class Country {

    private int countryID;
    private String country;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Empty constructor for the Country object.
     */
    public Country() {
    }

    /**
     * Getter for the country ID.
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Getter for the country name.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for the country name.
     *
     * @param country the name of the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for the localized create date/time. Not currently used by left the getter method for use in the future.
     *
     * @return the localized create time/date
     */
    public Timestamp getCreateDate() {
        ZonedDateTime localZonedDateTime = createDate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedDateTime.toLocalDateTime());
    }

    /**
     * Setter for the create date/time.
     *
     * @param createDate the ZoneDateTime this record was created in UTC
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for the name of the person who created this record
     *
     * @return getCreatedBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for the user ID that created this record.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for the localized last update date/time. Not currently used by left the getter method for use in the future.
     *
     * @return the localized last update time/date
     */
    public Timestamp getLastUpdate() {
        ZonedDateTime localZonedDateTime = lastUpdate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedDateTime.toLocalDateTime());
    }

    /**
     * Setter for the date/time this record was last updated in UTC.
     *
     * @param lastUpdate the ZoneDateTime of the last updated in UTC
     */
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for the name of the person who last updated this record.
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for the user name that last updated this record.
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
