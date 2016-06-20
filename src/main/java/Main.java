import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
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

    public static BufferedImage Mat2bufferedImage(Mat image) {
        MatOfByte bytemat = new MatOfByte();
        Highgui.imencode(".jpg", image, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void main(String[] args) {
        JFrame jframe = new JFrame("HUMAN MOTION DETECTOR FPS");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(640, 480);
        jframe.setVisible(true);

        Mat imageToDisplay = new Mat();



        VideoCapture camera = new VideoCapture(0);
        while(true){
            camera.read(imageToDisplay);
            ImageIcon image = new ImageIcon(Mat2bufferedImage(imageToDisplay));
            vidpanel.setIcon(image);
            vidpanel.repaint();
        }
    }
}

