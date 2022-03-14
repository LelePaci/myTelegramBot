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
public class Update {

    public Long update_id;
    public Message message;
    public CallbackQuery callbackQuery;

    public Update(JSONObject obj) {
        update_id = obj.has("update_id") ? obj.getLong("update_id") : null;
        message = obj.has("message") ? new Message(obj.getJSONObject("message")) : null;
        callbackQuery = obj.has("callback_query") ? new CallbackQuery(obj.getJSONObject("callback_query")) : null;

    }

    @Override
    public String toString() {
        return "update_id: " + update_id + "\n"
                + "message: " + message.toString() + "\n"
                + "callbackQuery: " + callbackQuery.toString();
    }
}
