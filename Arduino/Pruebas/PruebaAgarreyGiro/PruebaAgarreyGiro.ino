#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();

#define MIN_PULSE_WIDTH 650
#define MAX_PULSE_WIDTH 2350
#define DEFAULT_PULSE_WIDTH 1500
#define FREQUENCY 50
uint8_t servonum = 0;

int acumulado=0;

void setup()
{
  Serial.begin(9600);
  Serial.println("16 channel Servo test!");
  pwm.begin();
  pwm.setPWMFreq(FREQUENCY);
}

int pulseWidth(int angle)
{
  int pulse_wide, analog_value;
  pulse_wide = map(angle, 0, 180, MIN_PULSE_WIDTH, MAX_PULSE_WIDTH);
  analog_value = int(float(pulse_wide) / 1000000 * FREQUENCY * 4096);
  Serial.println(analog_value);
  return analog_value;
}

void loop() {
  
  while (Serial.available() == 0);
  int ang = Serial.read() - '0';

  Serial.print("angulo: ");
  Serial.println(ang);

    
  switch (ang) {
  case 0:
    pwm.setPWM(0, 0, pulseWidth(65));
    delay(1000);
    break;
  case 1:
    pwm.setPWM(0, 0, pulseWidth(-13));
    delay(1000);
    break;
  case 2:
   pwm.setPWM(1, 0, pulseWidth(6));
    delay(1000);
    break;
  case 3:
    pwm.setPWM(1, 0, pulseWidth(115));
    delay(1000);
    break;
}



  
  Serial.println("terminado");
}
