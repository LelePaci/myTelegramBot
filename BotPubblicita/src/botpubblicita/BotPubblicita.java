/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpubblicita;

import api.mapquest.MapQuestAPI;
import api.utils.MyFile;
import api.utils.Tokens;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paci_emanuele
 */
public class BotPubblicita {

    public static void main(String[] args) {

        Tokens tokens;
        MapQuestAPI mapQuest;
        MyFile fileUsers;
        ThreadTelegram telegram;

        try {
            tokens = new Tokens();
            mapQuest = new MapQuestAPI(tokens.getMapQuest());
            fileUsers = new MyFile("users.csv");
            if (!fileUsers.exists()) {
                fileUsers.createFile();
            }
            telegram = new ThreadTelegram(tokens.getTelegram(), mapQuest, fileUsers);
            telegram.start();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
