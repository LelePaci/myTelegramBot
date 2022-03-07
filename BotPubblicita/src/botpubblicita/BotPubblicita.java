/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botpubblicita;

import api.telegram.Chat;
import api.telegram.TelegramAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paci_emanuele
 */
public class BotPubblicita {

    public static void main(String[] args) {

        ThreadTelegram telegram;
        try {
            telegram = new ThreadTelegram(new Scanner(new File("token.txt")).next());
            telegram.start();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}