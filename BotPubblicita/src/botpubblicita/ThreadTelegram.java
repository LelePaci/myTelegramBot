/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpubblicita;

import api.openstreetmap.OpenStreetMapAPI;
import api.openstreetmap.SearchResults;
import api.telegram.*;
import api.utils.*;
import api.mapquest.*;
import api.openstreetmap.*;
import java.io.IOException;
import java.net.URL;
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
    private UserList userList;
    private MapQuestAPI mapQuest;

    public ThreadTelegram(String token, MapQuestAPI mapQuest, MyFile fileUsers) {
        try {
            this.api = new TelegramAPI(token);
            this.fileUsers = fileUsers;
            this.userList = new UserList(fileUsers);
            this.mapQuest = mapQuest;

            this.running = true;
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
                        Update update = updates.get(i);
                        try {
                            if (update.message != null) {
                                readMessages(update.message);
                            } else if (update.callbackQuery != null) {
                                System.out.println(update.callbackQuery.data);
                            }
                        } catch (Exception e) {
                            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, e);
                            //System.out.println(e);
                            String errorMessage = "Qualcosa è andato storto, riprova";
                            if (update.message != null) {
                                api.sendMessage(update.message.chat, errorMessage);
                            } else if (update.callbackQuery != null) {
                                api.sendMessage(update.callbackQuery.message.chat, errorMessage);
                            }
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
//        try {
//            ReplyMarkup[] buttons = {
//                ReplyMarkup.getButton("Precedente", "prec"),
//                ReplyMarkup.getButton("Conferma", "conf"),
//                ReplyMarkup.getButton("Successivo", "succ")
//            };
//            api.sendMessageReplyMarkup(message.chat, "prova", buttons);
//        } catch (IOException ex) {
//            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String text = message.text;
        Chat chat = message.chat;
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
                                Place place = sr.places.get(0);
                                if (!userList.userExists(message.chat)) {
                                    userList.addUser(message.chat, place);
                                    mapQuest.getImage(place.getLat(), place.getLon());
                                    api.sendMessage(message.chat, "Utente registrato");
                                } else {
                                    userList.updateUser(message.chat, sr.places.get(0), 1);
                                    api.sendMessage(message.chat, "Utente modificato");
                                    URL photo = mapQuest.getImage(place.getLat(), place.getLon());
                                    //api.sendPhoto(message.chat, photo.toString(), "Risultato trovato");
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
                    api.sendMessage(message.chat, "Comando non valido");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
