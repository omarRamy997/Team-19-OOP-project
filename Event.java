package com.example.eventmanagement;

import java.util.ArrayList;
import java.time.LocalTime;

public class Event {
    private String eventName; // Renamed for clarity and convention
    private String eventID;
    private Organizer organizer;
    private Category category;
    private double ticketPrice; // Renamed for clarity and convention
    private final ArrayList<Attendee> attendees = new ArrayList<>();
    private Room room;
    private LocalTime startTime;
    private LocalTime endTime;

    // static int no = 0; // Static counter might not be ideal for instance management, consider UUIDs or DB IDs

    public Event(String eventName, String eventID, double ticketPrice, Room room, Category category, Organizer organizer, LocalTime startTime, LocalTime endTime) {
        this.eventName = eventName;
        this.eventID = eventID;
        this.category = category;
        this.organizer = organizer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPrice = ticketPrice;
        this.room = room;
        // no++;
    }

    @Override
    public String toString() {
        return "Event: " + eventName + " (ID: " + eventID + ", Price: " + String.format("%.2f", ticketPrice) + ")";
    }

    public void addAttendee(Attendee attendee) { // Renamed for clarity
        if (!attendees.contains(attendee)) {
            attendees.add(attendee);
        }
    }

    public void showAttendees() { // Console method, GUI would use getAttendees()
        System.out.println("Attendees for " + eventName + ":");
        if (attendees.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Attendee attendee : attendees) {
                System.out.println("  - " + attendee.getUsername());
            }
        }
    }

    public Category getCategory() {
        return category;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventID() {
        return eventID;
    }
    
    public Room getRoom() {
        return room;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    // Update method might need more specific parameters depending on what can be updated
    public void updateDetails(String eventName, String eventID, Room room, double ticketPrice, LocalTime startTime, LocalTime endTime, Category category) {
        this.eventName = eventName;
        this.eventID = eventID;
        this.room = room;
        this.ticketPrice = ticketPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    // Static delete method like this is problematic as it only nulls the local reference.
    // Deletion should be handled by removing the event from the list in the Database class.
    // public static void delete(Event e) {
    //     e = null; 
    // }
}

