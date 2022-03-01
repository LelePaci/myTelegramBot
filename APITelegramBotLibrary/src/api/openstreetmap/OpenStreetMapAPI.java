/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.openstreetmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
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

    private String baseURL;
    private String addonURL;

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
}
