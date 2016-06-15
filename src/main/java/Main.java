import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

public class Main {
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);


    }

}
