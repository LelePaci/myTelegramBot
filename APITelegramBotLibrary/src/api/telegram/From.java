/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.telegram;

import org.json.JSONObject;

/**
 *
 * @author pacie
 */
public class From {

    public Integer id;
    public Boolean is_bot;
    public String first_name;
    public String username;
    public String language_code;

    public From(JSONObject obj) {
        id = obj.has("id") ? obj.getInt("id") : null;
        is_bot = obj.has("is_bot") ? obj.getBoolean("is_bot") : null;
        first_name = obj.has("first_name") ? obj.getString("first_name") : null;
        username = obj.has("username") ? obj.getString("username") : null;
        language_code = obj.has("language_code") ? obj.getString("language_code") : null;
    }

    public From(Integer id, Boolean is_bot, String first_name, String username, String language_code) {
        this.id = id;
        this.is_bot = is_bot;
        this.first_name = first_name;
        this.username = username;
        this.language_code = language_code;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n"
                + "is_bot: " + is_bot + "\n"
                + "first_name: " + first_name + "\n"
                + "username: " + username + "\n"
                + "language_code: " + language_code;
    }
}
