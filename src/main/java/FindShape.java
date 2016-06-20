import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class FindShape {
    private VideoCapture camera;
    private MyFrame myFrame;
    private Mat firstImage = new Mat();
    private Mat secondImage = new Mat();
    private Mat foregroundImage = new Mat();
    private Mat end = new Mat();
    private MatOfPoint contour;

    public FindShape(VideoCapture camera){
        this.camera=camera;
        this.myFrame = new MyFrame(camera);
    }

    Mat find(){
        this.secondImage = myFrame.imageAsBW();
        Core.subtract(secondImage, firstImage, foregroundImage);
        Imgproc.threshold(foregroundImage, end, 50,255, Imgproc.THRESH_BINARY);


        return end;
    }

    void getFirstimage(){
        this.firstImage = myFrame.imageAsBW();
    }

}
