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
public class Chat {

    public Integer id;
    public Boolean is_bot;
    public String first_name;
    public String username;
    public String type;
    public Photo photo;

    public Chat() {
        id = null;
        is_bot = null;
        first_name = null;
        username = null;
        type = null;
        photo = null;
    }

    public Chat(JSONObject obj) {
        id = obj.has("id") ? obj.getInt("id") : null;
        is_bot = obj.has("is_bot") ? obj.getBoolean("is_bot") : null;
        first_name = obj.has("first_name") ? obj.getString("first_name") : null;
        username = obj.has("username") ? obj.getString("username") : null;
        type = obj.has("type") ? obj.getString("type") : null;
        photo = obj.has("photo") ? new Photo(obj.getJSONObject("photo")) : null;
    }

    public Chat(Integer id, Boolean is_bot, String first_name, String username, String type) {
        this.id = id;
        this.is_bot = is_bot;
        this.first_name = first_name;
        this.username = username;
        this.type = type;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n"
                + "is_bot: " + is_bot + "\n"
                + "first_name: " + first_name + "\n"
                + "username: " + username + "\n"
                + "type: " + type + "\n" 
                + "photo: " + photo;
    }

    public class Photo {

        public String small_file_id;
        public String small_file_unique_id;
        public String big_file_id;
        public String big_file_unique_id;

        public Photo(JSONObject obj) {
            small_file_id = obj.has("small_file_id") ? obj.getString("small_file_id") : null;
            small_file_unique_id = obj.has("small_file_unique_id") ? obj.getString("small_file_unique_id") : null;
            big_file_id = obj.has("big_file_id") ? obj.getString("big_file_id") : null;
            big_file_unique_id = obj.has("big_file_unique_id") ? obj.getString("big_file_unique_id") : null;
        }

        @Override
        public String toString() {
            return "small_file_id: " + small_file_id + "\n"
                    + "small_file_unique_id: " + small_file_unique_id + "\n"
                    + "big_file_id: " + big_file_id + "\n"
                    + "big_file_unique_id: " + big_file_unique_id;
        }
    }
}
