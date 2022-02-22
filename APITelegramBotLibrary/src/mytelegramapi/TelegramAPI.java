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
    private String baseURL;

    public TelegramAPI(String token) throws IOException {
        this.baseURL = "https://api.telegram.org/bot";
        this.token = token;
    }

    private String getStream(String method) throws MalformedURLException, IOException {
        URL url = new URL(baseURL + token + "/" + method);
        this.scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\u001a");
        return scanner.next();
    }

    public List<Update> getUpdates() throws IOException {
        List<Update> upds = new ArrayList();
        String jsonString = getStream("getUpdates");
        JSONObject obj = new JSONObject(jsonString);
        JSONArray result = obj.getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            upds.add(new Update(result.getJSONObject(i).getJSONObject("message")));
        }
        return upds;
    }
}
