package com.example.eventmanagement;

import java.util.ArrayList;
import java.util.List; // Added for clarity, though ArrayList implies it

public class Category {
    private String type;
    // Storing events directly in Category might lead to data duplication if events can have multiple categories
    // or if events are primarily managed by a central list. For this project, assuming a simple direct association.
    private final ArrayList<Event> events = new ArrayList<>();
    // static int no = 0; // Static counters are generally not robust for ID generation.

    public Category(String type) {
        this.type = type;
        // no++;
    }

    public void addEvent(Event event) { // Renamed for clarity
        if (!events.contains(event)) {
            events.add(event);
            // Optionally, you might want to set this category on the event if an event can only have one category
            // event.setCategory(this); // This would require a setCategory method in Event class
        }
    }

    public void showEvents() { // Console method, GUI would use getEvents()
        System.out.println("Events in category '" + type + "':");
        if (events.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Event event : events) {
                System.out.println("  - " + event.getEventName());
            }
        }
    }

    public String getType() {
        return type;
    }

    public List<Event> getEvents() { // Added getter for GUI use
        return new ArrayList<>(events); // Return a copy to prevent external modification
    }

    // The delete method `public void delete(Category c){ c=null; }` is ineffective.
    // Deletion should be handled by removing the category from the list in the Database class.

    @Override
    public String toString() {
        return type; // Simple representation for ListView or ChoiceBox
    }
}

