import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;


public class MyFrame {

    VideoCapture camera;
    Mat image = new Mat(),mathelp;

    public MyFrame(VideoCapture camera){
        this.camera = camera;
    }

    Mat imageAsBW(){
        camera.read(image);
        mathelp = new Mat(image.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(image,mathelp,Imgproc.COLOR_RGB2GRAY);
        return mathelp;
    }

    Mat imageAsHSV(){
        camera.read(image);
        mathelp = new Mat();
        Imgproc.cvtColor(image, mathelp, Imgproc.COLOR_RGB2HSV);
        return mathelp;
    }

    Mat imageAsRGB(){
        camera.read(image);
        return image;
    }


}
