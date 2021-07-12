package com.mattrios.users;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class User {

    private static User instance;
    private int userID;
    private String userName;
    private String password;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Empty constructor for the class
     */
    private User() {
    }

    /**
     * Creates a new singleton class if an instance does not already exist and return the instance.
     *
     * @return instance
     */
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    /**
     * Getter for the user ID.
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for the user ID.
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for the user name.
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the user name.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the user password. Not currently in use but may be used in the future.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user password. Not currently in use but may be used in the future.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the localized timestamp identifying when this account was created. Not currently in use but may be
     * used in the future.
     *
     * @return Localized timestamp identifying when this account was created
     */
    public Timestamp getCreateDate() {
        ZonedDateTime localZonedCreateDate = createDate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedCreateDate.toLocalDateTime());
    }

    /**
     * Setter for the timestamp of when the account was created in UTC. Not currently in use but may be used in the future.
     *
     * @param createDate
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for the name of the user who created this user. Not currently in use but may be used in the future.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for the name of the user who created this account
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for the localized timestamp identifying when this account was last updated.
     *
     * @return localized timestamp identifying when this account was last updated
     */
    public Timestamp getLastUpdate() {
        ZonedDateTime localZonedLastUpdate = lastUpdate.withZoneSameInstant(ZoneId.systemDefault());
        return Timestamp.valueOf(localZonedLastUpdate.toLocalDateTime());
    }

    /**
     * Setter for the timestamp of when this user was last updated
     *
     * @param lastUpdate
     */
    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for the name of the user who last updated this account.
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for the name of the user that last updated this account
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
