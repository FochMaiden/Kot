import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CatDetection {
    private Mat frame, img, hsv_img, binary;
    private Mat cont;
    private List<MatOfPoint> contours;
    private List<Mat> end;


    CatDetection(Mat frame){
        this.frame = frame;
        this.img = new Mat();
        this.hsv_img = new Mat();
        this.binary = new Mat();
        this.cont = new Mat();
        contours = new ArrayList<MatOfPoint>();
        end = new ArrayList<Mat>();
    }

    public List<Mat> detect(){
        frame.copyTo(img);
        Imgproc.cvtColor(img, hsv_img, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv_img,new Scalar(100, 100, 100),new Scalar(109, 109, 109),binary);
        Imgproc.blur(binary,binary, new Size(3,3));
        Imgproc.erode(binary,binary,new Mat());
        Rect boundRect;
        binary.copyTo(cont);
        Imgproc.findContours(cont, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));

        double max =0;
        int i_cont = -1;
        int i;
        Mat drawing = new Mat();
        drawing = drawing.zeros(cont.size(), CvType.CV_8UC3);
        for( i =0; i< contours.size(); i++){
            if (abs(Imgproc.contourArea(contours.get(i))) > max ){
                max = abs(Imgproc.contourArea(contours.get(i)));
                i_cont = i;
            }
        }

        MatOfPoint2f thisContour2f = new MatOfPoint2f();
        MatOfPoint approxContour = new MatOfPoint();
        MatOfPoint2f approxContour2f = new MatOfPoint2f();
        contours.get(i_cont).convertTo(thisContour2f, CvType.CV_32FC2);
        if(i_cont>=0){
            Imgproc.approxPolyDP(thisContour2f,approxContour2f,3, true );
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            boundRect = Imgproc.boundingRect(approxContour);
            Core.fillConvexPoly(img,approxContour,new Scalar(0,255,0));
            Core.rectangle(img, boundRect.tl(), boundRect.br(), new Scalar(125, 250, 125), 2, 8, 0);
            Core.line(img, boundRect.tl(), boundRect.br(), new Scalar(250, 125, 125), 2, 8, 0);
            Core.line(img, new Point(boundRect.x + boundRect.width, boundRect.y), new Point(boundRect.x, boundRect.y+boundRect.height), new Scalar(250, 125, 125), 2, 8, 0 );
            String s = boundRect.x + boundRect.width/2 + "x" + boundRect.y + boundRect.height/2;
            Core.putText(img, s, new Point(50, 50), Core.FONT_HERSHEY_COMPLEX, 1, new Scalar(20, 40, 80), 3, 8, false);
            Imgproc.drawContours(drawing, contours, i_cont, new Scalar(125,125,250), 2);
            end.add(drawing);
            end.add(img);
            end.add(binary);
        }
        return end;
    }

}
