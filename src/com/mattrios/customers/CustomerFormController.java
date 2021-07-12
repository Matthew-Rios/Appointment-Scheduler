package com.mattrios.customers;

import com.mattrios.*;
import com.mattrios.prepopulated_entities.Country;
import com.mattrios.prepopulated_entities.FirstLevelDivision;
import com.mattrios.users.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Matt Rios
 */
public class CustomerFormController {

    @FXML
    private Label addModifyCustomersLabel;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> firstLevelDivisionComboBox;
    @FXML
    private ComboBox<String> contactFrequencyComboBox;

    private ObservableList<Country> allCountries = Database.getAllCountries();
    private ObservableList<FirstLevelDivision> relatedFirstLevelDivisions;
    private ObservableList<FirstLevelDivision> allFirstLevelDivsisions = Database.getAllFirstLevelDivisions();

    private int modifyCustomerID;
    private String modifyCreatedBy;
    private LocalDateTime modifyCreateDate;
    private boolean modifyAppointment = false;

    /**
     * Populates the country combo box with the names of all the countries in the countries table.
     */
    public void initialize() {
        for(Country country: allCountries) {
            countryComboBox.getItems().add(country.getCountry());
        }
        contactFrequencyComboBox.getItems().add("Do Not Contact");
        contactFrequencyComboBox.getItems().add("Monthly Contact");
        contactFrequencyComboBox.getItems().add("Weekly Contact");
        contactFrequencyComboBox.getItems().add("No Contact Limit");
    }

    /**
     * Validates the information provided in all fields. Creates a new customer object and inserts it into the table if
     * the modifyAppointment flag is set to false. Updates the existing object and table entry if set to true. Closes
     * the window when complete.
     */
    @FXML
    public void saveButtonClicked() {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneNumberField.getText();
        String firstLevelDivision = firstLevelDivisionComboBox.getValue();
        String contactFrequency = contactFrequencyComboBox.getValue();
        Customer customer;
        int divisionID = 0;


        if (name.length() > 50 || name.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a name of appropriate length (1-50 characters). " +
                    "Current length = " + name.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (address.length() > 100 || address.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a address of appropriate length (1-100 characters). " +
                    "Current length = " + address.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (postalCode.length() > 50 || postalCode.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a postal code of appropriate length (1-50 characters). " +
                    "Current length = " + postalCode.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (phone.length() > 50 || phone.length() < 1) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please choose a phone number of appropriate length " +
                    "(1-50 characters). Current length = " + phone.length() + " characters.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a value for country.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if (firstLevelDivisionComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a value for first-level division.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        if(contactFrequencyComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Please select a contact frequency.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        for (FirstLevelDivision division : relatedFirstLevelDivisions) {
            if (division.getDivision().equals(firstLevelDivision)) {
                divisionID = division.getDivisionID();
            }
        }

        switch (contactFrequency) {
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

        customer.setName(name);
        customer.setAddress(address);
        customer.setPostalCode(postalCode);
        customer.setPhone(phone);
        customer.setLastUpdate(convertToUTC(LocalDateTime.now()));
        customer.setLastUpdatedBy(User.getInstance().getUserName());
        customer.setDivisionID(divisionID);
        customer.setDivision(Database.getDivision(divisionID));

        if (modifyAppointment) {
            customer.setCustomerID(modifyCustomerID);
            customer.setCreateDate(convertToUTC(modifyCreateDate));
            customer.setCreatedBy(modifyCreatedBy);
            Database.updateCustomer(customer);
            CustomerDAO.editCustomer(modifyCustomerID, customer);
        } else {
            customer.setCustomerID(Database.getCustomerID());
            customer.setCreateDate(convertToUTC(LocalDateTime.now()));
            customer.setCreatedBy(User.getInstance().getUserName());
            Database.insertCustomer(customer);
            CustomerDAO.addCustomer(customer);
        }

        closeStage();
    }

    /**
     * This is triggered when the dialog is first opened if the user is editing an existing customer. It saves a few
     * values from the customer object passed to it by the mainFormController, which is the one being edited. Modify
     * appointment flag is set to true and the header label is adjusted to reflect that a customer is being edited. All
     * of the fields are populated with the current values in the customer object that is being edited. The country
     * Selected method is also called to populate the divisions combo box and enable it.
     *
     * @param customer the customer being edited.
     */
    public void setEditCustomerValues(Customer customer) {
        modifyCustomerID = customer.getCustomerID();
        modifyCreatedBy = customer.getCreatedBy();
        modifyCreateDate = customer.getCreateDate().toLocalDateTime();
        modifyAppointment = true;
        int countryID = 0;

        addModifyCustomersLabel.setText("Edit Customer");
        customerIDField.setText(String.valueOf(customer.getCustomerID()));
        nameField.setText(customer.getName());
        addressField.setText(customer.getAddress());
        postalCodeField.setText(customer.getPostalCode());
        phoneNumberField.setText(customer.getPhone());
        firstLevelDivisionComboBox.getSelectionModel().select(customer.getDivision());

        for (FirstLevelDivision division : allFirstLevelDivsisions) {
            if (customer.getDivisionID() == division.getDivisionID()) {
                countryID = division.getCountryID();
                break;
            }
        }

        for (Country country : allCountries) {
            if (country.getCountryID() == countryID) {
                countryComboBox.getSelectionModel().select(country.getCountry());
                countrySelected(new ActionEvent());
            }
        }

        contactFrequencyComboBox.getSelectionModel().select(customer.getContactFrequency());
    }

    /**
     * This method triggers when a new country is selected in the countries combo box. It clears the values in the first
     * division combo box and repopulates it with the first level division names that are apart of the country that was
     * selected in the countries combo box. The first level divisions combo box begins disabled as there is no country
     * selected when the dialog first opens so this will also enable that box.
     *
     * @param e a dummy ActionEvent object.
     */
    @FXML
    public void countrySelected(ActionEvent e) {
        firstLevelDivisionComboBox.getSelectionModel().clearSelection();
        firstLevelDivisionComboBox.getItems().clear();

        for(Country country: allCountries) {
            if (country.getCountry().equals(countryComboBox.getValue())) {
                relatedFirstLevelDivisions = Database.getRelatedFirstLevelDivisions(country.getCountryID());
                break;
            }
        }

        for(FirstLevelDivision division: relatedFirstLevelDivisions) {
            firstLevelDivisionComboBox.getItems().add(division.getDivision());
        }

        firstLevelDivisionComboBox.setDisable(false);
    }

    private ZonedDateTime convertToUTC (LocalDateTime time) {
        ZonedDateTime timeZoned = time.atZone(ZoneId.systemDefault());
        return timeZoned.withZoneSameInstant(ZoneId.of("UTC"));
    }

    /**
     * Calls the closeStage method when the cancel button is clicked.
     */
    @FXML
    public void cancelButtonClicked() {
        closeStage();
    }

    /**
     * Closes the dialog window.
     */
    private void closeStage() {
        Stage stage = (Stage) customerIDField.getScene().getWindow();
        stage.close();
    }
}
