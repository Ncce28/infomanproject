package com.example.controller;  // Replace this with your own package name

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentFormController {


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private Button saveButton;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db"; // Change to your database URL
    private static final String DB_USER = "root"; // Change if necessary
    private static final String DB_PASSWORD = ""; // Change to your MySQL password

    // Method to handle the Save button action
    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        try {
            // Retrieve values from the form fields
            String firstName = firstNameField.getText();
            String middleName = middleNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();

            // Validate the input
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all required fields.");
                return;
            }

            // Save the data to the database
            saveStudentToDatabase(firstName, middleName, lastName, address, phoneNumber, email, gender);

            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Student information saved successfully!");

            // Clear the form fields after saving
            clearFormFields();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save student information.");
        }
    }

    // Method to save the student's data to the database
    private void saveStudentToDatabase(String firstName, String middleName, String lastName, String address,
                                       String phoneNumber, String email, String gender) throws Exception {
        // Establish a connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        // SQL insert query
        String query = "INSERT INTO students (first_name, middle_name, last_name, address, phone_number, email, gender) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Prepare the SQL statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, middleName);
        preparedStatement.setString(3, lastName);
        preparedStatement.setString(4, address);
        preparedStatement.setString(5, phoneNumber);
        preparedStatement.setString(6, email);
        preparedStatement.setString(7, gender);

        // Execute the query
        preparedStatement.executeUpdate();

        // Close the connection
        preparedStatement.close();
        connection.close();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFormFields() {
        firstNameField.clear();
        middleNameField.clear();
        lastNameField.clear();
        addressField.clear();
        phoneNumberField.clear();
        emailField.clear();
        genderGroup.selectToggle(null);
    }
}
