package br.com.garrav.projetogarrav.model;

import java.util.List;

public class Event_User {

    private long id_user;
    private long id_event;

    //List Presence Event
    private static List<Event_User> uniqueListEvent_User;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_event() {
        return id_event;
    }

    public void setId_event(long id_event) {
        this.id_event = id_event;
    }

    //Actual List Presence
    public static List<Event_User> getUniqueListEvent_User() {
        return uniqueListEvent_User;
    }

    public static void setUniqueListEvent_User(List<Event_User> uniqueListEvent_User) {
        Event_User.uniqueListEvent_User = uniqueListEvent_User;
    }
}
