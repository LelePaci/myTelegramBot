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

    public void getMe() throws IOException {
        String jsonString = getStream("getMe");
        System.out.println(jsonString);
    }

    public List<Update> getUpdates() throws IOException {
        List<Update> upds = new ArrayList();
        String jsonString = getStream("getUpdates");
        JSONObject obj = new JSONObject(jsonString);
        JSONArray result = obj.getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            //System.out.println(result.getJSONObject(i));
            upds.add(new Update(result.getJSONObject(i)));
        }
        return upds;
    }

    public Message getFirstUpdate() throws IOException {
        //Message u = getUpdates().get(0);
        //int newOffset = u.update_id + 1;
        //getStream("getUpdates?offset=" + newOffset);
        return null;
    }

}
