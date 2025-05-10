package com.example.eventmanagement;

import java.util.ArrayList;
import java.util.List; // Added import for List
import java.time.LocalTime; // Added import for LocalTime

public class Room {
    private int roomNo;
    private int capacity;
    private static List<TimeSlot> bookedSlots; // Assuming TimeSlot is a class that holds start and end times

    public Room(int roomNo, int capacity) {
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.bookedSlots = new ArrayList<>(); // Corrected ArrayList import
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<TimeSlot> getBookedSlots() {
        return bookedSlots;
    }

//    public static boolean isAvailable(LocalTime start, LocalTime end) {
//        for (TimeSlot slot : bookedSlots) {
//            if (start.isBefore(slot.getEndTime()) && end.isAfter(slot.getStartTime())) {
//                return false; // Overlaps with an existing booking
//            }
//        }
//        return true;
//    }

    public void book(LocalTime start, LocalTime end) {
        if (isAvailable(start, end)) {
            bookedSlots.add(new TimeSlot(start, end));
        } else {
            System.out.println("Room is not available at the selected time.");
        }
    }

    @Override
    public String toString() {
        return "Room No: " + roomNo + ", Capacity: " + capacity;
    }

    public boolean isAvailable(LocalTime start, LocalTime end) {
        for (TimeSlot slot : bookedSlots) {
           if (start.isBefore(slot.getEndTime()) && end.isAfter(slot.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    public boolean isAvailable() {
        LocalTime start = null;
        LocalTime end = null;
        for (TimeSlot slot : bookedSlots) {
            if (start.isBefore(slot.getEndTime()) && end.isAfter(slot.getStartTime())) {
                return false;
            }
        }
        return true;
    }
}


