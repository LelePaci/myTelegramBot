/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.openstreetmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author paci_emanuele
 */
public class OpenStreetMapAPI {

    public String searchPlace(String place) throws FileNotFoundException, MalformedURLException, IOException {
        PrintWriter out = new PrintWriter("request.xml");
        URLEncoder.encode(place, StandardCharsets.UTF_8);
        URL url = new URL("https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(place, StandardCharsets.UTF_8) + "&format=xml&polygon_geojson=1&addressdetails=1%20Aperto%20marted%C3%AC,%201%20febbraio%202022,%2000:00");
        Scanner scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\u001a"); //-> tabella ascii - delimitatore 
        String file = scanner.next();
        out.write(file);
        out.close();

        return file;
    }
}
