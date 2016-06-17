import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CatDetection {
    private Mat img, hsv_img, binary;
    private Mat cont;
    private List<MatOfPoint> contours;



    CatDetection(Mat frame){
        this.img = frame;
        this.hsv_img = new Mat();
        this.binary = new Mat();
        this.cont = new Mat();
        contours = new ArrayList<MatOfPoint>();
    }

    public Mat detect(){
        if(img.empty()){
            System.err.print("ERROR. Frame is empty. Try again.");
            detect();
        }
        Imgproc.cvtColor(img, hsv_img, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv_img,new Scalar(90, 90, 90),new Scalar(140, 140, 140),binary);
        Imgproc.blur(binary,binary, new Size(5,5));

        Imgproc.erode(binary,binary,new Mat());
       // Imgproc.dilate(binary, binary, new Mat());

        Rect boundRect;
        binary.copyTo(cont);
        Imgproc.findContours(cont, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);

        double max =0;
        int i_cont = -1;
        int i;

        for( i =0; i< contours.size(); i++){
            if (abs(Imgproc.contourArea(contours.get(i))) > max ){
                max = abs(Imgproc.contourArea(contours.get(i)));
                i_cont = i;
            }
        }

        MatOfPoint2f thisContour2f = new MatOfPoint2f();
        MatOfPoint approxContour = new MatOfPoint();
        MatOfPoint2f approxContour2f = new MatOfPoint2f();
        if(i_cont>=0){
            contours.get(i_cont).convertTo(thisContour2f, CvType.CV_32FC2);
            Imgproc.approxPolyDP(thisContour2f,approxContour2f,3, true );
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            boundRect = Imgproc.boundingRect(approxContour);
            Core.fillConvexPoly(img,approxContour,new Scalar(0,255,0));
            Core.rectangle(img, boundRect.tl(), boundRect.br(), new Scalar(125, 250, 125), 2, 8, 0);
            Core.line(img, boundRect.tl(), boundRect.br(), new Scalar(250, 125, 125), 2, 8, 0);
            Core.line(img, new Point(boundRect.x + boundRect.width, boundRect.y), new Point(boundRect.x, boundRect.y+boundRect.height), new Scalar(250, 125, 125), 2, 8, 0 );
            for(int j =0; j< contours.size(); j++)
                Imgproc.drawContours(img, contours, j, new Scalar(255,0 , 0), 2);

        }
        return img;
    }

}
