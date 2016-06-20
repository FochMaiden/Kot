#include <Servo.h>

Servo myservo;
Servo servodwa;// create servo object to control a servo

  // analog pin used to connect the potentiometer
int val,val2;    // variable to read the value from the analog pin
int potpin = 3;
char wartosc[3];
char z = EOF;

void setup() {
  Serial.begin(9600);
  myservo.attach(6);
  servodwa.attach(7);// attaches the servo on pin 9 to the servo object
  pinMode(5,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(3,OUTPUT);
}

void loop() {
  val2+=10;
  if(val2>254){
  val2=0;
  }
  analogWrite(5,val2/3);
  analogWrite(4,val2/3);
  analogWrite(3,val2/3);
  Serial.readBytesUntil(z,wartosc,3);
  val = atoi(wartosc);
  //Serial.print("Pozycja potencjometru: ");
  //Serial.println(val);// reads the value of the potentiometer (value between 0 and 1023)
  //val = map(val, 0, 1023, 0, 180);
  //Serial.print("Pozycja serva: ");
  //Serial.println(val);// scale it to use it with the servo (value between 0 and 180)
  //myservo.write(val);
  int i;
  for(i=70;i<120;i++)
  {
  myservo.write(i);
  delay(50);
  }
  for(i=70;i<120;i++)
  {
  servodwa.write(i);
  delay(50);
  }

  Serial.print("Pozycja servo 1: ");
  Serial.println(val);
  //myservo.write(val);
  //servodwa.write(val);// sets the servo position according to the scaled value
  delay(15);                           // waits for the servo to get there
}