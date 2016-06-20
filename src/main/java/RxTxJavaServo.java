import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.OutputStream;

public class RxTxJavaServo {

        InputStream in;
        OutputStream out;

        void connect(String portName) throws Exception {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Port zajety");
            } else {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    this.in = serialPort.getInputStream();
                    this.out = serialPort.getOutputStream();

                    (new Thread(new SerialReader(in))).start();
                    (new Thread(new SerialWriter(out))).start();

                } else {
                    System.out.println("Nie com port");
                }
            }
        }




}

