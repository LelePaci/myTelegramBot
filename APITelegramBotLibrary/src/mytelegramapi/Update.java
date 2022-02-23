/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mytelegramapi;

import org.json.JSONObject;

/**
 *
 * @author pacie
 */
public class Update {

    public Integer update_id;
    public Message message;

    public Update(JSONObject obj) {
        update_id = obj.has("update_id") ? obj.getInt("update_id") : null;
        message = obj.has("message") ? new Message(obj.getJSONObject("message")) : null;
    }

    @Override
    public String toString() {
        return "update_id: " + update_id + "\n"
                + "message: " + message.toString();
    }
}
