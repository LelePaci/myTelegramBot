/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.utils;

import api.openstreetmap.Place;
import api.telegram.Chat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paci_emanuele
 */
public class UserList {

    private List<User> users;
    private MyFile file;

    public UserList(MyFile file) throws IOException {
        this.users = new ArrayList();
        this.file = file;
        users = new ArrayList();
        loadUsers();
    }

    private void loadUsers() throws IOException {
        String[] lines = file.read();
        users.clear();
        for (int i = 0; i < lines.length; i++) {
            users.add(new User(lines[i]));
        }
    }

    private void saveList() {

    }

    public boolean userExists(Chat chat) throws IOException {
        String[] lines = file.read();
        for (int i = 0; i < lines.length; i++) {
//            System.out.println(lines[i]);
            Long id = Long.valueOf(lines[i].substring(0, lines[i].indexOf(";")));
            if (id.equals(chat.id)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(Chat chat, Place place) throws IOException {
        User u = new User(chat.id, chat.first_name, Double.valueOf(place.getLat()), Double.valueOf(place.getLon()));
        users.add(u);
        file.append(u.toCSV());
    }

    public void updateUser(Chat chat, Place place) throws IOException {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getChatID() == chat.id) {
                users.set(i, new User(chat.id, chat.first_name, Double.valueOf(place.getLat()), Double.valueOf(place.getLon())));
            }
        }

        String[] toWrite = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            toWrite[i] = users.get(i).toCSV();
        }
        file.write(toWrite);
    }
}
