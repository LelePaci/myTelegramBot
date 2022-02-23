package mytelegramapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        String jsonString = getStream("getUpdates");
        JSONArray result = new JSONObject(jsonString).getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            upds.add(new Update(result.getJSONObject(i)));
        }
        return upds;
    }
    
    public Update getFirstUpdate() throws IOException {
        Update u = getUpdates().get(0);
        int newOffset = u.update_id + 1;
        getStream("getUpdates?offset=" + newOffset);
        return u;
    }
    
    public int getUpdatesLenght() throws IOException{
        return getUpdates().size();
    }
    
    public Chat getChatByID(int id) throws IOException{
        String jsonString = getStream("getChat?chat_id="+ id);
        return new Chat(new JSONObject(jsonString).getJSONObject("result"));
    }
    
    public void sendMessage(Chat chat, String text) throws IOException{
        String reply = text.replaceAll("\\s+", "%20");
        getStream("sendMessage?chat_id=" + chat.id + "&text="+ reply);
    }
}
