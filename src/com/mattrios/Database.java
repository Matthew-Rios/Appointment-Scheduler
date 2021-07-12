package com.mattrios;

import com.mattrios.appointments.Appointment;
import com.mattrios.customers.*;
import com.mattrios.prepopulated_entities.Contact;
import com.mattrios.prepopulated_entities.Country;
import com.mattrios.prepopulated_entities.FirstLevelDivision;
import com.mattrios.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;

/**
 * @author Matt Rios
 */
public class Database {

    public static final String CONNECTION_STRING = "jdbc:mysql://wgudb.ucertify.com/WJ07jUr";
    public static final String CONNECTION_USER = "U07jUr";
    public static final String CONNECTION_PASSWORD = "53689048237";

    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_FIRST_LEVEL_DIVISIONS = "first_level_divisions";
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String COLUMN_USER_NAME = "User_Name";
    public static final String COLUMN_USER_ID = "User_ID";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_APPOINTMENT_ID = "Appointment_ID";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_START_TIME = "Start_Time";
    public static final String COLUMN_END_TIME = "End_Time";
    public static final String COLUMN_CREATE_DATE = "Create_Date";
    public static final String COLUMN_CREATED_BY = "Created_By";
    public static final String COLUMN_LAST_UPDATE = "Last_Update";
    public static final String COLUMN_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_CONTACT_ID = "Contact_ID";
    public static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    public static final String COLUMN_CONTACT_NAME = "Contact_Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_CUSTOMER_NAME = "Customer_Name";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_POSTAL_CODE = "Postal_Code";
    public static final String COLUMN_PHONE = "Phone";
    public static final String COLUMN_CONTACT_FREQUENCY = "Contact_Frequency";
    public static final String COLUMN_DIVISION_ID = "Division_ID";
    public static final String COLUMN_COUNTRY_ID = "Country_ID";
    public static final String COLUMN_COUNTRY = "Country";
    public static final String COLUMN_DIVISION = "Division";


    public static final String QUERY_USER_NAME_PREP = "SELECT * FROM " + TABLE_USERS + " WHERE " +
            COLUMN_USER_NAME + " = ?";
    public static final String QUERY_LOGIN_PREP = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_NAME +
            " = ? AND " + COLUMN_PASSWORD + " = ?";
    public static final String QUERY_USER_ID_PREP = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " ORDER BY " +
         COLUMN_USER_ID;
    public static final String INSERT_USER_PREP = "INSERT INTO " + TABLE_USERS + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String QUERY_APPOINTMENTS_PREP = "SELECT * FROM " + TABLE_APPOINTMENTS;
    public static final String QUERY_USER_APPOINTMENTS_PREP = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_USER_ID + " = ?";
    public static final String QUERY_CUSTOMER_ID_PREP = "SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMERS +
            " WHERE " + COLUMN_CUSTOMER_ID + " = ?";
    public static final String QUERY_APPOINTMENT_ID_PREP = "SELECT " + COLUMN_APPOINTMENT_ID + " FROM " + TABLE_APPOINTMENTS +
            " ORDER BY " + COLUMN_APPOINTMENT_ID;
    public static final String QUERY_CONTACTS_PREP = "SELECT * FROM " + TABLE_CONTACTS;
    public static final String INSERT_APPOINTMENT_PREP = "INSERT INTO " + TABLE_APPOINTMENTS +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_APPOINTMENT_PREP = "UPDATE " + TABLE_APPOINTMENTS + " SET " + COLUMN_TITLE +
            " = ?, " + COLUMN_DESCRIPTION + " = ?, " + COLUMN_LOCATION + " = ?, " + COLUMN_TYPE + " = ?, " +
            COLUMN_START_TIME + " = ?, " + COLUMN_END_TIME + " = ?, " + COLUMN_CREATE_DATE + " = ?, " +
            COLUMN_CREATED_BY + " = ?, " + COLUMN_LAST_UPDATE + " = ?, " + COLUMN_LAST_UPDATED_BY + " = ?, " +
            COLUMN_CUSTOMER_ID + " = ?, " + COLUMN_USER_ID + " = ?, " + COLUMN_CONTACT_ID + " = ? WHERE " +
            COLUMN_APPOINTMENT_ID + " = ?";
    public static final String DELETE_APPOINTMENT_PREP = "DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_APPOINTMENT_ID + " = ?";
    public static final String QUERY_CUSTOMER_APPOINTMENT_PREP = "SELECT " + COLUMN_START_TIME + ", " + COLUMN_END_TIME +
            ", " + COLUMN_APPOINTMENT_ID + " FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_CUSTOMER_ID + " = ?";
    public static final String QUERY_ALL_CUSTOMERS_PREP = "SELECT * FROM " + TABLE_CUSTOMERS + " INNER JOIN " +
            TABLE_FIRST_LEVEL_DIVISIONS + " ON " + TABLE_CUSTOMERS + "." + COLUMN_DIVISION_ID + " = " +
            TABLE_FIRST_LEVEL_DIVISIONS + "." + COLUMN_DIVISION_ID;
    public static final String QUERY_ALL_COUNTRIES_PREP = "SELECT * FROM " + TABLE_COUNTRIES;
    public static final String QUERY_FIRST_LEVEL_DIVISIONS_PREP = "SELECT * FROM " + TABLE_FIRST_LEVEL_DIVISIONS +
            " WHERE " + COLUMN_COUNTRY_ID + " = ?";
    public static final String INSERT_CUSTOMER_PREP = "INSERT INTO " + TABLE_CUSTOMERS +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String QUERY_ASSIGN_CUSTOMER_ID_PREP = "SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMERS +
            " ORDER BY " + COLUMN_CUSTOMER_ID;
    public static final String DELETE_CUSTOMER_PREP = "DELETE FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_ID +
            " = ?";
    public static final String QUERY_ALL_FIRST_LEVEL_DIVISIONS_PREP = "SELECT * FROM " + TABLE_FIRST_LEVEL_DIVISIONS;
    public static final String UPDATE_CUSTOMER_PREP = "UPDATE " + TABLE_CUSTOMERS + " SET " + COLUMN_CUSTOMER_NAME +
            " = ?, " + COLUMN_ADDRESS + " = ?, " + COLUMN_POSTAL_CODE + " = ?, " + COLUMN_PHONE + " = ?, " +
            COLUMN_CONTACT_FREQUENCY + " = ?, "+ COLUMN_CREATE_DATE + " = ?, " + COLUMN_CREATED_BY + " = ?, " +
            COLUMN_LAST_UPDATE + " = ?, " + COLUMN_LAST_UPDATED_BY + " = ?, " + COLUMN_DIVISION_ID + " = ? WHERE " +
            COLUMN_CUSTOMER_ID + " = ?";
    public static final String QUERY_DIVISION_PREP = "SELECT " + COLUMN_DIVISION + " FROM " +
            TABLE_FIRST_LEVEL_DIVISIONS + " WHERE " + COLUMN_DIVISION_ID + " = ?";
    public static final String QUERY_CUSTOMER_APPOINTMENTS_PREP = "SELECT " + COLUMN_START_TIME + ", " + COLUMN_TYPE +
            " FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_USER_ID + " = ?";
    public static final String QUERY_CONTACT_APPOINTMENTS_PREP = "SELECT " + COLUMN_APPOINTMENT_ID + ", " +
            COLUMN_TITLE + ", " + COLUMN_TYPE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_START_TIME + ", " +
            COLUMN_END_TIME + ", " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_APPOINTMENTS + " WHERE " +
            COLUMN_CONTACT_ID + " = ?";
    public static final String QUERY_DIVISION_CUSTOMERS_PREP = "SELECT " + COLUMN_CUSTOMER_ID + ", " +
            COLUMN_CUSTOMER_NAME + ", " + COLUMN_ADDRESS + ", " + COLUMN_POSTAL_CODE + ", " + COLUMN_PHONE + ", " +
            COLUMN_DIVISION_ID + " FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_DIVISION_ID + " = ?";


    /**
    * Connects to database and creates all database tables if they do not already exist.
    */
    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             Statement statement = conn.createStatement()){

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRIES +
                    " (Country_ID INT(10), Country VARCHAR(50), Create_Date DATETIME, Created_By VARCHAR(50), " +
                    "Last_Update TIMESTAMP, Last_Updated_By VARCHAR(50), PRIMARY KEY (Country_ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_FIRST_LEVEL_DIVISIONS +
                    " (Division_ID INT(10), Division VARCHAR(50), Create_Date DATETIME, Created_By VARCHAR(50), " +
                    "Last_Update TIMESTAMP, Last_Updated_By VARCHAR(50), Country_ID INT(10), PRIMARY KEY (Division_ID), " +
                    "FOREIGN KEY (Country_ID) REFERENCES " + TABLE_COUNTRIES + " (Country_ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMERS +
                    " (Customer_ID INT(10), Customer_Name VARCHAR(50), Address VARCHAR(100), Postal_Code VARCHAR(50), " +
                    "Phone VARCHAR(50), Contact_Frequency VARCHAR(50), Create_Date DATETIME, Created_By VARCHAR(50), " +
                    "Last_Update TIMESTAMP, Last_Updated_By VARCHAR(50), Division_ID INT(10), PRIMARY KEY (Customer_ID), " +
                    "FOREIGN KEY (Division_ID) REFERENCES " + TABLE_FIRST_LEVEL_DIVISIONS + " (Division_ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
                    " (User_ID INT(10), User_Name VARCHAR(50) UNIQUE, Password TEXT, Create_Date DATETIME, " +
                    "Created_By VARCHAR(50), Last_Update TIMESTAMP, Last_Updated_By VARCHAR(50), " +
                    "PRIMARY KEY (User_ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +
                    " (Contact_ID INT(10), Contact_Name VARCHAR(50), Email VARCHAR(50), PRIMARY KEY (Contact_ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENTS +
                    " (Appointment_ID INT(10), Title VARCHAR(50), Description VARCHAR(50), Location VARCHAR(50), " +
                    "Type VARCHAR(50), Start_Time DATETIME, End_Time DATETIME, Create_Date DATETIME, Created_By VARCHAR(50), " +
                    "Last_Update TIMESTAMP, Last_Updated_By VARCHAR(50), Customer_ID INT(10), User_ID INT(10), " +
                    "Contact_ID INT(10), PRIMARY KEY (Appointment_ID), FOREIGN KEY (Customer_ID) REFERENCES " +
                    TABLE_CUSTOMERS + " (Customer_ID), FOREIGN KEY (User_ID) REFERENCES " +
                    TABLE_USERS + " (User_ID), FOREIGN KEY (Contact_ID) REFERENCES " +
                    TABLE_CONTACTS + " (Contact_ID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches the users table to see if an existing user name matches the one passed to this method.
     *
     * @param userName the name to be checked against the users table to verify if the name is unique or not.
     * @return true if the user name already exists in the database or if there is an SQL error.
     * false if userName is unique
     */
    public static boolean userNameExists(String userName) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
            PreparedStatement preparedStatementUserName = conn.prepareStatement(QUERY_USER_NAME_PREP)) {

            preparedStatementUserName.setString(1, userName);

            ResultSet rs = preparedStatementUserName.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Inserts a new user into the users table. The method will go through an ordered list of user IDs to find the
     * first available ID and assign it to the new user. This userName is set for the Created_By and Last_Updated_By
     * columns because the user is creating their own account. Create_Date and Last_update are also set to the current
     * Timestamp as they are both happening simultaneously.
     *
     * @param userName the user name to be added to the database.
     * @param password the password associated with this user name.
     */
    public static void insertUser(String userName, String password) {
        int userID = 0;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementUserID = conn.prepareStatement(QUERY_USER_ID_PREP);
             PreparedStatement preparedStatementInsertUser = conn.prepareStatement(INSERT_USER_PREP)) {

            ResultSet rs = preparedStatementUserID.executeQuery();
            int i = 1;

            while (rs.next()) {
                if(rs.getInt(COLUMN_USER_ID) != i) {
                    userID = i;
                    break;
                }
                i++;
            }

            if (userID == 0) {
                userID = i;
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            preparedStatementInsertUser.setInt(1, userID);
            preparedStatementInsertUser.setString(2, userName);
            preparedStatementInsertUser.setString(3, password);
            preparedStatementInsertUser.setTimestamp(4, timestamp);
            preparedStatementInsertUser.setString(5, userName);
            preparedStatementInsertUser.setTimestamp(6, timestamp);
            preparedStatementInsertUser.setString(7, userName);

            preparedStatementInsertUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a userName and password from the calling method and queries the users table to determine if a userName
     * exists associated with that password.
     *
     * @param userName the user name to be queried.
     * @param password the password associated with this user name.
     * @return true if a username/password match is found. False if not or if an error is encountered.
     */
    public static boolean loginSuccessful (String userName, String password) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementLogin = conn.prepareStatement(QUERY_LOGIN_PREP)) {

            preparedStatementLogin.setString(1, userName);
            preparedStatementLogin.setString(2, password);

            ResultSet rs = preparedStatementLogin.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * A singleton of the user class is created for the user that logged into the system. The userID and userName are
     * set in the class to be able to reference it when needed.
     *
     * @param userName the userName of the user that logged into the system
     */
    public static void generateUserSingleton (String userName) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementUserName = conn.prepareStatement(QUERY_USER_NAME_PREP)) {

            preparedStatementUserName.setString(1, userName);

            ResultSet rs = preparedStatementUserName.executeQuery();
            rs.next();

            User user = User.getInstance();
            
            user.setUserID(rs.getInt(Database.COLUMN_USER_ID));
            user.setUserName(rs.getString(Database.COLUMN_USER_NAME));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * An observable list for all the appointments is created and the appointments table is queried. A new appointment
     * object is created for each entry and the values are then copied from the table to the object which is then added
     * to the observable list. The list is returned to the calling method to populate the appointments tableview.
     *
     * @return allAppointments an abserable list of all appointments in the table
     */
    public static ObservableList<Appointment> getAllUserAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementAppointments = conn.prepareStatement(QUERY_USER_APPOINTMENTS_PREP)) {

            preparedStatementAppointments.setInt(1, User.getInstance().getUserID());
            ResultSet rs = preparedStatementAppointments.executeQuery();

            while (rs.next()) {

                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt(Database.COLUMN_APPOINTMENT_ID));
                appointment.setTitle(rs.getString(Database.COLUMN_TITLE));
                appointment.setDescription(rs.getString(Database.COLUMN_DESCRIPTION));
                appointment.setLocation(rs.getString(Database.COLUMN_LOCATION));
                appointment.setType(rs.getString(Database.COLUMN_TYPE));
                appointment.setStart(rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setEnd(rs.getTimestamp(Database.COLUMN_END_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                appointment.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                appointment.setCustomerID(rs.getInt(Database.COLUMN_CUSTOMER_ID));
                appointment.setUserID(rs.getInt(Database.COLUMN_USER_ID));
                appointment.setContactID(rs.getInt(Database.COLUMN_CONTACT_ID));
                allAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementAppointments = conn.prepareStatement(QUERY_APPOINTMENTS_PREP)) {

            ResultSet rs = preparedStatementAppointments.executeQuery();

            while (rs.next()) {

                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt(Database.COLUMN_APPOINTMENT_ID));
                appointment.setTitle(rs.getString(Database.COLUMN_TITLE));
                appointment.setDescription(rs.getString(Database.COLUMN_DESCRIPTION));
                appointment.setLocation(rs.getString(Database.COLUMN_LOCATION));
                appointment.setType(rs.getString(Database.COLUMN_TYPE));
                appointment.setStart(rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setEnd(rs.getTimestamp(Database.COLUMN_END_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                appointment.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                appointment.setCustomerID(rs.getInt(Database.COLUMN_CUSTOMER_ID));
                appointment.setUserID(rs.getInt(Database.COLUMN_USER_ID));
                appointment.setContactID(rs.getInt(Database.COLUMN_CONTACT_ID));
                allAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * The customer table is queried for a result with a customer ID that matches the cutomerID parameter. If a result
     * is found then the ID exists in the table and true is returned.
     *
     * @param customerID the customerID to check against the table
     * @return true if a matching customerID is found in the customer table.
     * false if no matching ID is found or another error occurs.
     */
    public static boolean customerIDExists(int customerID) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementCustomerID = conn.prepareStatement(QUERY_CUSTOMER_ID_PREP)) {


            preparedStatementCustomerID.setInt(1, customerID);
            ResultSet rs = preparedStatementCustomerID.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * This method queries the appointment_ID column from the appointments table and sorts the IDs ascending.
     * It then iterates through the results to find the first integer ID that is not currently in use. If there are
     * none available it increments the highest current ID by one.
     *
     * @return appointmentID if no errors occur. -1 if an error is found
     */
    public static int getAppointmentID() {
        int appointmentID = 0;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementAppointmentID = conn.prepareStatement(QUERY_APPOINTMENT_ID_PREP)) {

            ResultSet rs = preparedStatementAppointmentID.executeQuery();
            int i = 1;

            while (rs.next()) {
                if (rs.getInt(COLUMN_APPOINTMENT_ID) != i) {
                    appointmentID = i;
                    break;
                }
                i++;
            }

            if (appointmentID == 0) {
                appointmentID = i;
            }

            return appointmentID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * An observable list of contacts is created and the method queries the contacts table. A new contact object is
     * created and the associated data is copied from the table into the object. The object is then added to the
     * contactList which is returned to the calling method.
     *
     * @return contactsList
     */
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementContacts = conn.prepareStatement(QUERY_CONTACTS_PREP)) {

            ResultSet rs = preparedStatementContacts.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();

                contact.setContactID(rs.getInt(COLUMN_CONTACT_ID));
                contact.setName(rs.getString(COLUMN_CONTACT_NAME));
                contact.setEmail(rs.getString(COLUMN_EMAIL));
                contactsList.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }

    /**
     * This method receive an appointment object from the calling method. The values in the object are then copied
     * into their appropriate columns in the appointments table.
     *
     * @param appointment the appointment to be inserted into the table
     */
    public static void insertAppointment (Appointment appointment) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementInsertAppointment = conn.prepareStatement(INSERT_APPOINTMENT_PREP)) {

            preparedStatementInsertAppointment.setInt(1, appointment.getAppointmentID());
            preparedStatementInsertAppointment.setString(2, appointment.getTitle());
            preparedStatementInsertAppointment.setString(3, appointment.getDescription());
            preparedStatementInsertAppointment.setString(4, appointment.getLocation());
            preparedStatementInsertAppointment.setString(5, appointment.getType());
            preparedStatementInsertAppointment.setTimestamp(6, appointment.getStartUTC());
            preparedStatementInsertAppointment.setTimestamp(7, appointment.getEndUTC());
            preparedStatementInsertAppointment.setTimestamp(8, appointment.getCreateDateUTC());
            preparedStatementInsertAppointment.setString(9, appointment.getCreatedBy());
            preparedStatementInsertAppointment.setTimestamp(10, appointment.getLastUpdateUTC());
            preparedStatementInsertAppointment.setString(11, appointment.getLastUpdatedBy());
            preparedStatementInsertAppointment.setInt(12, appointment.getCustomerID());
            preparedStatementInsertAppointment.setInt(13, appointment.getUserID());
            preparedStatementInsertAppointment.setInt(14, appointment.getContactID());

            preparedStatementInsertAppointment.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method receive an appointment object from the calling method. The entry in the table which matches the
     * appointment ID of the passed object is updated with the values in the updated object.
     *
     * @param appointment the updated appointment to be reflected in the table.
     */
    public static void updateAppointment (Appointment appointment) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementUpdateAppointment = conn.prepareStatement(UPDATE_APPOINTMENT_PREP)) {

            preparedStatementUpdateAppointment.setString(1, appointment.getTitle());
            preparedStatementUpdateAppointment.setString(2, appointment.getDescription());
            preparedStatementUpdateAppointment.setString(3, appointment.getLocation());
            preparedStatementUpdateAppointment.setString(4, appointment.getType());
            preparedStatementUpdateAppointment.setTimestamp(5, appointment.getStart());
            preparedStatementUpdateAppointment.setTimestamp(6, appointment.getEnd());
            preparedStatementUpdateAppointment.setTimestamp(7, appointment.getCreateDate());
            preparedStatementUpdateAppointment.setString(8, appointment.getCreatedBy());
            preparedStatementUpdateAppointment.setTimestamp(9, appointment.getLastUpdate());
            preparedStatementUpdateAppointment.setString(10, appointment.getLastUpdatedBy());
            preparedStatementUpdateAppointment.setInt(11, appointment.getCustomerID());
            preparedStatementUpdateAppointment.setInt(12, appointment.getUserID());
            preparedStatementUpdateAppointment.setInt(13, appointment.getContactID());
            preparedStatementUpdateAppointment.setInt(14, appointment.getAppointmentID());

            preparedStatementUpdateAppointment.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method receives an appointmentID from the calling method and inserts that value ito the
     * DELETE_APPOINTMENT_PREP constant and the statement is executed to remove the entry with the matching
     * ID from the table
     *
     * @param appointmentID The appointmentID of the entry to be removed from the table.
     */
    public static void deleteAppointment(int appointmentID) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementDeleteAppointment = conn.prepareStatement(DELETE_APPOINTMENT_PREP)) {

            preparedStatementDeleteAppointment.setInt(1, appointmentID);

            preparedStatementDeleteAppointment.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Queries the database for customer appointments matching the passed customerID parameter. This compares the
     * passed newStart and newEnd parameters to the start time and end time of each appointment already scheduled for
     * the customer. If overlap is found, the method returns true indicating the customer is already scheduled during
     * that time. False is returned if there is no overlap. The appointment ID of the entry in the database is compared
     * to the modifyAppointmentID passed to this method to determine if the two appointments being examined are the
     * same prior to comparing times. This makes it so an error is not generated when editing an appointment without
     * changing the time.
     *
     * @return true if there is a conflict in appointment times for the customer, false if there is no conflict.
     * @param newStart the start time of the new or edited appointment in UTC
     * @param newEnd the end time of the new or edited appointment in UTC
     * @param customerID the ID of the customer scheduled for the appointment
     * @param modifyAppointmentID the ID of the appointment that is being modified.
     */
    public static boolean queryCustomerAppointments (LocalDateTime newStart, LocalDateTime newEnd, int customerID,
                                                      int modifyAppointmentID) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryCustomerAppointment = conn.prepareStatement(QUERY_CUSTOMER_APPOINTMENT_PREP)) {

            preparedStatementQueryCustomerAppointment.setInt(1, customerID);

            ResultSet rs = preparedStatementQueryCustomerAppointment.executeQuery();

            while(rs.next()) {
                LocalDateTime oldStart = rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime();
                LocalDateTime oldEnd = rs.getTimestamp(Database.COLUMN_END_TIME).toLocalDateTime();
                if (rs.getInt(COLUMN_APPOINTMENT_ID) != modifyAppointmentID) {
                    if (newStart.compareTo(oldEnd) < 0 && newStart.compareTo(oldStart) >= 0) {
                        return true;
                    }
                    if (newEnd.compareTo(oldStart) > 0 && newEnd.compareTo(oldEnd) <= 0) {
                        return true;
                    }
                }
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static ArrayList<Appointment> queryCustomerAppointments (int customerID) {
        ArrayList<Appointment> customerAppointments = new ArrayList<>();
        Appointment appointment;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryCustomerAppointment = conn.prepareStatement(QUERY_CUSTOMER_APPOINTMENT_PREP)) {

            preparedStatementQueryCustomerAppointment.setInt(1, customerID);

            ResultSet rs = preparedStatementQueryCustomerAppointment.executeQuery();

            while(rs.next()) {
                appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt(COLUMN_APPOINTMENT_ID));
                appointment.setStart(rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setEnd(rs.getTimestamp(Database.COLUMN_END_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                customerAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerAppointments;
    }

    /**
     * Queries the customer table and returns an observable list of customer objects.
     *
     * @return allCustomers an observable list of customer objects
     */
    public static ObservableList<Customer> getAllCustomers () {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Customer customer;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryAllCustomers = conn.prepareStatement(QUERY_ALL_CUSTOMERS_PREP)) {

            ResultSet rs = preparedStatementQueryAllCustomers.executeQuery();

            while (rs.next()) {

                switch (rs.getString(Database.COLUMN_CONTACT_FREQUENCY)) {
                    case "Do Not Contact":
                        customer = new NoContactCustomer();
                        break;
                    case "Monthly Contact":
                        customer = new MonthlyContactCustomer();
                        break;
                    case "Weekly Contact":
                        customer = new WeeklyContactCustomer();
                        break;
                    default:
                        customer = new NoContactLimitCustomer();
                }

                customer.setCustomerID(rs.getInt(Database.COLUMN_CUSTOMER_ID));
                customer.setName(rs.getString(Database.COLUMN_CUSTOMER_NAME));
                customer.setAddress(rs.getString(Database.COLUMN_ADDRESS));
                customer.setPostalCode(rs.getString(Database.COLUMN_POSTAL_CODE));
                customer.setPhone(rs.getString(Database.COLUMN_PHONE));
                customer.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                customer.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                customer.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                customer.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                customer.setDivisionID(rs.getInt(Database.COLUMN_DIVISION_ID));
                customer.setDivision(rs.getString(Database.COLUMN_DIVISION));
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Queries the country table and returns an observable list of country objects.
     *
     * @return allCountries an observable list of country objects
     */
    public static ObservableList<Country> getAllCountries () {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryAllCountries = conn.prepareStatement(QUERY_ALL_COUNTRIES_PREP)) {

            ResultSet rs = preparedStatementQueryAllCountries.executeQuery();

            while (rs.next()) {

                Country country = new Country();
                country.setCountryID(rs.getInt(Database.COLUMN_COUNTRY_ID));
                country.setCountry(rs.getString(Database.COLUMN_COUNTRY));
                country.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                country.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                country.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                country.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                allCountries.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }

    /**
     * Receives a country ID from the calling method. Queries the first_level_divisions table where the country ID =
     * the one passed to this method. Returns an observable list of those divisions.
     *
     * @param countryID the countryID associated with the desired first level divisions
     * @return relatedFirstLevelDivisions an observable list of first level division objects with a matching country ID
     */
    public static ObservableList<FirstLevelDivision> getRelatedFirstLevelDivisions(int countryID) {
        ObservableList<FirstLevelDivision> relatedFirstLevelDivisions = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryFirstLevelDivisions = 
                     conn.prepareStatement(QUERY_FIRST_LEVEL_DIVISIONS_PREP)) {

            preparedStatementQueryFirstLevelDivisions.setInt(1, countryID);

            ResultSet rs = preparedStatementQueryFirstLevelDivisions.executeQuery();

            while (rs.next()) {

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivisionID(rs.getInt(Database.COLUMN_DIVISION_ID));
                firstLevelDivision.setDivision(rs.getString(Database.COLUMN_DIVISION));
                firstLevelDivision.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                firstLevelDivision.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                firstLevelDivision.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                firstLevelDivision.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                firstLevelDivision.setCountryID(rs.getInt(Database.COLUMN_COUNTRY_ID));
                relatedFirstLevelDivisions.add(firstLevelDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatedFirstLevelDivisions;
    }

    /**
     * Queries the first_level_division table for all divisions. FirstLevelDivision objects are created for each entry
     * and all object are returned in an observable list.
     *
     * @return allDivisions an observable list of all divisions in the database
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryAllDivisions = conn.prepareStatement(QUERY_ALL_FIRST_LEVEL_DIVISIONS_PREP)) {

            ResultSet rs = preparedStatementQueryAllDivisions.executeQuery();

            while (rs.next()) {

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivisionID(rs.getInt(Database.COLUMN_DIVISION_ID));
                firstLevelDivision.setDivision(rs.getString(Database.COLUMN_DIVISION));
                firstLevelDivision.setCreateDate(rs.getTimestamp(Database.COLUMN_CREATE_DATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                firstLevelDivision.setCreatedBy(rs.getString(Database.COLUMN_CREATED_BY));
                firstLevelDivision.setLastUpdate(rs.getTimestamp(Database.COLUMN_LAST_UPDATE).toLocalDateTime().atZone(ZoneId.of("UTC")));
                firstLevelDivision.setLastUpdatedBy(rs.getString(Database.COLUMN_LAST_UPDATED_BY));
                firstLevelDivision.setCountryID(rs.getInt(Database.COLUMN_COUNTRY_ID));
                allDivisions.add(firstLevelDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDivisions;
    }

    /**
     * Queries the database for all customer IDs. Returns the first unassigned ID or the max id + 1 if no unassigned
     * customer IDs are found. Returns -1 in the event of an error.
     *
     * @return customerID the ID to be assigned to the new customer
     */
    public static int getCustomerID() {
        int customerID = 0;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementCustomerID = conn.prepareStatement(QUERY_ASSIGN_CUSTOMER_ID_PREP)) {

            ResultSet rs = preparedStatementCustomerID.executeQuery();
            int i = 1;

            while (rs.next()) {
                if (rs.getInt(COLUMN_CUSTOMER_ID) != i) {
                    customerID = i;
                    break;
                }
                i++;
            }

            if (customerID == 0) {
                customerID = i;
            }

            return customerID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Inserts the customer passed to this method into the database.
     *
     * @param customer the customer to be added to the customer table
     */
    public static void insertCustomer(Customer customer) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementInsertCustomer = conn.prepareStatement(INSERT_CUSTOMER_PREP)) {

            preparedStatementInsertCustomer.setInt(1, customer.getCustomerID());
            preparedStatementInsertCustomer.setString(2, customer.getName());
            preparedStatementInsertCustomer.setString(3, customer.getAddress());
            preparedStatementInsertCustomer.setString(4, customer.getPostalCode());
            preparedStatementInsertCustomer.setString(5, customer.getPhone());
            preparedStatementInsertCustomer.setString(6, customer.getContactFrequency());
            preparedStatementInsertCustomer.setTimestamp(7, customer.getCreateDateUTC());
            preparedStatementInsertCustomer.setString(8, customer.getCreatedBy());
            preparedStatementInsertCustomer.setTimestamp(9, customer.getLastUpdateUTC());
            preparedStatementInsertCustomer.setString(10, customer.getLastUpdatedBy());
            preparedStatementInsertCustomer.setInt(11, customer.getDivisionID());

            preparedStatementInsertCustomer.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the customer associated with the customer ID passed to this method from the customers table.
     *
     * @param customerID the customerID associated with the customer to be deleted
     */
    public static void deleteCustomer(int customerID) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementDeleteCustomer = conn.prepareStatement(DELETE_CUSTOMER_PREP)) {

            preparedStatementDeleteCustomer.setInt(1, customerID);

            preparedStatementDeleteCustomer.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing customer in the database based on the customer passed to this method.
     *
     * @param customer the updated customer to be reflected in the database
     */
    public static void updateCustomer (Customer customer) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementUpdateCustomer = conn.prepareStatement(UPDATE_CUSTOMER_PREP)) {

            preparedStatementUpdateCustomer.setString(1, customer.getName());
            preparedStatementUpdateCustomer.setString(2, customer.getAddress());
            preparedStatementUpdateCustomer.setString(3, customer.getPostalCode());
            preparedStatementUpdateCustomer.setString(4, customer.getPhone());
            preparedStatementUpdateCustomer.setString(5, customer.getContactFrequency());
            preparedStatementUpdateCustomer.setTimestamp(6, customer.getCreateDate());
            preparedStatementUpdateCustomer.setString(7, customer.getCreatedBy());
            preparedStatementUpdateCustomer.setTimestamp(8, customer.getLastUpdate());
            preparedStatementUpdateCustomer.setString(9, customer.getLastUpdatedBy());
            preparedStatementUpdateCustomer.setInt(10, customer.getDivisionID());
            preparedStatementUpdateCustomer.setInt(11, customer.getCustomerID());

            preparedStatementUpdateCustomer.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the division name of the division associated with the division ID passed to this method.
     *
     * @param divisionID the id of the division to get the name of
     * @return division the name of the division associated with the division ID
     */
    public static String getDivision (int divisionID) {
        String division = "";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryDivision = conn.prepareStatement(QUERY_DIVISION_PREP)) {

            preparedStatementQueryDivision.setInt(1, divisionID);

            ResultSet rs = preparedStatementQueryDivision.executeQuery();

            rs.next();

            division = rs.getString(COLUMN_DIVISION);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return division;
    }

    /**
     * Queries the appointments table for the logged in userID. Converts the start time to a localized month. Compares
     * the localized month and the type of each result returned in the query to the parameters passed to this method.
     * if both match, total is incremented by 1.
     *
     * @return total the total number of appointments that match the provided month and type
     */
    public static String getMonthTypeAppointmentTotal(Month monthToMatch, String type) {
        int total = 0;

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementQueryAppointments = conn.prepareStatement(QUERY_CUSTOMER_APPOINTMENTS_PREP)) {

            preparedStatementQueryAppointments.setInt(1, User.getInstance().getUserID());
            ResultSet rs = preparedStatementQueryAppointments.executeQuery();

            while(rs.next()) {
                ZonedDateTime startZoneDate = rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime().atZone(ZoneId.systemDefault());
                Month month = startZoneDate.toLocalDate().getMonth();

                if (month.equals(monthToMatch) && rs.getString(Database.COLUMN_TYPE).equals(type)) {
                    total++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(total);
    }

    /**
     * Queries all the appointments for the contact ID that is passed to this method.
     *
     * @return contactAppointments an observable list of all appointments associated with the contact ID
     */
    public static ObservableList<Appointment> getContactAppointments(int contactID) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementContactAppointments = conn.prepareStatement(QUERY_CONTACT_APPOINTMENTS_PREP)) {

            preparedStatementContactAppointments.setInt(1, contactID);
            ResultSet rs = preparedStatementContactAppointments.executeQuery();

            while (rs.next()) {

                Appointment appointment = new Appointment();
                appointment.setAppointmentID(rs.getInt(Database.COLUMN_APPOINTMENT_ID));
                appointment.setTitle(rs.getString(Database.COLUMN_TITLE));
                appointment.setDescription(rs.getString(Database.COLUMN_DESCRIPTION));
                appointment.setType(rs.getString(Database.COLUMN_TYPE));
                appointment.setStart(rs.getTimestamp(Database.COLUMN_START_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setEnd(rs.getTimestamp(Database.COLUMN_END_TIME).toLocalDateTime().atZone(ZoneId.of("UTC")));
                appointment.setCustomerID(rs.getInt(Database.COLUMN_CUSTOMER_ID));
                contactAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactAppointments;
    }

    /**
     * Queries all the customers for the divion ID that is passed to this method.
     *
     * @return divisionCustomers an observable list of all customers belonging to the division associated with the
     * division ID
     */
    public static ObservableList<Customer> getDivisionCustomers(int divisionID) {
        ObservableList<Customer> divisionCustomers = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASSWORD);
             PreparedStatement preparedStatementDivisionCustomers = conn.prepareStatement(QUERY_DIVISION_CUSTOMERS_PREP)) {

            preparedStatementDivisionCustomers.setInt(1, divisionID);
            ResultSet rs = preparedStatementDivisionCustomers.executeQuery();

            while (rs.next()) {

                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt(Database.COLUMN_CUSTOMER_ID));
                customer.setName(rs.getString(Database.COLUMN_CUSTOMER_NAME));
                customer.setAddress(rs.getString(Database.COLUMN_ADDRESS));
                customer.setPostalCode(rs.getString(Database.COLUMN_POSTAL_CODE));
                customer.setPhone(rs.getString(Database.COLUMN_PHONE));
                customer.setDivisionID(rs.getInt(Database.COLUMN_DIVISION_ID));
                divisionCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionCustomers;
    }
}
