#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

Adafruit_PWMServoDriver servos = Adafruit_PWMServoDriver(0x40);

unsigned int pos0=172; // ancho de pulso en cuentas para pocicion 0°
unsigned int pos180=677; // ancho de pulso en cuentas para la pocicion 180°
boolean agarrado = false;

void setup() {
  Serial.begin(9600);
  servos.begin();  
  servos.setPWMFreq(60); //Frecuecia PWM de 60Hz o T=16,66ms
  Serial.print("Bienvenido!");

  setServo(1,50);
  delay(500);
  setServo(11,0);
  delay(500);
  
}

void setServo(uint8_t n_servo, int angulo) {
  int duty;
  duty=map(angulo,0,180,pos0, pos180);
  servos.setPWM(n_servo, 0, duty);  
}

void loop() {

 while (Serial.available() == 0){ }
  int ang = Serial.parseInt();
  Serial.println(ang);


switch (ang) {
  case 5:
  
    if( agarrado ){
      setServo(1,50);
    }else{
      setServo(1,-20);
    }
    agarrado = !agarrado;
    
    break;
  case 4:
    setServo(11,0);
    break;
  case 2:
   setServo(11,90);
    break;
}
   delay(1000);
  
     
    
Serial.print("terminado");
}
