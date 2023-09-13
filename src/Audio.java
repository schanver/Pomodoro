package src;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio 
{   
    ArrayList<String> tunes = new ArrayList<>();
    Random random = new Random();

    public void playAudio()
    {
        try 
        {
            int index = random.nextInt(tunes.size() - 2) + 1;
            String filePath = tunes.get(index);
            File audioFile = new File(filePath);
            URL url = audioFile.toURI().toURL();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
             
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    public void ringTone()
    {
        try {
            int index = 0;
            String filePath = tunes.get(0);
            File audioFile = new File(filePath);
            URL url = audioFile.toURI().toURL();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
