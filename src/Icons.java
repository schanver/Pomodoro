package src;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Icons 
{
    public ImageIcon setIcon(String filePath, int width, int height )
    {
        ImageIcon imageIcon = new ImageIcon(filePath); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        return imageIcon;
    }
}

