/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytelegramapi;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author paci_emanuele
 */
public class Update {

    public Integer message_id;
    public From from;
    public Chat chat;
    public Integer date;
    public String text;
    public Entity[] entities;

    public Update() {
        message_id = null;
        from = null;
        chat = null;
        entities = null;
        date = null;
        text = null;
    }

    public Update(JSONObject obj) {
        chat = null;
        entities = null;

        message_id = obj.has("message_id") ? obj.getInt("message_id") : null;
        from = obj.has("from") ? new From(obj.getJSONObject("from")) : null;
        chat = obj.has("chat") ? new Chat(obj.getJSONObject("chat")) : null;
        date = obj.has("date") ? obj.getInt("date") : null;
        text = obj.has("text") ? obj.getString("text") : null;

        JSONArray e = obj.has("entities") ? obj.getJSONArray("entities") : null;
        if (e != null) {
            entities = new Entity[e.length()];
            for (int i = 0; i < e.length(); i++) {
                entities[i] = new Entity((JSONObject) e.get(i));
            }
        }

        //System.out.println("--------------------------FROM\n" + from.toString());
        //System.out.println("--------------------------CHAT\n" + chat.toString());
        //System.out.println(toString());
    }

    public Update(Integer message_id, From from, Chat chat, int date, Entity[] entities, String text) {
        this.message_id = message_id;
        this.from = from;
        this.chat = chat;
        this.entities = entities;
        this.date = date;
        this.text = text;
    }

    @Override
    public String toString() {
        String toReturn = "message_id" + message_id + "\n"
                + "from" + from.toString() + "\n"
                + "chat" + chat.toString() + "\n"
                + "date" + date + "\n"
                + "text" + text + "\n";
        if (entities != null) {
            for (int i = 0; i < entities.length; i++) {
                toReturn += entities[i] + "\n";
            }
        }
        return toReturn;
    }

    public class From {

        public Integer id;
        public Boolean is_bot;
        public String first_name;
        public String username;
        public String language_code;

        public From() {
            id = null;
            is_bot = null;
            first_name = null;
            username = null;
            language_code = null;
        }

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

    public class Chat {

        public Integer id;
        public Boolean is_bot;
        public String first_name;
        public String username;
        public String type;

        public Chat() {
            id = null;
            is_bot = null;
            first_name = null;
            username = null;
            type = null;
        }

        public Chat(JSONObject obj) {
            id = obj.has("id") ? obj.getInt("id") : null;
            is_bot = obj.has("is_bot") ? obj.getBoolean("is_bot") : null;
            first_name = obj.has("first_name") ? obj.getString("first_name") : null;
            username = obj.has("username") ? obj.getString("username") : null;
            type = obj.has("type") ? obj.getString("type") : null;
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
                    + "type: " + type;
        }
    }

    public class Entity {

        public Integer offset;
        public Integer length;
        public String type;

        public Entity() {
            offset = null;
            length = null;
            type = null;
        }

        public Entity(JSONObject obj) {
            offset = obj.has("offset") ? obj.getInt("offset") : null;
            length = obj.has("length") ? obj.getInt("length") : null;
            type = obj.has("type") ? obj.getString("type") : null;
        }

        public Entity(Integer offset, Integer length, String type) {
            this.offset = offset;
            this.length = length;
            this.type = type;
        }

        @Override
        public String toString() {
            return "offset: " + offset + "\n"
                    + "length: " + length + "\n"
                    + "type: " + type;
        }
    }
}
