import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        JFrame jframe = new JFrame("HUMAN MOTION DETECTOR FPS");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(640, 480);
        jframe.setVisible(true);

        Mat imageToDisplay = new Mat();

        VideoCapture camera = new VideoCapture(1);
        FindShape findShape = new FindShape(camera);
        BufferedImageFromMat imageDisplayed = new BufferedImageFromMat();
        findShape.getFirstimage();

    int x=0;
        while(true){
           // while(x<3) {
                imageToDisplay = findShape.find();
                ImageIcon image = new ImageIcon(imageDisplayed.getBufferedImageFromMat(imageToDisplay));
                vidpanel.setIcon(image);
                vidpanel.repaint();
                x++;
           // }
           // x=0;
        }
    }
}

