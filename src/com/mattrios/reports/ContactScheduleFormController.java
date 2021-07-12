package com.mattrios.reports;

import com.mattrios.Database;
import com.mattrios.appointments.Appointment;
import com.mattrios.prepopulated_entities.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;

/**
 * @author Matt Rios
 */
public class ContactScheduleFormController {

    @FXML
    private TableView<Appointment> contactAppointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    private ComboBox selectedContactComboBox;

    private List<Contact> allContacts = Database.getContacts();


    /**
     * Initializes the values in the combo box with the names of all stored contacts.
     */
    public void initialize() {
        for(Contact contact: allContacts) {
            selectedContactComboBox.getItems().add(contact.getName());
        }
    }

    /**
     * Matches the contact name in the combo box with the contact object in the all contacts list. Gets the contact ID
     * from the matching contact object and calls the getContactAppointments method in PreparedStatements which returns
     * an observable list of all the appointments associated with that contact id. Populates the table based on the
     * objects in the list.
     *
     * @param e Dummy action event variable
     */
    @FXML
    public void contactSelected(ActionEvent e) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        for (Contact contact : allContacts) {
            if (contact.getName().equals(selectedContactComboBox.getValue())) {
                contactAppointments = Database.getContactAppointments(contact.getContactID());
                break;
            }
        }

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactAppointmentsTable.setItems(contactAppointments);

    }

    /**
     * Calls close stage when the exit button is clicked.
     */
    @FXML
    public void exitButtonClicked() {
        closeStage();
    }

    /**
     * Closes the dialog.
     */
    private void closeStage() {
        Stage stage = (Stage) contactAppointmentsTable.getScene().getWindow();
        stage.close();
    }
}
