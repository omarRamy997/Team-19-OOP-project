package com.example.eventmanagement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Attendee extends User {
    private Wallet wallet;
    private String address;
    private Gender gender;
    private List<Event> registeredEvents;
    private String interest;


    public enum Gender {
        MALE, FEMALE
    }

    public Attendee(String username, String password, String dateofbirth , double initialBalance, String address, Gender gender,String interest) {
        super(username, password,dateofbirth);
        this.wallet = new Wallet(initialBalance);
        this.address = address;
        this.gender = gender;
        this.registeredEvents = new ArrayList<>();
        this.interest = interest;
    }


    public Wallet getWallet() {
        return wallet;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getInterest() {
        return interest;
    }

    public List<Event> getRegisteredEvents() {
        return registeredEvents;
    }

    public boolean purchaseTicket(Event event) {
        double price = event.getTicketPrice();
        if (!registeredEvents.contains(event) && wallet.hasSufficientFunds(price)) {
            boolean success = wallet.deductFunds(price);
            if (success == true) {
                registeredEvents.add(event);
                event.addAttendee(this);
                event.getOrganizer().getWallet().addFunds(price);
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {

        return "Attendee: " + getUsername();
    }
    public void showAvailableEvents() {
        List<Event> events = Database.getEvents();

        if (events == null || events.isEmpty()) {
            System.out.println("No events are currently available.");
            return;
        }

        System.out.println("\n=== Available Events ===");

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            System.out.println((i + 1) + ". " + event.getEventName());
            System.out.println("   Price: $" + event.getTicketPrice());

        }
    }



    public void displayInfo() {
        System.out.println("<Attendee Details>");
        System.out.println("Username:" + getUsername());
        System.out.println("Date of Birth:" + getDateOfBirth());
        System.out.println("Gender:" + gender);
        System.out.println("Address:" + address);
        System.out.println("Wallet Balance:" + wallet.getBalance() + " EGP");

        System.out.println("Interests:" + interest);
        System.out.println("Registered Events:");
        if (registeredEvents.size() > 0)
        {
            for (int i = 0; i < registeredEvents.size(); i++) {
                System.out.println(registeredEvents.get(i).getEventName());
            }
        }
        else {
            System.out.println("None");
        }
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();

        for (Attendee attendee : Database.getAttendees()) {
            if (username.equals(attendee.getUsername()) && password.equals(attendee.getPassword())) {
                System.out.println("Login successful!");
                return true;
            }
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    @Override
    public boolean signup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }

        System.out.println("Please enter your password: ");
        String password = sc.nextLine();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        System.out.println("Please enter your date of birth (dd-mm-yyyy): ");
        String dateOfBirth = sc.nextLine();
        if (dateOfBirth.isEmpty() || !dateOfBirth.matches("\\d{2}-\\d{2}-\\d{4}")) {
            System.out.println("Invalid date of birth. Please use the format dd-mm-yyyy.");
            return false;
        }

        System.out.println("Please enter your initial wallet balance: ");
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(sc.nextLine());
            if (initialBalance < 0) {
                System.out.println("Initial balance cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance amount.");
            return false;
        }

        System.out.println("Please enter your address: ");
        String address = sc.nextLine();
        if (address.isEmpty()) {
            System.out.println("Address cannot be empty.");
            return false;
        }

        System.out.println("Please select your gender (MALE/FEMALE): ");
        Gender gender;
        try {
            gender = Gender.valueOf(sc.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender choice.");
            return false;
        }

        System.out.println("Please enter your interest: ");
        String interest = sc.nextLine();
        if (interest.isEmpty()) {
            System.out.println("Interest cannot be empty.");
            return false;
        }

        Attendee newAttendee = new Attendee(username, password, dateOfBirth, initialBalance, address, gender, interest);
        Database.setAttendees(newAttendee);
        System.out.println("Signup successful! Attendee added to the database.");
        return true;
    }

}