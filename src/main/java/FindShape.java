import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class FindShape {
    private VideoCapture camera;
    private MyFrame myFrame;
    private Mat firstImage = new Mat();
    private Mat secondImage = new Mat();
    private Mat foregroundImage = new Mat();
    private Mat end = new Mat();
    private MatOfPoint contour;
    private List<MatOfPoint> contours;

    public FindShape(VideoCapture camera){
        this.camera=camera;
        this.myFrame = new MyFrame(camera);
    }

    Mat find(){
        this.firstImage = myFrame.imageAsBW();
        this.secondImage = myFrame.imageAsBW();
        Core.subtract(firstImage, secondImage, foregroundImage);
        Imgproc.adaptiveThreshold(foregroundImage, end, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 6);


        return foregroundImage;
    }


    MatOfPoint getContour(Mat foregroundImage){
        Imgproc.findContours(foregroundImage, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i=0; i<contours.size(); i++){

        }

        return contour;
    }
}
