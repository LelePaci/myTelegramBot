/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.telegram;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author paci_emanuele
 */
public class Message {

    public Integer message_id;
    public From from;
    public Chat chat;
    public Integer date;
    public String text;
    public Entity[] entities;

    public Message(JSONObject obj) {
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
    }

    public Message(Integer message_id, From from, Chat chat, int date, String text, Entity[] entities) {
        this.message_id = message_id;
        this.from = from;
        this.chat = chat;
        this.date = date;
        this.text = text;
        this.entities = entities;
    }

    @Override
    public String toString() {
        String toReturn = "message_id: " + message_id + "\n"
                + "from: " + from.toString() + "\n"
                + "chat: " + chat.toString() + "\n"
                + "date: " + date + "\n"
                + "text: " + text + "\n";
        if (entities != null) {
            for (Entity entitie : entities) {
                toReturn += entitie + "\n";
            }
        }
        return toReturn;
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
