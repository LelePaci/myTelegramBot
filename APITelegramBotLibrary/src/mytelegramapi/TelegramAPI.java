package mytelegramapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

        getUpdates();

        //System.out.println(jsonString);
    }

    private String getStream(String method) throws MalformedURLException, IOException {
        URL url = new URL(baseURL + token + "/" + method);
        this.scanner = new Scanner(url.openStream());
        scanner.useDelimiter("\u001a");
        return scanner.next();
    }

    public Update getUpdates() throws IOException {
        
        String jsonString = getStream("getUpdates");
        JSONObject obj = new JSONObject(jsonString);
        JSONArray result = obj.getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            System.out.println(result.getJSONObject(i).getJSONObject("message"));
            Update m = new Update(result.getJSONObject(i).getJSONObject("message"));
        }
        
        return null;
    }
}
