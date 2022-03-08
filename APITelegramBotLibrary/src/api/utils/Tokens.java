/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author pacie
 */
public class Tokens {

    private String telegram;
    private String mapQuest;

    public Tokens() throws FileNotFoundException {
        Scanner s = new Scanner(new File("token.json"));
        s.useDelimiter("\u001a");
        JSONObject obj = new JSONObject(s.next());
        telegram = obj.getString("telegram");
        mapQuest = obj.getString("mapquest");
    }

    public String getTelegram() {
        return telegram;
    }

    public String getMapQuest() {
        return mapQuest;
    }
}
