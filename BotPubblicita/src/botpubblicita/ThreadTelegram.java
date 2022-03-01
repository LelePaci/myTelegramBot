/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpubblicita;

import api.openstreetmap.OpenStreetMapAPI;
import api.openstreetmap.SearchResults;
import api.telegram.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.directory.SearchResult;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author paci_emanuele
 */
public class ThreadTelegram extends Thread {

    private TelegramAPI api = null;
    private boolean running = false;

    public ThreadTelegram(String token) {
        try {
            api = new TelegramAPI(token);
            running = true;
        } catch (IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                List<Update> updates = api.getUpdates();
                if (updates.size() > 0) {
                    long lastOffset = updates.get(updates.size() - 1).update_id;
                    api.changeOffset(lastOffset + 1);
                    for (int i = 0; i < updates.size(); i++) {
                        doSomething(updates.get(i).message);
                    }
                }

                Thread.sleep(100);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void doSomething(Message message) {

        String text = message.text;

        if (text.startsWith("/")) {
            int firstSpace = text.indexOf(" ");
            if (firstSpace != -1) {
                OpenStreetMapAPI map = new OpenStreetMapAPI();
                text = text.substring(firstSpace + 1, text.length());
                try {
                    SearchResults sr =map.searchPlace(text);
                    for (int i = 0; i < sr.places.size(); i++) {
                         api.sendMessage(message.chat, sr.places.get(i).toString());
                    }
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    api.sendMessage(message.chat, "Inserisci una località");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
