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
public class Me {

    public Integer id;
    public Boolean is_bot;
    public String first_name;
    public String username;
    public Boolean can_join_groups;
    public Boolean can_read_all_group_messages;
    public Boolean supports_inline_queries;

    public Me(JSONObject obj) {
        id = obj.has("id") ? obj.getInt("id") : null;
        is_bot = obj.has("is_bot") ? obj.getBoolean("is_bot") : null;
        first_name = obj.has("first_name") ? obj.getString("first_name") : null;
        username = obj.has("username") ? obj.getString("username") : null;
        can_join_groups = obj.has("can_join_groups") ? obj.getBoolean("can_join_groups") : null;
        can_read_all_group_messages = obj.has("can_read_all_group_messages") ? obj.getBoolean("can_read_all_group_messages") : null;
        supports_inline_queries = obj.has("supports_inline_queries") ? obj.getBoolean("supports_inline_queries") : null;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n"
                + "is_bot: " + is_bot + "\n"
                + "first_name: " + first_name + "\n"
                + "username: " + username + "\n"
                + "can_join_groups: " + can_join_groups + "\n"
                + "can_read_all_group_messages: " + can_read_all_group_messages + "\n"
                + "supports_inline_queries: " + supports_inline_queries;
    }
}
