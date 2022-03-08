/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.mapquest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author pacie
 */
public class MapQuestAPI {

    private String baseURL;
    private String token;
    private int width;
    private int height;
    private int zoom;

    public MapQuestAPI(String token) {
        this.token = token;
        this.width = 300;
        this.height = 200;
        this.zoom = 13;
        this.baseURL = "https://open.mapquestapi.com/staticmap/v4/getmap?key=";
    }

    public URL getImage(String lat, String lon) throws MalformedURLException, IOException {
        return new URL(baseURL + token + "&size=" + width + "," + height + "&zoom=" + zoom + "&center=" + lat + "," + lon);
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
