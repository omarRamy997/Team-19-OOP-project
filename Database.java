package com.example.eventmanagement;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<User> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();
    private static List<Organizer> organizers = new ArrayList<>();
    private static List<Attendee> attendees = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    public static LocalTime promstart =  LocalTime.of(20,0);
    public static LocalTime footballstart =  LocalTime.of(22,0);
    public static LocalTime aiCONFstart =  LocalTime.of(10,0);
    public static LocalTime promend =  LocalTime.of(2,45);
    public static LocalTime footballend =  LocalTime.of(23,30);
    public static LocalTime aiCONFend =  LocalTime.of(12,30);
    static {
        initializeData();
    }
    public static void initializeData() {

        // Create dummy categories
        Category party = new Category("Party");
        Category sports = new Category("Sports");
        Category educational = new Category("Technology");
        Category graduation = new Category("Graduation");
        categories.add(party);
        categories.add(sports);
        categories.add(educational);
        categories.add(graduation);

        Room mainHall = new Room(1, 500);
        Room conferenceRoom = new Room(2, 200);
        Room sportsArena = new Room(3, 1000);
        Room Disco = new Room(4, 150);

        rooms.add(mainHall);
        rooms.add(conferenceRoom);
        rooms.add(sportsArena);
        rooms.add(Disco);

        Admin admin = new Admin("Mandoob", "Mo_72", "2/8/2006", "System Administrator", 30);
        Admin assistantAdmin = new Admin("Saleh", "saksooka42", "18/7/2006", "System Administrator Assistant", 50);
        admins.add(admin);
        admins.add(assistantAdmin);

        Organizer organizer1 = new Organizer("Boody", "abdo12","4/7/2006");
        Organizer organizer2 = new Organizer("Romeo", "Barca123", "28/3/2007");
        organizers.add(organizer1);
        users.add(organizer1);
        organizers.add(organizer2);
        users.add(organizer2);

        Attendee attendee1 = new Attendee("Tellawy", "Jordan18","14/9/2006",500, "Tagamo3-0aly St", Attendee.Gender.MALE,"sports");
        Attendee attendee2 = new Attendee("Jenny", "Knife12", "19/4/2006",1000, "Mokattam", Attendee.Gender.FEMALE,"party" );
        attendees.add(attendee1);
        users.add(attendee1);
        attendees.add(attendee2);
        users.add(attendee2);

        Event prom = new Event("Prom seniors25","Event001", 2999.99,mainHall,graduation,organizer1,promstart,promend);
        Event tournament = new Event("Football Tournament","Event002",149.99,sportsArena,sports,organizer2,footballstart,footballend);
        Event aiConf = new Event("AI Conference","Event003",99.99,conferenceRoom,educational,organizer1,aiCONFstart,aiCONFend);

        events.add(prom);
        events.add(tournament);
        events.add(aiConf);

    }
//    public static boolean createUser(User user) {
//        if (user != null) {
//            users.add(user);
//
//            if (user instanceof Admin) {
//                admins.add((Admin) user);
//            } else if (user instanceof Organizer) {
//                organizers.add((Organizer) user);
//            } else if (user instanceof Attendee) {
//                attendees.add((Attendee) user);
//            }
//
//            return true;
//
//        }
//        return false;
//    }
    public static boolean createEvent(Event event) {
        if (event != null) {
            events.add(event);
            return true;
        }

        return false;
    }

    public static boolean createRoom(Room room) {
        if (room != null) {
            rooms.add(room);
            return true;
        }

        return false;
    }

    public static boolean createCategory(Category category) {
        if (category != null) {
            categories.add(category);
            return true;

        }
        return false;
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }
    public static List<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }
    public static List<Organizer> getOrganizers() {
        return new ArrayList<>(organizers);
    }
    public static List<Attendee> getAttendees() {
        return new ArrayList<>(attendees);
    }
    public static List<Event> getEvents() {
        return new ArrayList<>(events);
    }
    public static List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }
    public static List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public static void setAdmins(Admin a) {
        admins.add(a);
    }

    public static void setOrganizers(Organizer o) {
        organizers.add(o);
    }

    public static void setAttendees(Attendee att) {
        attendees.add(att);
    }

    public static void setEvents(Event e) {
        events.add(e);
    }

    public static void setRooms(Room r) {
        rooms.add(r);
    }

    public static void setCategories(Category c) {
        categories.add(c);
    }
}

