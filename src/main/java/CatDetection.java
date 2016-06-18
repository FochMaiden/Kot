import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CatDetection {
    private Mat img, hsv_img, binary, gray;
    private Mat cont;
    private List<MatOfPoint> contours;
    int x,y;


    CatDetection(Mat frame){
        this.img = frame;
        this.gray = new Mat();
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
        x=200;
        y=210;

        gray = new Mat(img.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);

        Core.inRange(gray,new Scalar(x,x,x),new Scalar(y,y,y),binary);
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
            Core.fillConvexPoly(gray,approxContour,new Scalar(0,255,0));
            Core.rectangle(gray, boundRect.tl(), boundRect.br(), new Scalar(125, 250, 125), 2, 8, 0);
            Core.line(gray, boundRect.tl(), boundRect.br(), new Scalar(250, 125, 125), 2, 8, 0);
            Core.line(gray, new Point(boundRect.x + boundRect.width, boundRect.y), new Point(boundRect.x, boundRect.y+boundRect.height), new Scalar(250, 125, 125), 2, 8, 0 );
            for(int j =0; j< contours.size(); j++)
                Imgproc.drawContours(gray, contours, j, new Scalar(255,0 , 0), 2);

        }
        return gray;
    }

}
