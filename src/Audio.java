package src;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio 
{   
    ArrayList<String> tunes = new ArrayList<>();
    Clip clip, rainClip, ringClip;
    Random random = new Random();
    String song_Name = "0-lofi_song.wav";
    boolean rainButtonPressed = false;
    boolean audioStartButtonPressed = false;

    private int previousSongIndex;


    public void playAudio()
    {
        
        if( clip != null && clip.isRunning())
        {
            audioStartButtonPressed = !audioStartButtonPressed;
            if(audioStartButtonPressed)
            {
                clip.stop();
            }
            else
            {
                clip.start();
            }
        }
        else 
        {
            // Choose a random index except the first two, which are the rain sound and the ringtone.
            int index = random.nextInt(tunes.size() - 2) + 2;
            String songFilePath = tunes.get(index);
            try 
            {
                
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songFilePath));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clip.addLineListener(new LineListener() { 
                    @Override
                    public void update(LineEvent event) { // Choose a new Song to play if the song ends.
                        if (event.getType() == LineEvent.Type.STOP) {
                            previousSongIndex = index;
                            nextSong();
                        }
                    }
                });
            
        }   catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
        }
        }
    }
    public void ringTone(int index)
    {
        
        try {
            
            String filePath = tunes.get(index);
            File audioFile = new File(filePath);
            URL url = audioFile.toURI().toURL();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            ringClip = AudioSystem.getClip();
            ringClip.open(audioInputStream);
            ringClip.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void startRain()
    {
        if(rainClip != null && rainClip.isRunning())
        {
            rainButtonPressed = !rainButtonPressed;
            if( rainButtonPressed )
            {
                rainClip.stop();
            }
            else
            {
                rainClip.start();
            }
        }
        else 
        {
            try 
            {
                int index = 1;
                String filePath = tunes.get(index);
                File audioFile = new File(filePath);
                URL url = audioFile.toURI().toURL();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                rainClip = AudioSystem.getClip();
                rainClip.open(audioInputStream);
                rainClip.start();
            
        }   catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
        }
        }
    }

    

    public void nextSong()
    {
        int index = random.nextInt(tunes.size() - 2) + 2;
            if(previousSongIndex == index)
            {
                index = random.nextInt(tunes.size() - 2) + 2;
            }
            else
            {
                previousSongIndex = index;
                
            }
        
        try {
            String filePath = tunes.get(index);
            File audioFile = new File(filePath);
            URL url = audioFile.toURI().toURL();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream); 
            clip.start();

            
        } catch (UnsupportedAudioFileException | IOException | SecurityException | LineUnavailableException e) {
            e.printStackTrace();
        }
        

            
    }   
    
}
