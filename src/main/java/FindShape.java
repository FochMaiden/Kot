import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class FindShape {
    private VideoCapture camera;
    private MyFrame myFrame;
    private Mat firstImage;
    private Mat secondImage;
    private Mat foregroundImage;
    private Mat end;
    private MatOfPoint contour;

    public FindShape(VideoCapture camera){
        this.camera=camera;
        this.myFrame = new MyFrame(camera);
        if(firstImage.empty()) {
            this.firstImage = myFrame.imageAsBW();
        }
        else{
            this.secondImage = firstImage;
            this.firstImage = myFrame.imageAsBW();
        }

    }

    Mat find(){
        Core.subtract(firstImage, secondImage, foregroundImage);
        Imgproc.adaptiveThreshold(foregroundImage, end, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2);


        return end;
    }

}
