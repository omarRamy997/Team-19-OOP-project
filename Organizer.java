package com.example.eventmanagement;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Organizer extends User {
    private List<Event> myEvents;
    private Wallet wallet;

    public Organizer(String username, String password, String dateOfBirth) {
        super(username, password, dateOfBirth);
        this.myEvents = new ArrayList<>();
        this.wallet = new Wallet(0);
    }

    public List<Event> getMyEvents() {
        return myEvents;
    }

    public void createEvent(String eventName, String eventID, double ticketPrice,
                            Room room, Category category, Organizer organizer, LocalTime start, LocalTime end) {

        if (!room.isAvailable(start, end)) {
            System.out.println("Room is not available at the selected time.");
            return;
        }

        Event event = new Event(eventName, eventID, ticketPrice, room, category, this, start, end);
        room.book(start, end);
        myEvents.add(event);
        Database.setEvents(event);

        System.out.println("Event created: " + event.getEventName()); // Corrected method call
    }

    public void viewMyEvents() {
        if (myEvents.isEmpty()) {
            System.out.println("You have not created any events.");
            return;
        }
        System.out.println("My Events:");
        for (Event event : myEvents) {
            System.out.println(event.toString());
        }
    }

    public List<Room> getAvailableRooms(LocalTime start, LocalTime end) {
        List<Room> available = new ArrayList<>();
        for (Room room : Database.getRooms()) {
            if (room.isAvailable(start, end)) {
                available.add(room);
            }
        }
        return available;
    }

    public List<Attendee> getAttendeesForEvent(Event event) {
        if (myEvents.contains(event)) {
            return event.getAttendees();
        }
        return new ArrayList<>();
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();

        for (Organizer organizer : Database.getOrganizers()) {
            if (username.equals(organizer.getUsername()) && password.equals(organizer.getPassword())) {
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
        Organizer newOrganizer = new Organizer(username, password, dateOfBirth);
        Database.setOrganizers(newOrganizer);
        Database.getUsers().add(newOrganizer);
        System.out.println("Signup successful! Organizer added to the database.");
        return true;
    }

    @Override
    public String toString() {
        return "Organizer: " + getUsername();
    }
}

