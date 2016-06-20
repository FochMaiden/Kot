import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import static java.lang.StrictMath.abs;

public class FindShape {
    private VideoCapture camera;
    private MyFrame myFrame;
    private Mat firstImage = new Mat();
    private Mat secondImage = new Mat();
    private Mat foregroundImage = new Mat();
    private Mat end = new Mat();
    private MatOfPoint contour;
    private List<MatOfPoint> contours;
    private double max=0;
    private int i_max=0;

    public FindShape(VideoCapture camera){
        this.camera=camera;
        this.myFrame = new MyFrame(camera);
    }

    Mat find(){
        this.firstImage = myFrame.imageAsBW();
        this.secondImage = myFrame.imageAsBW();
        Core.subtract(firstImage, secondImage, foregroundImage);
        Imgproc.adaptiveThreshold(foregroundImage, end, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 6);
        getContour(foregroundImage);

        return foregroundImage;
    }


    MatOfPoint getContour(Mat foregroundImage){
        Imgproc.findContours(foregroundImage, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i=0; i<contours.size(); i++){
            if (abs(Imgproc.contourArea(contours.get(i))) > max ){
                max = abs(Imgproc.contourArea(contours.get(i)));
                i_max = i;
            }
        }


        MatOfPoint2f thisContour2f = new MatOfPoint2f(); //konwersacja starej tablicy konturów
        MatOfPoint approxContour = new MatOfPoint();
        MatOfPoint2f approxContour2f = new MatOfPoint2f(); //po konwersacji
        if(i_max>=0){
            contours.get(i_max).convertTo(thisContour2f, CvType.CV_32FC2); //conversja matofpoint -> matofpoint2f
        }
            Imgproc.approxPolyDP(thisContour2f,approxContour2f,3, true ); //oszacowanie konturów
            approxContour2f.convertTo(approxContour, CvType.CV_32S); //conversja matofpoint2f -> matofpoint
        Rect boundRect = Imgproc.boundingRect(approxContour); //przypisanie prostokąta otaczającego contur
        Core.rectangle(foregroundImage, boundRect.tl(), boundRect.br(), new Scalar(125, 250, 125), 2, 8, 0); //wyrysowanie prostokata
            Core.line(foregroundImage, boundRect.tl(), boundRect.br(), new Scalar(250, 125, 125), 2, 8, 0); // X wyrysowany
            Core.line(foregroundImage, new Point(boundRect.x + boundRect.width, boundRect.y), new Point(boundRect.x, boundRect.y+boundRect.height), new Scalar(250, 125, 125), 2, 8, 0 );
            Imgproc.drawContours(foregroundImage, contours, i_max, new Scalar(255,0 , 0), 2); // kontury z listy rysowane na img

            return contour;
    }
}
