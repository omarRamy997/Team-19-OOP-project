package com.example.eventmanagement;

import java.time.LocalTime;

public class TimeSlot {
    private LocalTime startTime; // Renamed for clarity
    private LocalTime endTime;   // Renamed for clarity

    public TimeSlot(LocalTime start, LocalTime end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Start time must be before end time and neither can be null.");
        }
        this.startTime = start;
        this.endTime = end;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean overlaps(TimeSlot other) {
        if (other == null) return false;
        // Check if this timeslot starts after the other ends, or ends before the other starts
        return !(this.endTime.isBefore(other.startTime) || this.endTime.equals(other.startTime) || 
                 this.startTime.isAfter(other.endTime) || this.startTime.equals(other.endTime));
    }

    @Override
    public String toString() {
        return "[" + startTime + " - " + endTime + "]";
    }
}

