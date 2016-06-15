import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;

public class Main  extends JFrame{


    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);

        camera.open(0);
        if(!camera.isOpened()){
            System.out.print("Camera error");
        }

    }

}
