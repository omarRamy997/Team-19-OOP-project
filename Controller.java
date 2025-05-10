package com.example.eventmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {

        @FXML
        public Button attendeeLoginButton;
        @FXML
        public Button organizerLoginButton;
        @FXML
        public Button adminLoginButton;
        @FXML
        public Button attendeeSignUpButton;
        @FXML
        public Button organizerSignUpButton;
        @FXML
        public Button adminSignUpButton;
        @FXML
        public Button SignUpButton;
        @FXML
        public Button attendeeDashboardButton;
        @FXML
        public Button returnToHomepageButton;
        @FXML
        public Button LogoutButton;
        @FXML
        public Button adminDashboardButton;
        @FXML
        public Button organizerDashboardButton;

    public void switchScene(Button button, String fxmlFile, String title) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
          Parent root = loader.load();
          Stage stage = (Stage) button.getScene().getWindow();
          stage.setScene(new Scene(root));
          stage.setTitle(title);
          stage.show();
    }
    @FXML
    public void switchToAdminLogin() throws IOException {
         switchScene(adminLoginButton, "AdminLogin.fxml", "Admin Login");
    }

    @FXML
    public void switchToAttendeeLogin() throws IOException {
        switchScene(attendeeLoginButton, "AttendeeLogin.fxml", "Attendee Login");
    }

    @FXML
    public void switchToOrganizerLogin() throws IOException {
         switchScene(organizerLoginButton, "OrganizerLogin.fxml", "Organizer Login");
    }
    @FXML
    public void switchSignUp() throws IOException {
        switchScene(SignUpButton, "SignUp.fxml", "Sign Up");
    }
    @FXML
    public void switchToAttendeeSignUp() throws IOException {
        switchScene(attendeeSignUpButton, "AttendeeSignUp.fxml", "Attendee Sign Up");
    }
    @FXML
    public void switchToOrganizerSignUp() throws IOException {
        switchScene(organizerSignUpButton, "OrganizerSignUp.fxml", "Organizer Sign Up");
    }
    @FXML
    public void switchToAdminSignUp() throws IOException {
        switchScene(adminSignUpButton, "AdminSignUp.fxml", "Admin Sign Up");
    }

        public void loadScene(String fxmlFile, String title) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) adminLoginButton.getScene().getWindow(); // Or any other button on the current scene
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        }

        public void SwitchtoAttendeeLogin(ActionEvent actionEvent) throws IOException {
            loadScene("AttendeeLogin.fxml", "Attendee Login");
        }


        private Attendee attendee;

        @FXML private Button btnInfo;
        @FXML private Button btnEvents;
        @FXML private Button btnPurchase;
        @FXML private TextArea txtDisplay;


        public void init(Attendee attendee) {
            this.attendee = attendee;
            txtDisplay.setText("Welcome, " + attendee.getUsername() + "!\nClick a button to begin.");
        }

        @FXML
        private void displayInfo() {
            captureAndShow(() -> attendee.displayInfo());
        }

        @FXML
        private void showAvailableEvents() {
            captureAndShow(() -> attendee.showAvailableEvents());
        }

        @FXML
        private void purchaseTicket() {

            captureAndShow(() -> attendee.showAvailableEvents());
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Purchase Ticket");
            dialog.setHeaderText("Enter event number to purchase:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int idx = Integer.parseInt(result.get().trim()) - 1;
                    List<Event> events = Database.getEvents();
                    if (idx < 0 || idx >= events.size()) {
                        txtDisplay.appendText("\nInvalid event number.\n");
                        return;
                    }
                    Event chosen = events.get(idx);
                    boolean success = attendee.purchaseTicket(chosen);
                    if (success) {
                        txtDisplay.appendText("\nSuccessfully purchased ticket for: "
                                + chosen.getEventName() + "\n");
                    } else {
                        txtDisplay.appendText("\nPurchase failed (insufficient funds or already registered).\n");
                    }
                } catch (NumberFormatException ex) {
                    txtDisplay.appendText("\nPlease enter a valid number.\n");
                }
            }
        }

        /** Helper: redirect System.out to buffer then show in TextArea */
        private void captureAndShow(Runnable action) {
            PrintStream realOut = System.out;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            System.setOut(new PrintStream(buffer));

            action.run();

            System.setOut(realOut);
            txtDisplay.setText(buffer.toString());
        }


    @FXML
    private TextField signupUsernameField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private TextField signupDobField;

    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private DatePicker adminDobPicker;

    @FXML
    private TextField organizerUsernameField;
    @FXML
    private PasswordField organizerPasswordField;
    @FXML
    private DatePicker organizerDobPicker;
    @FXML
    private TextField signupBalanceField;
    @FXML
    private TextField signupAddressField;
    @FXML
    private ComboBox<Attendee.Gender> signupGenderComboBox;
    @FXML
    private TextField signupInterestField;

    public void handleAdminSignUp(ActionEvent actionEvent) throws IOException {
        try {
            String username = adminUsernameField.getText();
            String password = adminPasswordField.getText();
            String dob = adminDobPicker.getValue().toString();

            if (username.isEmpty() || password.isEmpty() || dob.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
                return;
            }

            Admin newAdmin = new Admin(username, password, dob, "Administrator", 40);
            Database.setAdmins(newAdmin);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Admin sign up successful!");
            switchScene(adminSignUpButton, "Homepage.fxml", "Homepage");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not complete signup process");
        }
    }

    public void handleOrganizerSignup(ActionEvent actionEvent) throws IOException {
        try {
            String username = organizerUsernameField.getText();
            String password = organizerPasswordField.getText();
            String dob = organizerDobPicker.getValue().toString();

            if (username.isEmpty() || password.isEmpty() || dob.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
                return;
            }

            Organizer newOrganizer = new Organizer(username, password, dob);
            Database.setOrganizers(newOrganizer);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Organizer sign up successful!");
            switchScene(organizerSignUpButton, "Homepage.fxml", "Homepage");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not complete signup process");
        }
    }

    public void handleAttendeeSignUp(ActionEvent actionEvent) throws IOException, NumberFormatException {
        try {
            String username = signupUsernameField.getText();
            String password = signupPasswordField.getText();
            String dob = signupDobField.getText();
            double balance = Double.parseDouble(signupBalanceField.getText());
            String address = signupAddressField.getText();
            Attendee.Gender gender = signupGenderComboBox.getValue();
            String interest = signupInterestField.getText();

            if (username.isEmpty() || password.isEmpty() || dob.isEmpty() ||
                    address.isEmpty() || gender == null || interest.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
                return;
            }

            Attendee newAttendee = new Attendee(username, password, dob, balance, address, gender, interest);
            Database.setAttendees(newAttendee);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Sign up successful!");
            switchScene(attendeeSignUpButton, "Homepage.fxml", "Homepage");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid balance amount");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not return to homepage");
        }
    }

        @FXML
        private TextField roomNumberField;
        @FXML private TextField roomCapacityField;
        @FXML private TextArea outputArea;

        @FXML
        public void Addroom(ActionEvent event) {
            try {
                outputArea.clear();
                int roomNo = Integer.parseInt(roomNumberField.getText());
                int capacity = Integer.parseInt(roomCapacityField.getText());
                Admin.addRoom(new Room(roomNo, capacity));
                String message = "Room " + roomNo + " added with capacity " + capacity + "\n";
                outputArea.appendText(message);

                roomNumberField.clear();
                roomCapacityField.clear();

            } catch (NumberFormatException e) {
                outputArea.appendText("Please enter valid numbers.\n");
            }
        }

        public void ShowEvents(ActionEvent e) {
            outputArea.clear();
            List<String> eventDetailsList = Admin.viewAllEvents();


            outputArea.setText(String.join("\n", eventDetailsList));
        }
        public void ShowAttendees(ActionEvent e) {

            outputArea.clear();
            List<String> attendeeDetailsList = Admin.viewAllAttendees();


            outputArea.setText(String.join("\n", attendeeDetailsList));



        }

        public void ShowRooms(ActionEvent e) {
            outputArea.clear();
            List<String> roomDetailsList = Admin.viewAllROOMs();


            outputArea.setText(String.join("\n", roomDetailsList));
        }


    @FXML private Button loginButton;
    @FXML private Button logoutButton;
    @FXML private TextField eventNameField;
    @FXML private TextField eventIDField;
    @FXML private TextField ticketPriceField;
    @FXML private ListView<String> availableRoomsForEventListView;
    @FXML private ListView<String> categoriesForEventListView;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private Button createEventButton;
    @FXML private ListView<String> myEventsListView;
    @FXML private Button viewEventAttendeesButton;
    @FXML private ListView<String> eventAttendeesListView;
    @FXML private TextField checkStartTimeField;
    @FXML private TextField checkEndTimeField;
    @FXML private Button viewAvailableRoomsButton;
    @FXML private ListView<String> availableRoomsListViewOutput;

    private Stage primaryStage;
    private Organizer currentOrganizer;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCurrentOrganizer(Organizer organizer) {
        this.currentOrganizer = organizer;
        if (this.currentOrganizer != null) {
            loadOrganizerDashboardData();
        }
    }

    @FXML
    public void initialize() {
        if (myEventsListView != null && currentOrganizer != null) {
            loadOrganizerDashboardData();
        }
        if (availableRoomsForEventListView != null) {
            availableRoomsForEventListView.getItems().setAll(Database.getRooms().stream().map(Room::toString).collect(Collectors.toList()));
        }
        if (categoriesForEventListView != null) {
            categoriesForEventListView.getItems().setAll(Database.getCategories().stream().map(Category::toString).collect(Collectors.toList()));
        }
    }


    @FXML
    private void handleCreateEvent() {
        if (currentOrganizer == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No organizer logged in.");
            return;
        }
        try {
            String eventName = eventNameField.getText();
            String eventID = eventIDField.getText();
            double ticketPrice = Double.parseDouble(ticketPriceField.getText());
            String selectedRoomString = availableRoomsForEventListView.getSelectionModel().getSelectedItem();
            String selectedCategoryString = categoriesForEventListView.getSelectionModel().getSelectedItem();
            LocalTime startTime = LocalTime.parse(startTimeField.getText());
            LocalTime endTime = LocalTime.parse(endTimeField.getText());

            if (eventName.isEmpty() || eventID.isEmpty() || selectedRoomString == null || selectedCategoryString == null || startTimeField.getText().isEmpty() || endTimeField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled and selections made.");
                return;
            }

            Room selectedRoom = Database.getRooms().stream()
                    .filter(r -> r.toString().equals(selectedRoomString))
                    .findFirst().orElse(null);
            Category selectedCategory = Database.getCategories().stream()
                    .filter(c -> c.toString().equals(selectedCategoryString))
                    .findFirst().orElse(null);

            if (selectedRoom == null || selectedCategory == null) {
                showAlert(Alert.AlertType.ERROR, "Selection Error", "Invalid room or category selected.");
                return;
            }

            if (!selectedRoom.isAvailable(startTime, endTime)) {
                showAlert(Alert.AlertType.WARNING, "Booking Conflict", "Selected room is not available at the specified time.");
                return;
            }

            currentOrganizer.createEvent(eventName, eventID, ticketPrice, selectedRoom, selectedCategory, currentOrganizer, startTime, endTime);
            showAlert(Alert.AlertType.INFORMATION, "Event Created", "Event '" + eventName + "' created successfully.");
            clearEventCreationFields();
            loadOrganizerDashboardData();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for ticket price.");
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter time in HH:mm format (e.g., 14:30).");
        }
    }

    @FXML
    private void handleViewEventAttendees() {
        String selectedEventString = myEventsListView.getSelectionModel().getSelectedItem();
        if (selectedEventString == null || currentOrganizer == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an event to view attendees.");
            return;
        }
        Event selectedEvent = currentOrganizer.getMyEvents().stream()
                .filter(e -> e.toString().equals(selectedEventString))
                .findFirst().orElse(null);
        if (selectedEvent != null) {
            eventAttendeesListView.getItems().setAll(selectedEvent.getAttendees().stream().map(Attendee::toString).collect(Collectors.toList()));
        } else {
            eventAttendeesListView.getItems().clear();
        }
    }

    @FXML
    private void handleViewAvailableRooms() {
        try {
            LocalTime startTime = LocalTime.parse(checkStartTimeField.getText());
            LocalTime endTime = LocalTime.parse(checkEndTimeField.getText());

            availableRoomsListViewOutput.getItems().setAll(
                    Database.getRooms().stream()
                            .filter(room -> room.isAvailable(startTime, endTime))
                            .map(Room::toString)
                            .collect(Collectors.toList())
            );
            if(availableRoomsListViewOutput.getItems().isEmpty()){
                showAlert(Alert.AlertType.INFORMATION, "No Rooms", "No rooms available for the selected time slot.");
            }
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter time in HH:mm format (e.g., 10:00) for checking availability.");
        }
    }

    private void loadOrganizerDashboardData() {
        if (currentOrganizer != null && myEventsListView != null) {
            myEventsListView.getItems().setAll(currentOrganizer.getMyEvents().stream().map(Event::toString).collect(Collectors.toList()));
        }
    }

    private void clearEventCreationFields() {
        eventNameField.clear();
        eventIDField.clear();
        ticketPriceField.clear();
        startTimeField.clear();
        endTimeField.clear();
        availableRoomsForEventListView.getSelectionModel().clearSelection();
        categoriesForEventListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleLogout() throws IOException {
        currentOrganizer = null;
        switchScene(returnToHomepageButton, "Homepage.fxml", "Homepage");
    }

//    @FXML
//    private void SwitchtoHomePage() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
//            AnchorPane homePageLayout = loader.load();
//            Scene scene = new Scene(homePageLayout);
//            Stage stage = primaryStage != null ? primaryStage : (Stage) logoutButton.getScene().getWindow();
//            stage.setScene(scene);
//            stage.setTitle("Event Management System - Home");
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Loading Error", "Could not load the home page.");
//        }
//    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




@FXML private TextField usernameField;
        @FXML private PasswordField passwordField;

    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (Attendee attendee : Database.getAttendees()) {
            if (username.equals(attendee.getUsername()) && password.equals(attendee.getPassword())) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendeeDashboard.fxml"));
                    Parent root = loader.load();
                    Controller controller = loader.getController();
                    controller.init(attendee);
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Attendee Dashboard");
                    stage.show();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        for (Organizer organizer : Database.getOrganizers()) {
            if (username.equals(organizer.getUsername()) && password.equals(organizer.getPassword())) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrganizerDashboard.fxml"));
                    Parent root = loader.load();
                    Controller controller = loader.getController();
                    controller.init(organizer);
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Organizer Dashboard");
                    stage.show();
                    setCurrentOrganizer(organizer);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Admin admin : Database.getAdmins()) {
        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
                Parent root = loader.load();
                Controller controller = loader.getController();
                controller.init(admin);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Attendee Dashboard");
                stage.show();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }

    private void init(Organizer organizer) {
    }

    private void init(Admin admin) {
    }

    public void SwitchtoHomePage(ActionEvent actionEvent) throws IOException {
        switchScene(returnToHomepageButton, "Homepage.fxml", "Homepage");
        }
    public void Logout(ActionEvent actionEvent) throws IOException {
        switchScene(LogoutButton, "Homepage.fxml", "Homepage");
    }


}
