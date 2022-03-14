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
public class CallbackQuery {

    public long id;
    public From from;
    public Message message;
    public long chat_instance;
    public String data;

    public CallbackQuery(JSONObject obj) {
        id = obj.has("id") ? obj.getLong("id") : null;
        from = obj.has("from") ? new From(obj.getJSONObject("from")) : null;
        message = obj.has("message") ? new Message(obj.getJSONObject("message")) : null;
        chat_instance = obj.has("chat_instance") ? obj.getLong("chat_instance") : null;
        data = obj.has("data") ? obj.getString("data") : null;
    }
}
