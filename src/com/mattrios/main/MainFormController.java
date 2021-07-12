package com.mattrios.main;

import com.mattrios.*;
import com.mattrios.appointments.Appointment;
import com.mattrios.appointments.AppointmentDAO;
import com.mattrios.appointments.AppointmentFormController;
import com.mattrios.customers.Customer;
import com.mattrios.customers.CustomerDAO;
import com.mattrios.customers.CustomerFormController;
import com.mattrios.prepopulated_entities.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * @author Matt Rios
 */
public class MainFormController {

    @FXML
    private VBox mainFormVBox;
    @FXML
    private TextField appointmentSearchBar;
    @FXML
    private TextField customerSearchBar;
    @FXML
    private TableView<Appointment> appointmentsTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentStartColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIDColumn;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer, Integer> customerDivisionIDColumn;
    @FXML
    private TableColumn<FirstLevelDivision, String> customerDivisionColumn;



    /**
     * Creates a filtered list of all the appointments returned by the getAllAppointments method in the schedule class.
     * This list is scanned for any appointments within 15 minutes of user login. If any are found an alert is generated
     * with appointment information and the immediateAppointment is set to true. If an immediate appointment is not
     * found an alert is generated letting the user know there are no immediate appointments. The appointments and
     * customers table views are populated. All appointments are visible by default.
     */
    public void initialize() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(AppointmentDAO.getAllUserAppointments());

        boolean immediateAppointment = false;

        for(Appointment appointment : allAppointments) {
            LocalTime appointmentTime = appointment.getStart().toLocalDateTime().toLocalTime();
            if (appointment.getStart().toLocalDateTime().toLocalDate().compareTo(LocalDate.now()) == 0
            && LocalTime.now().until(appointmentTime, ChronoUnit.MINUTES) <= 15
            && LocalTime.now().until(appointmentTime, ChronoUnit.MINUTES) >= 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment starting in the next " +
                        "15 minutes\nAppointment ID: " + appointment.getAppointmentID() + "\nTime: " +
                        appointment.getStart() + " - " + appointment.getEnd());
                immediateAppointment = true;
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        }

        if(!immediateAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no immediate appointments.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentsTableView.setItems(AppointmentDAO.getAllUserAppointments());
        appointmentsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerTableView.setItems(CustomerDAO.getAllCustomers());
        customerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    /**
     * opens the appointmentForm dialog box or prints an error if unable.
     */
    @FXML
    public void addAppointmentButtonClicked() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("appointments/appointmentForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
                System.out.println("Couldn't load the dialog");
        }
        dialog.showAndWait();
        appointmentsTableView.refresh();
    }

    /**
     * Checks to see if the user has an appointment selected to edit. If not an error window is generated. If an
     * appointment is selected the dialog is readied and the setEditAppointmentValues class is called in the
     * AppointmentFormController to set the value of the fields to reflect the selected appointment. Once the method
     * completes the appointmentForm is generated.
     */
    @FXML
    public void editAppointmentButtonClicked() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected to edit.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("appointments/appointmentForm.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
        }
        AppointmentFormController controller = fxmlLoader.getController();
        controller.setEditAppointmentValues(selectedAppointment);

        dialog.showAndWait();
        appointmentsTableView.refresh();
    }

    /**
     * Checks to see if the user has an appointment selected to delete. If not an error window is generated. If an
     * appointment is selected a confirmation alert is generated to notify the user the change is permanent. If the
     * confirmation dialog is okayed the Schedule.removeAppointment method is called to remove the appointment from
     * the observable list and the PreparedStatements.deleteAppointment method is called to delete it from the table.
     * An alert showing the appointment ID and Type of the cancelled appointment is generated upon success.
     */
    @FXML
    public void deleteAppointmentButtonClicked() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected to delete.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting " + selectedAppointment.getTitle()
                + " will remove it permanently\nClick okay to confirm deletion or cancel to cancel this action.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            AppointmentDAO.removeAppointment(selectedAppointment);
            Database.deleteAppointment(selectedAppointment.getAppointmentID());
        }

        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " +
                selectedAppointment.getAppointmentID() + ", Type: " + selectedAppointment.getType() + " successfully " +
                "cancelled!");
        informationAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        informationAlert.showAndWait();
        appointmentsTableView.refresh();
    }

    /**
     * Refreshes the appointments table view in the event of a system time zone change.
     */
    @FXML
    public void refreshButtonClicked() {
        appointmentsTableView.refresh();
    }

    /**
     * Opens the add customer form dialog box or generates an error if unsuccessful.
     */
    @FXML
    public void addCustomerButtonClicked() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("customers/customerForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
        }
        dialog.showAndWait();
        customerTableView.refresh();
    }

    /**
     * Opens the add customer form dialog box and passes the selected customer object to the form controller. Generates
     * an alert if no customer is selected.
     */
    @FXML
    public void editCustomerButtonClicked() {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected to edit.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("customers/customerForm.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
        }
        CustomerFormController controller = fxmlLoader.getController();
        controller.setEditCustomerValues(selectedCustomer);

        dialog.showAndWait();
        customerTableView.refresh();
    }

    /**
     * Gets the selected customer or generates an alert if none selected. An error alert will also be generated if the
     * selected customer has appointments still scheduled. Creates a confirmation alert asking the
     * user to confirm deletion. An alert stating the deletion was successful is generated upon success.
     */
    @FXML
    public void deleteCustomerButtonClicked() {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected to delete.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if (appointment.getCustomerID() == selectedCustomer.getCustomerID()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, selectedCustomer.getName() + " has active " +
                        "appointments.\n\nPlease delete this customers appointments prior to deleting this customer.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting " + selectedCustomer.getName()
                + " will remove it permanently\nClick okay to confirm deletion or cancel to cancel this action.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            CustomerDAO.removeCustomer(selectedCustomer);
            Database.deleteCustomer(selectedCustomer.getCustomerID());
        }

        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION, "Customer ID: " +
                selectedCustomer.getCustomerID() + ", Name: " + selectedCustomer.getName() + " successfully " +
                "deleted!");
        informationAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        informationAlert.showAndWait();
        customerTableView.refresh();
    }

    /**
     * Opens the generate customer appointments dialog box or generates an error if unsuccessful.
     */
    @FXML
    public void generateCustomerAppointments() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("reports/customerAppointmentsForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Couldn't load the dialog");
        }
        dialog.showAndWait();
    }

    /**
     * Opens the generate contact schedules dialog box or generates an error if unsuccessful.
     */
    @FXML
    public void generateContactSchedules() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("reports/contactScheduleForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Couldn't load the dialog");
        }
        dialog.showAndWait();
    }

    /**
     * Opens the generate division customers dialog box or generates an error if unsuccessful.
     */
    @FXML
    public void generateDivisionCustomers() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("reports/divisionCustomersForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Couldn't load the dialog");
        }
        dialog.showAndWait();
    }

    @FXML
    public void onEnterAppointmentSearch() {
        ObservableList<Appointment> matchingAppointments = FXCollections.observableArrayList();
        int idSearch;

        if (appointmentSearchBar.getText().equals("")) {
            appointmentsTableView.setItems(AppointmentDAO.getAllUserAppointments());
        }

        try {
            idSearch = Integer.parseInt(appointmentSearchBar.getText());
            matchingAppointments.add(AppointmentDAO.lookupAppointment(idSearch));
        } catch (NumberFormatException e){
            matchingAppointments = AppointmentDAO.lookupAppointment(appointmentSearchBar.getText());
        }

        if (matchingAppointments.size() == 0) {
            return;
        }

        if (matchingAppointments.size() == 1) {
            appointmentsTableView.getSelectionModel().select(matchingAppointments.get(0));
        } else {
            appointmentsTableView.setItems(matchingAppointments);
        }
    }

    @FXML
    public void onEnterCustomerSearch() {
        ObservableList<Customer> matchingCustomers = FXCollections.observableArrayList();
        int idSearch;

        if (customerSearchBar.getText().equals("")) {
            customerTableView.setItems(CustomerDAO.getAllCustomers());
        }

        try {
            idSearch = Integer.parseInt(customerSearchBar.getText());
            matchingCustomers.add(CustomerDAO.lookupCustomer(idSearch));
        } catch (NumberFormatException e){
            matchingCustomers = CustomerDAO.lookupCustomer(customerSearchBar.getText());
        }

        if (matchingCustomers.size() == 0) {
            return;
        }

        if (matchingCustomers.size() == 1) {
            customerTableView.getSelectionModel().select(matchingCustomers.get(0));
        } else {
            customerTableView.setItems(matchingCustomers);
        }
    }

    /**
     * Exits the program if the exit button is clicked.
     */
    @FXML
    public void exitButtonClicked() {
        System.exit(0);
    }
}