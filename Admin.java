package com.example.eventmanagement;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User  {
    private String role;
    private int workingHours;

    public Admin(String username, String password, String dateOfBirth, String role, int workingHours) {
        super(username, password, dateOfBirth);
        this.role = role;
        this.workingHours = workingHours;
    }

    public static void addRoom(Room room) {
        Database.setRooms(room);
        System.out.println("Room added: " + room.getRoomNo());
    }


    public static List<String> viewAllROOMs() {
        List<String> eventDetailsList = new ArrayList<>();

        for (Room room : Database.getRooms()) {
            // إضافة تفاصيل الحدث إلى الـ List
            eventDetailsList.add(room.toString());  // assuming event.toString() returns a detailed description of the event
        }

        return eventDetailsList;  // إرجاع قائمة النصوص
    }

    public static List<String> viewAllEvents() {
        List<String> eventDetailsList = new ArrayList<>();

        for (Event event : Database.getEvents()) {
            eventDetailsList.add(event.toString());
        }

        return eventDetailsList;  // إرجاع قائمة النصوص
    }
    public static List<String> viewAllAttendees() {
        List<String> eventDetailsList = new ArrayList<>();

        for (Attendee attendee : Database.getAttendees()) {
            // إضافة تفاصيل الحدث إلى الـ List
            eventDetailsList.add(attendee.toString());  // assuming event.toString() returns a detailed description of the event
        }

        return eventDetailsList;  // إرجاع قائمة النصوص
    }



    public String getRole() {
        return role;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();

        for (Admin admin : Database.getAdmins()) {
            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
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

        System.out.println("Please enter your role: ");
        String role = sc.nextLine();
        if (role.isEmpty()) {
            System.out.println("Role cannot be empty.");
            return false;
        }

        System.out.println("Please enter your working hours: ");
        int workingHours;
        try {
            workingHours = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid working hours. Please enter a valid number.");
            return false;
        }

        Admin newAdmin = new Admin(username, password, dateOfBirth, role, workingHours);
        Database.setAdmins(newAdmin);
        System.out.println("Signup successful! Admin added to the database.");
        return true;
    }
}