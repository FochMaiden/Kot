#include <Servo.h>

Servo servoX;
Servo servoY;
int X,xl,Y,yl;
char valForX[3];
char valForY[3];
char garbage[2];
char z = EOF;
int flag = 0;

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
    z = Serial.read();
    if(z=='X'){
      X = Serial.parseInt();
      Serial.println(X);
      X = map(X,0,400,70,120);
      servoX.write(X);

    }else if(z=='Y'){
      Y = Serial.parseInt();
      Serial.println(Y);
      Y = map(Y,0,400,90,30);
      servoY.write(Y);
    }
  }
}