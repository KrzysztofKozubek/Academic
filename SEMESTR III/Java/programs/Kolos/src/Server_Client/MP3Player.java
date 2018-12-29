package Server_Client;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player {

    public MP3Player(String fileMp3) {
        play(fileMp3);
    }

    public static void play(String fileMp3) {

        try {

            File file = new File(fileMp3);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(inputStream);

            try {

                Player player = new Player(bis);
                player.play();
            } catch (JavaLayerException e) {
            }
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        MP3Player mp3 = new MP3Player("sms.mp3");
    }


}
