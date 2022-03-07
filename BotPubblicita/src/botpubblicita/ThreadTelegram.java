/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpubblicita;

import api.openstreetmap.OpenStreetMapAPI;
import api.openstreetmap.Place;
import api.openstreetmap.SearchResults;
import api.telegram.*;
import api.utils.MyFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author paci_emanuele
 */
public class ThreadTelegram extends Thread {

    private TelegramAPI api = null;
    private boolean running = false;
    private MyFile fileUsers;

    public ThreadTelegram(String token) {
        try {
            api = new TelegramAPI(token);
            running = true;
            fileUsers = new MyFile("users.csv");
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
                        if (updates.get(i).message.text != null) {
                            readMessages(updates.get(i).message);
                        }

                    }
                }

                Thread.sleep(100);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void readMessages(Message message) {

        String text = message.text;

        if (text.startsWith("/")) {
            int firstSpace = text.indexOf(" ");
            if (firstSpace != -1) {
                String cmd = text.substring(1, firstSpace);
                switch (cmd) {
                    case "citta":
                        OpenStreetMapAPI map = new OpenStreetMapAPI();
                        text = text.substring(firstSpace + 1, text.length());
                        System.out.println("text: " + text);
                        try {
                            SearchResults sr = map.searchPlace(text);
                            if (sr.places != null) {
                                if (!recordExists(message.chat)) {
                                    saveRecord(message.chat, sr.places.get(0));
                                    api.sendMessage(message.chat, "Utente registrato");
                                } else {
                                    updateRecord(message.chat, sr.places.get(0));
                                    api.sendMessage(message.chat, "Utente modificato");
                                }
                            } else {
                                api.sendMessage(message.chat, "Nessun risultato trovato");
                            }

                        } catch (ParserConfigurationException | SAXException | IOException ex) {
                            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
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

    private boolean recordExists(Chat chat) {
        try {
            String[] lines = fileUsers.read();
            for (int i = 0; i < lines.length; i++) {
                int id = Integer.parseInt(lines[i].substring(0, lines[i].indexOf(";")));
                if (id == chat.id) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void saveRecord(Chat chat, Place place) {
        String user = MyFile.getCSV(chat.id.toString(), chat.first_name, place.getLat(), place.getLon());
        try {
            fileUsers.append(user);
        } catch (IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateRecord(Chat chat, Place place) {
        try {
            String[] lines = fileUsers.read();
            for (int i = 0; i < lines.length; i++) {
                int id = Integer.parseInt(lines[i].substring(0, lines[i].indexOf(";")));
                if (id == chat.id) {
                    lines[i] = MyFile.getCSV(chat.id.toString(), chat.first_name, place.getLat(), place.getLon());
                    break;
                }
            }
            fileUsers.write(lines);
        } catch (IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
