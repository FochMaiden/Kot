#include <Servo.h>

Servo servoX;
Servo servoY;
int X,Y;
char valForX[3],valForY[3];
char z = EOF;
int flag;

void setup() {
  Serial.begin(9600);
  servoX.attach(6);
  servoY.attach(7);
  pinMode(5,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(3,OUTPUT);
}

void loop() {
  if(Serial.available() > 0){
      if(flag ==0){
         analogWrite(5,0);
        analogWrite(4,150);
    Serial.readBytesUntil(z,valForY,3);
      Y = atoi(valForY);
      if(Y>90||Y<45){
       servoY.write(91);
      }else{
        servoY.write(Y);
      }

      flag =1;
     }else{
        analogWrite(5,150);
        analogWrite(4,0);
    Serial.readBytesUntil(z,valForX,3);
      X = atoi(valForX);
      if(X>90||X<45){
        servoX.write(91);
      }else{
        servoX.write(X);
      }
      flag = 0;
    }
  }
}