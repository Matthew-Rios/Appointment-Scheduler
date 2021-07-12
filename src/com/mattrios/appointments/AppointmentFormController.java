package com.mattrios.appointments;

import com.mattrios.*;
import com.mattrios.customers.Customer;
import com.mattrios.customers.CustomerDAO;
import com.mattrios.prepopulated_entities.Contact;
import com.mattrios.users.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.time.*;

/**
 * @author Matt Rios
 */
public class AppointmentFormController {

    @FXML
    private Label addModifyAppointmentsLabel;
    @FXML
    private TextField appointmentIDField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private TextField typeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startHourField;
    @FXML
    private TextField startMinuteField;
    @FXML
    private ComboBox<String> startComboBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endHourField;
    @FXML
    private TextField endMinuteField;
    @FXML
    private ComboBox<String> endComboBox;
    @FXML
    private TextField customerIDField;

    private ObservableList<Contact> contacts = Database.getContacts();
    private int modifyAppointmentID;
    private String modifyCreatedBy;
    private LocalDateTime modifyCreateDate;
    private boolean modifyAppointment = false;
    private LocalTime officeOpenUTC = LocalTime.of(12, 00);
    private LocalTime officeCloseUTC = LocalTime.of(2, 00);

    /**
     * Initializes the contacts combo box with the names of the contacts in the contacts table. Also initializes the
     * AM/PM combo boxes.
     */
    public void initialize() {
        for(Contact contact : contacts) {
            contactComboBox.getItems().add(contact.getName());
        }

        startComboBox.getItems().addAll("AM", "PM");
        endComboBox.getItems().addAll("AM", "PM");
    }

    /**
     * Moves all the values from their respective fields into variables. Checks these fields for valid values.
     * Converts the separate date/time elements into a LocalDateTime for the start and end times.
     *
     * If all values are valid a new appointment object is created. If the modify appointment flag is set to true the
     * method calls the updateAppointment  method and  if false, the editAppointment methods from the PreparedStatements
     * and Schedule classes which will either create a new appointment or update an existing one. Also, the appointment
     * is inserted into the appointments table via a call to PreparedStatement.insertAppointment. Finally, the stage is
     * closed to bring the appointments tableview window back into focus.
     */
    public void saveButtonClicked() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        String startHour = startHourField.getText();
        String startMinute = startMinuteField.getText();
        String startAMPM = startComboBox.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String endHour = endHourField.getText();
        String endMinute = endMinuteField.getText();
        String endAMPM = endComboBox.getValue();
        String customerID = customerIDField.getText();

        int contactID = 0;
        int startHourInt;
        int startMinuteInt;
        int endHourInt;
        int endMinuteInt;
        int customerIDInt;
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        Customer customer = null;

        for (Contact contact : contacts) {
            if (contact.getName().equals(contactComboBox.getSelectionModel().getSelectedItem())) {
                contactID = contact.getContactID();
            }
        }


        if (title.length() > 50 || title.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a title of appropriate length (1-50 characters). " +
                    "Current length = " + title.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (description.length() > 50 || description.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a description of appropriate length (1-50 characters). " +
                    "Current length = " + description.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (location.length() > 50 || location.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a location of appropriate length (1-50 characters). " +
                    "Current length = " + location.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (contactComboBox.getSelectionModel().isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please select an appropriate option for contact.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (type.length() > 50 || type.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a type of appropriate length (1-50 characters). " +
                    "Current length = " + type.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }
        
        try {
            startHourInt = Integer.parseInt(startHour);
        } catch (NumberFormatException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid hour (1-12) for the start time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }
        
        if (startHourInt < 1 || startHourInt > 12) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid hour (1-12) for the start time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            startMinuteInt = Integer.parseInt(startMinute);
        } catch (NumberFormatException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid minute (1-59) for the start time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (startMinuteInt < 0 || startMinuteInt > 59) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid minute (0-59) for the start time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            endHourInt = Integer.parseInt(endHour);
        } catch (NumberFormatException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid hour (1-12) for the end time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (endHourInt < 1 || endHourInt > 12) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid hour (1-12) for the end time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            endMinuteInt = Integer.parseInt(endMinute);
        } catch (NumberFormatException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid minute (1-59) for the end time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (endMinuteInt < 0 || endMinuteInt > 59) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid minute (0-59) for the end time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (startComboBox.getSelectionModel().isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose AM or PM for start time");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (endComboBox.getSelectionModel().isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose AM or PM for end time");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (startAMPM.equals("PM")) {
            if (startHourInt != 12) {
                startHourInt += 12;
            }
        }

        if (endAMPM.equals("PM")) {
            if (endHourInt != 12) {
                endHourInt +=12;
            }
        }

        if (startDatePicker.getValue() == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please pick a start date.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (endDatePicker.getValue() == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please pick an end date.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        startDateTime = LocalDateTime.of(startDate, LocalTime.of(startHourInt, startMinuteInt));
        endDateTime = LocalDateTime.of(endDate, LocalTime.of(endHourInt, endMinuteInt));

        if (startDateTime.compareTo(endDateTime) > 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "End date/time can not be before start date/time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        ZonedDateTime startUTC = convertToUTC(startDateTime);
        ZonedDateTime endUTC = convertToUTC(endDateTime);

        if (startUTC.toLocalTime().compareTo(officeOpenUTC) < 0 && startUTC.toLocalTime().compareTo(officeCloseUTC) > 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Start time is outside normal office hours.\n" +
                    "Please schedule all appointments between 8:00 a.m to 10:00pm EST");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (endUTC.toLocalTime().compareTo(officeOpenUTC) < 0 && endUTC.toLocalTime().compareTo(officeCloseUTC) > 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "End time is outside normal office hours.\n" +
                    "Please schedule all appointments between 8:00 a.m to 10:00pm EST");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            customerIDInt = Integer.parseInt(customerID);
        } catch (NumberFormatException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid integer value for the Customer ID field.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (!Database.customerIDExists(customerIDInt)) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Customer does not exist in the system. Please choose a valid" +
                    " customer ID.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }


        if (Database.queryCustomerAppointments(startUTC.toLocalDateTime(), endUTC.toLocalDateTime(),
                customerIDInt, modifyAppointmentID)) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Customer already scheduled for an appointment during this time." +
                    "\nPlease choose another appointment time.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        for (Customer searchCustomer : CustomerDAO.getAllCustomers()) {
            if (customerIDInt == searchCustomer.getCustomerID()) {
                customer = searchCustomer;
                break;
            } else {
                customer = new Customer();
            }
        }

        if (customer.contactFrequencyViolation(startUTC, endUTC)) {
            if (customer.getContactFrequency().equals("Do Not Contact")) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR, "Customer has opted to not be contacted. Please update their" +
                        " contact frequency preferences prior to scheduling an appointment.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                return;
            }

            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Customer contact frequency is set to: " +
                    customer.getContactFrequency() + ". They are already scheduled for an appointment within this time " +
                    "frame.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        Appointment appointment = new Appointment();
        User user = User.getInstance();

        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setType(type);
        appointment.setStart(startUTC);
        appointment.setEnd(endUTC);
        appointment.setLastUpdate(convertToUTC(LocalDateTime.now()));
        appointment.setLastUpdatedBy(user.getUserName());
        appointment.setCustomerID(customerIDInt);
        appointment.setUserID(user.getUserID());
        appointment.setContactID(contactID);

        if (modifyAppointment) {
            appointment.setAppointmentID(modifyAppointmentID);
            appointment.setCreatedBy(modifyCreatedBy);
            appointment.setCreateDate(convertToUTC(modifyCreateDate));
            Database.updateAppointment(appointment);
            AppointmentDAO.editAppointment(modifyAppointmentID, appointment);

        } else {
            appointment.setAppointmentID(Database.getAppointmentID());
            appointment.setCreateDate(convertToUTC(LocalDateTime.now()));
            appointment.setCreatedBy(user.getUserName());
            Database.insertAppointment(appointment);
            AppointmentDAO.addAppointment(appointment);
        }

        closeStage();
    }

    /**
     * Gets the appointment ID from the appointment passed to this method. Sets the modify appointment flag to true.
     * Converts the Timestamp values for the start and end times to LocalTime. Uses all these values and the values
     * in the appointment object to set the fields in the form to reflect the stored values. Also updates the header
     * label to reflect that the appointment is being modified.
     *
     * @param appointment the appointment that is being modified
     */
    public void setEditAppointmentValues(Appointment appointment) {
        modifyAppointmentID = appointment.getAppointmentID();
        modifyCreatedBy = appointment.getCreatedBy();
        modifyCreateDate = appointment.getCreateDate().toLocalDateTime();
        modifyAppointment = true;
        String contactName = "";
        LocalTime startTime = appointment.getStart().toLocalDateTime().toLocalTime();
        LocalTime endTime = appointment.getEnd().toLocalDateTime().toLocalTime();
        int startHour = startTime.getHour();
        int endHour = startTime.getHour();

        if (startHour > 12) {
            startComboBox.getSelectionModel().select("PM");
            startHour -= 12;
        } else {
            startComboBox.getSelectionModel().select("AM");
        }

        if (endHour > 12) {
            endComboBox.getSelectionModel().select("PM");
            endHour -= 12;
        } else {
            endComboBox.getSelectionModel().select("AM");
        }

        for(Contact contact : contacts) {
            if (contact.getContactID() == appointment.getContactID()) {
                contactName = contact.getName();
            }
        }

        addModifyAppointmentsLabel.setText("Modify Appointment");
        appointmentIDField.setText(String.valueOf(appointment.getAppointmentID()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        contactComboBox.getSelectionModel().select(contactName);
        typeField.setText(appointment.getType());
        startDatePicker.setValue(appointment.getStart().toLocalDateTime().toLocalDate());
        startHourField.setText(String.valueOf(startHour));
        startMinuteField.setText(String.valueOf(startTime.getMinute()));
        endDatePicker.setValue(appointment.getEnd().toLocalDateTime().toLocalDate());
        endHourField.setText(String.valueOf(endHour));
        endMinuteField.setText(String.valueOf(endTime.getMinute()));
        customerIDField.setText(String.valueOf(appointment.getCustomerID()));
    }

    /**
     * Code needed to close the stage.
     */
    private void closeStage() {
        Stage stage = (Stage) appointmentIDField.getScene().getWindow();
        stage.close();
    }

    private ZonedDateTime convertToUTC (LocalDateTime time) {
        ZonedDateTime timeZoned = time.atZone(ZoneId.systemDefault());
        return timeZoned.withZoneSameInstant(ZoneId.of("UTC"));
    }

    @FXML
    public void cancelButtonClicked() {
        closeStage();
    }

}