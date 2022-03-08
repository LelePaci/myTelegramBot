package api.telegram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.*;

/**
 *
 * @author paci_emanuele
 */
public class TelegramAPI {

    private String token;
    private Scanner scanner;
    private String telegramURL;
    private String baseURL;

    private long offset = 0;

    public TelegramAPI(String token) throws IOException {
        this.telegramURL = "https://api.telegram.org/bot";
        this.token = token;
        this.baseURL = telegramURL + token + "/";
    }

    private String getStream(String method) throws MalformedURLException, IOException {
        URL url = new URL(baseURL + method);
        this.scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\u001a");
        return scanner.next();
    }

    public String getURL() {
        return baseURL;
    }

    public Me getMe() throws IOException {
        String jsonString = getStream("getMe");
        return new Me(new JSONObject(jsonString).getJSONObject("result"));
    }

    public List<Update> getUpdates() throws IOException {
        List<Update> upds = new ArrayList();
        String jsonString = getStream("getUpdates?offset=" + offset);
        JSONArray result = new JSONObject(jsonString).getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            upds.add(new Update(result.getJSONObject(i)));
        }
        return upds;
    }

    public void changeOffset(long offset) throws IOException {
        //getStream("getUpdates?offset=" + offset);
        this.offset = offset;
    }

    public Chat getChatByID(int id) throws IOException {
        String jsonString = getStream("getChat?chat_id=" + id);
        return new Chat(new JSONObject(jsonString).getJSONObject("result"));
    }

    public void sendMessage(Chat chat, String text) throws IOException {
        getStream("sendMessage?chat_id=" + chat.id + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8));
    }

    public void sendPhoto(Chat chat, String photo, String caption) throws IOException {
        getStream("sendPhoto?chat_id=" + chat.id + "&photo=" + URLEncoder.encode(photo, StandardCharsets.UTF_8) + "&caption=" + URLEncoder.encode(caption, StandardCharsets.UTF_8));
    }
}
