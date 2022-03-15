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
    private String placeName;
    private double lat;
    private double lon;
    private int nLoc;

    public User(long chatID, String firstName, String placeName, double lat, double lon, int nLoc) {
        this.chatID = chatID;
        this.firstName = firstName;
        this.placeName = placeName;
        this.lat = lat;
        this.lon = lon;
        this.nLoc = nLoc;
    }

    public User(String CSV) {
        String[] cells = CSV.split(";");
        this.chatID = Long.valueOf(cells[0]);
        this.firstName = cells[1];
        this.placeName = cells[2];
        this.lat = Double.valueOf(cells[3]);
        this.lon = Double.valueOf(cells[4]);
        this.nLoc = Integer.valueOf(cells[5]);
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setnLoc(int nLoc) {
        this.nLoc = nLoc;
    }

    public long getChatID() {
        return chatID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getnLoc() {
        return nLoc;
    }

    public String toCSV() {
        return chatID + ";" + firstName + ";" + placeName + ";" + lat + ";" + lon + ";" + nLoc;
    }
}
