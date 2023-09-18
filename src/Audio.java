package src;

import java.io.File;
import java.io.IOException;
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

public class Audio {

    Clip clip, rainClip, ringClip;
   

    String song_Name = "";
    boolean rainButtonPressed = false;
    boolean audioStartButtonPressed = false;
    int previousSongIndex, indexSlash;
    long pausedPosition;

    ArrayList<String> tunes = new ArrayList<>();

    Random random = new Random();
    Icons icons = new Icons();
    App app;

    

   public Audio(App app, TimerManager tm)
    {
    this.app = app;
    tunes.add("resources/audio/KNB.wav");
    
        
    }
    /*public void stopAudio()
    {
        if( clip!= null && clip.isRunning())
        {
            clip.stop();
        }
    }*/

    public void playAudio()  //TODO fix the bugs
    {
        
        if( clip != null && clip.isRunning())
        {
            audioStartButtonPressed = !audioStartButtonPressed;
            if( audioStartButtonPressed)
            {   
                pausedPosition = clip.getMicrosecondPosition();
                clip.stop();
            }
            else
            {
                clip.setMicrosecondPosition(pausedPosition);
                clip.start();
            }
            
        }
        else 
        {
            // Choose a random index except the first two, which are the rain sound and the ringtone.
            int index = 0;//random.nextInt(tunes.size() - 2) + 2;
            String songFilePath = tunes.get(index);
            indexSlash = songFilePath.lastIndexOf("/");
            song_Name = (indexSlash > 0)? songFilePath.substring(indexSlash + 1) : songFilePath;
            app.songName.setText(song_Name);
            
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
                int index = 0;
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
