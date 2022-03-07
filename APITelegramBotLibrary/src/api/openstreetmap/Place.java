/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.openstreetmap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author paci_emanuele
 */
public class Place {

//    public String at_placeID;
//    public String at_osmType;
//    public String at_osmID;
//    public String at_placeRank;
    private String village;
    private String county;
    private String state;
    private String postcode;
    private String country;
    private String country_code;
    private String railway;
    private String road;
    private String amenity;
    private String city;
    private double lat;
    private double lon;

    public Place(Element e) {
        village = e.getElementsByTagName("village").item(0) != null ? e.getElementsByTagName("village").item(0).getTextContent() : null;
        county = e.getElementsByTagName("county").item(0) != null ? e.getElementsByTagName("county").item(0).getTextContent() : null;
        state = e.getElementsByTagName("state").item(0) != null ? e.getElementsByTagName("state").item(0).getTextContent() : null;
        postcode = e.getElementsByTagName("postcode").item(0) != null ? e.getElementsByTagName("postcode").item(0).getTextContent() : null;
        country = e.getElementsByTagName("country").item(0) != null ? e.getElementsByTagName("country").item(0).getTextContent() : null;
        country_code = e.getElementsByTagName("country_code").item(0) != null ? e.getElementsByTagName("country_code").item(0).getTextContent() : null;
        railway = e.getElementsByTagName("railway").item(0) != null ? e.getElementsByTagName("railway").item(0).getTextContent() : null;
        road = e.getElementsByTagName("road").item(0) != null ? e.getElementsByTagName("road").item(0).getTextContent() : null;
        amenity = e.getElementsByTagName("amenity").item(0) != null ? e.getElementsByTagName("amenity").item(0).getTextContent() : null;
        city = e.getElementsByTagName("city").item(0) != null ? e.getElementsByTagName("city").item(0).getTextContent() : null;
        lat = e.getAttribute("lat") != null ? Double.parseDouble(e.getAttribute("lat")) : null;
        lon = e.getAttribute("lon") != null ? Double.parseDouble(e.getAttribute("lon")) : null;
    }

    public String getVillage() {
        return village;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getRailway() {
        return railway;
    }

    public String getRoad() {
        return road;
    }

    public String getAmenity() {
        return amenity;
    }

    public String getCity() {
        return city;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "village: " + village + "\n"
                + "county: " + county + "\n"
                + "state: " + state + "\n"
                + "postcode: " + postcode + "\n"
                + "country: " + country + "\n"
                + "country_code: " + country_code + "\n"
                + "railway: " + railway + "\n"
                + "road: " + road + "\n"
                + "amenity: " + amenity + "\n"
                + "city: " + city + "\n"
                + "lat: " + lat + "\n"
                +"lon: " + lon + "\n";
    }
}
