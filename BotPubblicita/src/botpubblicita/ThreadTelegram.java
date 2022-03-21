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
    private OpenStreetMapAPI OsmAPI;

    public ThreadTelegram(String token, MapQuestAPI mapQuest, MyFile fileUsers) {
        try {
            this.api = new TelegramAPI(token);
            this.fileUsers = fileUsers;
            this.userList = new UserList(fileUsers);
            this.mapQuest = mapQuest;
            this.OsmAPI = new OpenStreetMapAPI();

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
                if (!updates.isEmpty()) {
                    long lastOffset = updates.get(updates.size() - 1).update_id;
                    api.changeOffset(lastOffset + 1);
                    for (int i = 0; i < updates.size(); i++) {
                        Update update = updates.get(i);
                        try {
                            if (update.message != null) {
                                readMessages(update.message);
                            } else if (update.callbackQuery != null) {
                                readCallbackQuery(update.callbackQuery);
                            }
                        } catch (Exception e) {
                            //Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println(e);
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
        String text = message.text;
        Chat chat = message.chat;
        if (text.startsWith("/")) {
            int firstSpace = text.indexOf(" ");
            if (firstSpace != -1) {
                String cmd = text.substring(1, firstSpace);
                switch (cmd) {
                    case "citta":
                        text = text.substring(firstSpace + 1, text.length());
                        System.out.println("text: " + text);
                        try {
                            SearchResults sr = OsmAPI.searchPlace(text);
                            if (sr.places != null) {
                                if (!userList.userExists(message.chat)) {
                                    //ADD NEW USER
                                    Place place = sr.places.get(0);
                                    userList.addUser(message.chat, text, place);
                                    ReplyMarkup[] buttons;
                                    if (sr.places.size() > 1) {
                                        ReplyMarkup[] temp = {
                                            ReplyMarkup.getButton("Conferma", "conf"),
                                            ReplyMarkup.getButton("Successivo", "succ")
                                        };
                                        buttons = temp;
                                    } else {
                                        ReplyMarkup[] temp = {
                                            ReplyMarkup.getButton("Conferma", "conf")
                                        };
                                        buttons = temp;
                                    }
                                    User u = userList.getUserByChatID(message.chat.id);
                                    String msg = "Risultato " + (u.getnLoc() + 1) + " di " + sr.places.size() + " risultati trovati";
                                    URL photo = mapQuest.getImage(place.getLat(), place.getLon());
                                    api.sendPhotoReplyMarkup(message.chat, photo.toString(), msg, buttons);
                                } else {
                                    //EDIT EXISTING USER
                                    Place place = sr.places.get(0);
                                    userList.updateUser(message.chat, text, place, 0);
                                    ReplyMarkup[] buttons;
                                    if (sr.places.size() > 1) {
                                        ReplyMarkup[] temp = {
                                            ReplyMarkup.getButton("Conferma", "conf"),
                                            ReplyMarkup.getButton("Successivo", "succ")
                                        };
                                        buttons = temp;
                                    } else {
                                        ReplyMarkup[] temp = {
                                            ReplyMarkup.getButton("Conferma", "conf")
                                        };
                                        buttons = temp;
                                    }
                                    User u = userList.getUserByChatID(message.chat.id);
                                    String msg = "Risultato " + (u.getnLoc() + 1) + " di " + sr.places.size() + " risultati trovati";
                                    URL photo = mapQuest.getImage(place.getLat(), place.getLon());
                                    api.sendPhotoReplyMarkup(message.chat, photo.toString(), msg, buttons);

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

    private void readCallbackQuery(CallbackQuery query) {
        User u = userList.getUserByChatID(query.message.chat.id);
        int nPos = u.getnLoc();
        try {
            if (query.data.equals("conf")) {
                if (userList.userExists(query.message.chat)) {
                    api.sendMessage(query.message.chat, "Posizione salvata, utente modificato");
                } else {
                    api.sendMessage(query.message.chat, "Posizione salvata, utente registrato");
                }
            } else {
                SearchResults sr = OsmAPI.searchPlace(u.getPlaceName());
                if (sr.places != null) {
                    if (query.data.equals("succ")) {
                        nPos++;
                    }
                    if (query.data.equals("prec")) {
                        nPos--;
                    }
                    if (nPos >= 0 && nPos < sr.places.size()) {
                        Place place = sr.places.get(nPos);
                        String msg = "Risultato " + (nPos + 1) + " di " + sr.places.size() + " risultati trovati";
                        URL photo = mapQuest.getImage(place.getLat(), place.getLon());
                        ReplyMarkup[] buttons;
                        if (nPos == 0) {
                            ReplyMarkup[] temp = {
                                ReplyMarkup.getButton("Conferma", "conf"),
                                ReplyMarkup.getButton("Successivo", "succ")
                            };
                            buttons = temp;
                        } else if (nPos == sr.places.size() - 1) {
                            ReplyMarkup[] temp = {
                                ReplyMarkup.getButton("Precendente", "prec"),
                                ReplyMarkup.getButton("Conferma", "conf")
                            };
                            buttons = temp;
                        } else {
                            ReplyMarkup[] temp = {
                                ReplyMarkup.getButton("Precendente", "prec"),
                                ReplyMarkup.getButton("Conferma", "conf"),
                                ReplyMarkup.getButton("Successivo", "succ")
                            };
                            buttons = temp;
                        }
                        u.setnLoc(nPos);
                        u.setLat(Double.valueOf(place.getLat()));
                        u.setLon(Double.valueOf(place.getLon()));
                        userList.updateUser(u);
                        api.sendPhotoReplyMarkup(query.message.chat, photo.toString(), msg, buttons);
                    }
                }
            }
            api.deleteMessage(query.message.chat, query.message.message_id);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized UserList getUserList() {
        return userList;
    }

    public void sendPubblicita(long id, String text) {
        try {
            api.sendMessage(id, text);
        } catch (IOException ex) {
            Logger.getLogger(ThreadTelegram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
