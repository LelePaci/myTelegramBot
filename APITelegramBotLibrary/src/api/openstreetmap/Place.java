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
    public String village;
    public String county;
    public String state;
    public String postcode;
    public String country;
    public String country_code;
    public String railway;
    public String road;
    public String amenity;
    public String city;

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
                + "city: " + city + "\n";
    }
}
