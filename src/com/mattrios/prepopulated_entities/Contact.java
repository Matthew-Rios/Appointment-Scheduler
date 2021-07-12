package com.mattrios.prepopulated_entities;

/**
 * @author Matt Rios
 */
public class Contact {
    private int contactID;
    private String name;
    private String email;

    /**
     * Empty constructor for the contact object.
     */
    public Contact() {
    }

    /**
     * getter for the contactID.
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setter for the contactID
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * getter for the contact name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the contact name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for the contact email. Not currently used by left the getter method for use in the future.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for the contact email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}