import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class FindShape {
    private VideoCapture camera;
    private MyFrame myFrame;
    private Mat firstImage = new Mat();
    private String xs,ys;
    private Mat secondImage = new Mat();
    private Mat foregroundImage = new Mat();
    private Mat end = new Mat();
    private MatOfPoint contour;
    private List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private double max=0;
    private int i_max=0;
    private RxTxJavaServo serialPort;
    private byte[] tabX,tabY;
    private char[] tabX2,tabY2;

    public FindShape(VideoCapture camera){
        this.camera=camera;
        this.myFrame = new MyFrame(camera);
        this.serialPort = new RxTxJavaServo();
        try {
            serialPort.connect("COM3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Mat find() {
        this.secondImage = myFrame.imageAsBW();
        Core.subtract(secondImage, firstImage, foregroundImage);
        Imgproc.threshold(foregroundImage, end, 50, 255, Imgproc.THRESH_BINARY);
        getContour(end);
        return end;
    }


    void getContour(Mat foregroundImage){
        contours.clear();
        max=0;
        i_max=0;
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
        if(i_max>0) {
            contours.get(i_max).convertTo(thisContour2f, CvType.CV_32FC2); //conversja matofpoint -> matofpoint2f

            Imgproc.approxPolyDP(thisContour2f, approxContour2f, 3, true); //oszacowanie konturów
            approxContour2f.convertTo(approxContour, CvType.CV_32S); //conversja matofpoint2f -> matofpoint
            Rect boundRect = Imgproc.boundingRect(approxContour); //przypisanie prostokąta otaczającego contur
            Core.rectangle(end, boundRect.tl(), boundRect.br(), new Scalar(125, 250, 125), 2, 8, 0); //wyrysowanie prostokata
            Core.line(end, boundRect.tl(), boundRect.br(), new Scalar(250, 125, 125), 2, 8, 0); // X wyrysowany
            Core.line(end, new Point(boundRect.x + boundRect.width, boundRect.y), new Point(boundRect.x, boundRect.y + boundRect.height), new Scalar(250, 125, 125), 2, 8, 0);
            Imgproc.drawContours(end, contours, i_max, new Scalar(255, 0, 0), 2); // kontury z listy rysowane na img
            if(boundRect.width >= boundRect.height){
                xs = String.format("X%03d",(boundRect.x + boundRect.width / 2));
                ys = String.format("Y%03d",(boundRect.y + boundRect.height / 2));
                System.out.println(xs + "   " + ys);
                tabX = xs.getBytes();
                tabY = ys.getBytes();

                try {
                    serialPort.out.write(tabX);
                    serialPort.out.write(tabY);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        void getFirstimage(){
            this.firstImage = myFrame.imageAsBW();
        }
}
