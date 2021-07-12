package com.mattrios.customers;

import com.mattrios.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Locale;

/**
 * @author Matt Rios
 */
public class CustomerDAO {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Calls PreparedStatements.getAllCustomers to generate the initial Observable list which holds all the
     * customer objects. This list is then returned to the calling method to populate the customers tableview.
     *
     * @return allCustomers
     */
    public static ObservableList<Customer> getAllCustomers() {
        allCustomers = Database.getAllCustomers();
        return allCustomers;
    }

    /**
     * Adds a new customer passed from the calling method into the allCustomers observable list.
     *
     * @param customer the customer to be added to the list
     */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * Removes a customer passed from the calling method from the allCustomers observable list.
     *
     * @param customer the customer to be removed from the list.
     */
    public static void removeCustomer(Customer customer) {
        allCustomers.remove(customer);
    }


    /**
     * Updates an existing customer in the allCustomers list by removing the original customer which matches
     * the customerID and adding the new customer passed to this method.
     *
     * @param removeCustomerID the ID of the customer to be removed.
     * @param addCustomer the customer to be added to the list.
     */
    public static void editCustomer(int removeCustomerID, Customer addCustomer) {
        for (Customer customer : allCustomers) {
            if (customer.getCustomerID() == removeCustomerID) {
                allCustomers.remove(customer);
                break;
            }
        }
        allCustomers.add(addCustomer);
    }

    public static Customer lookupCustomer(int customerId) {
        Customer customerToLookup = null;

        for (Customer customer : allCustomers) {
            if (customer.getCustomerID() == customerId) {
                customerToLookup = customer;
                break;
            }
        }

        if (customerToLookup == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not find customer id: " + customerId);
            alert.showAndWait();
        }

        return customerToLookup;
    }

    public static ObservableList<Customer> lookupCustomer(String customerName) {
        ObservableList<Customer> matchingCustomers = FXCollections.observableArrayList();
        customerName = customerName.toLowerCase();

        if (customerName == null) {
            return allCustomers;
        }

        for (Customer customer : allCustomers) {
            if (customer.getName().toLowerCase().contains(customerName)) {
                matchingCustomers.add(customer);
            }
        }

        if (matchingCustomers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not find customers matching customer name: "
                    + customerName);
            alert.showAndWait();
        }

        return matchingCustomers;
    }
}
