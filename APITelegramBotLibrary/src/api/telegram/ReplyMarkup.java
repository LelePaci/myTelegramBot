/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.telegram;

import java.io.StringWriter;
import org.json.JSONWriter;

/**
 *
 * @author pacie
 */
public class ReplyMarkup {

    private final String text;
    private final String callbackData;

    public ReplyMarkup(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

    public static ReplyMarkup getButton(String text, String callbackData) {
        return new ReplyMarkup(text, callbackData);
    }

    public static String getJSONReplyMarkup(String type, ReplyMarkup[] buttons) {
        StringWriter sw = new StringWriter();
        JSONWriter writer = new JSONWriter(sw);
        //inline_keyboard
        writer.object().key(type).array().array();
        for (ReplyMarkup button : buttons) {
            writer.object().key("text").value(button.text).key("callback_data").value(button.callbackData).endObject();
        }
        writer.endArray().endArray().endObject();

        return sw.toString();
    }
}
