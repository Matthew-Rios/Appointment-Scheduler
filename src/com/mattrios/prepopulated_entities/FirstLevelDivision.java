package com.mattrios.prepopulated_entities;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Matt Rios
 */
public class FirstLevelDivision {

    private int divisionID;
    private String division;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    /**
     * Empty constructor.
     */
    public FirstLevelDivision() {
    }

    /**
     * Getter for the division ID.
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

    /**
     * Getter for the localized time/date that this object was created. This method is not yet in use but is being kept
     * for future use.
     *
     * @return Timestamp of the localized time/date that this object was created
     */
    public Timestamp getCreateDate() {
        ZonedDateTime localZonedCreateDate = createDate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedCreateDate.toLocalDateTime());
    }

    /**
     * Setter for the time/date that this object was created in UTC.
     *
     * @param createDate ZoneDateTime of when the object was created in UTC.
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for the user name of who created this object. This method is not yet in use but is being kept
     * for future use.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for the user name that created this object.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for the localized time/date that this object was last updated. This method is not yet in use but is being
     * kept for future use.
     *
     * @return Timestamp of the localized time/date that this object was last updated
     */
    public Timestamp getLastUpdate() {
        ZonedDateTime localZonedLastUpdate = lastUpdate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedLastUpdate.toLocalDateTime());
    }

    /**
     * Setter for the time/date that this object was last updated in UTC.
     *
     * @param lastUpdate ZoneDateTime of when the object was last updated in UTC.
     */
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for the user name that last updated this object. This method is not yet in use but is being kept
     * for future use.
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
     * Getter for the Country ID that is associated with this division
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for the country ID associated with this division.
     *
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
