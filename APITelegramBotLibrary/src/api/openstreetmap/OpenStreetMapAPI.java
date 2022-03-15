/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.openstreetmap;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author paci_emanuele
 */
public class OpenStreetMapAPI {

    private final String baseURL;
    private final String addonURL;

    public OpenStreetMapAPI() {
        this.baseURL = "https://nominatim.openstreetmap.org/search?q=";
        this.addonURL = "&format=xml&polygon_geojson=1&addressdetails=1%20Aperto%20marted%C3%AC,%201%20febbraio%202022,%2000:00";
    }

    private String getXML(String search) throws FileNotFoundException, IOException {
        URL url = new URL(baseURL + URLEncoder.encode(search, StandardCharsets.UTF_8) + addonURL);
        Scanner scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\u001a");
        String file = scanner.next();
        //System.out.println(file);
        return file;
    }

    public SearchResults searchPlace(String place) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xml = getXML(place);
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        SearchResults sr = new SearchResults((Element) doc.getElementsByTagName("searchresults").item(0));
        return sr;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int r = 6371;
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = r * c;
        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
