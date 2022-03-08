/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.utils;

/**
 *
 * @author paci_emanuele
 */
public class User {

    private long chatID;
    private String firstName;
    private double lat;
    private double lon;

    public User(long chatID, String firstName, double lat, double lon) {
        this.chatID = chatID;
        this.firstName = firstName;
        this.lat = lat;
        this.lon = lon;
    }

    public User(String CSV) {
        String[] cells = CSV.split(";");
        this.chatID = Long.valueOf(cells[0]);
        this.firstName = cells[1];
        this.lat = Double.valueOf(cells[2]);
        this.lat = Double.valueOf(cells[3]);
    }

    public long getChatID() {
        return chatID;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String toCSV() {
        return chatID + ";" + firstName + ";" + lat + ";" + lon;
    }
}
