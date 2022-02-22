/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytelegramapi;

import org.json.JSONObject;

/**
 *
 * @author paci_emanuele
 */
public class Update {

    public Integer message_id;
    public From from;
    public Chat chat;
    public Entity entities;
    private JSONObject obj;

    public Update() {
        message_id = null;
        from = null;
        chat = null;
        entities = null;
        obj = null;
    }
    
    public Update(JSONObject obj){
        message_id = null;
        from = null;
        chat = null;
        entities = null;
        this.obj = obj;
        
        message_id = obj.getInt("message_id");
        System.out.println(message_id);
    }

    public Update(Integer message_id, From from, Chat cha, Entity entities) {
        this.message_id = message_id;
        this.from = from;
        this.chat = cha;
        this.entities = entities;
        obj = null;
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

        public From(Integer id, Boolean is_bot, String first_name, String username, String language_code) {
            this.id = id;
            this.is_bot = is_bot;
            this.first_name = first_name;
            this.username = username;
            this.language_code = language_code;
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

        public Chat(Integer id, Boolean is_bot, String first_name, String username, String type) {
            this.id = id;
            this.is_bot = is_bot;
            this.first_name = first_name;
            this.username = username;
            this.type = type;
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

        public Entity(Integer offset, Integer length, String type) {
            this.offset = offset;
            this.length = length;
            this.type = type;
        }
    }
}
