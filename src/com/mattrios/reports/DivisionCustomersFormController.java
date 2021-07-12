package com.mattrios.reports;

import com.mattrios.customers.Customer;
import com.mattrios.prepopulated_entities.FirstLevelDivision;
import com.mattrios.Database;
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
public class DivisionCustomersFormController {
    @FXML
    private TableView<Customer> divisionCustomersTable;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionIDColumn;
    @FXML
    private ComboBox selectedDivisionComboBox;

    private List<FirstLevelDivision> allDivisions = Database.getAllFirstLevelDivisions();

    /**
     * Populates the selected division combo box with the name of all the divisions.
     */
    public void initialize() {
        for(FirstLevelDivision division : allDivisions) {
            selectedDivisionComboBox.getItems().add(division.getDivision());
        }
    }


    /**
     * Triggers when a division is selected in the selected division combo box. Matches the division name in the
     * selected division combo box with the object that has the same name in the all divisions list. Gets the division
     * ID of this object and passes it to the getDivisionCustomers method in PreparedStatements to get all customers who
     * belong to this division from the database. Populates the table view with these customers' information.
     *
     * @param e Dummy ActionEvent object.
     */
    @FXML
    public void divisionSelected(ActionEvent e) {
        ObservableList<Customer> divisionCustomers = FXCollections.observableArrayList();

        for (FirstLevelDivision division : allDivisions) {
            if (division.getDivision().equals(selectedDivisionComboBox.getValue())) {
                divisionCustomers = Database.getDivisionCustomers(division.getDivisionID());
                break;
            }
        }

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        divisionCustomersTable.setItems(divisionCustomers);

    }

    /**
     * Calls the closeStage method when the exit button is clicked.
     */
    @FXML
    public void exitButtonClicked() {
        closeStage();
    }

    /**
     * Closes the current dialog.
     */
    private void closeStage() {
        Stage stage = (Stage) divisionCustomersTable.getScene().getWindow();
        stage.close();
    }
}
