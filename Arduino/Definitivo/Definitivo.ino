#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();


//     __     __                 _           _       _              
//     \ \   / /   __ _   _ __  (_)   __ _  | |__   | |   ___   ___ 
//      \ \ / /   / _` | | '__| | |  / _` | | '_ \  | |  / _ \ / __|
//       \ V /   | (_| | | |    | | | (_| | | |_) | | | |  __/ \__ \
//        \_/     \__,_| |_|    |_|  \__,_| |_.__/  |_|  \___| |___/
#define FREQUENCY 50
//DERECHO
#define pinDerBr 0
#define pinDerAg 8
#define derUp 500
#define derMid 300
#define derDown 100
#define derAgarrado 106
#define derDesAgarrado 256

//IZQUIERDA
#define pinIzqBr 4
#define pinIzqAg 12
#define izqUp 482
#define izqMid 278
#define izqDown 95
#define izqAgarrado 280
#define izqDesAgarrado 440

//ARRIBA
#define pinUpBr 6
#define pinUpAg 14
#define upR 95
#define upMid 285
#define upL 480
#define upAgarrado 270
#define upDesAgarrado 440

//ABAJO
#define pinBajoBr 4
#define pinBajoAg 10
#define bajoR 85
#define bajoMid 276
#define bajoL 468
#define bajoAgarrado 290
#define bajoDesAgarrado 460


/******************************************** FIN VARIABLES ***************************************************/


void posIni(){
  doMove(pinBajoAg,bajoAgarrado);
  doMove(pinUpAg,upAgarrado);
  doMove(pinDerBr,derMid);
  doMove(pinDerAg,derAgarrado);
  doMove(pinIzqBr,izqMid);
  doMove(pinIzqAg,izqAgarrado);
}

void fin(){
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinUpAg,upDesAgarrado);
  doMove(pinIzqAg,izqDesAgarrado);
}

void setup()
{
  Serial.begin(9600);
  pwm.begin();
  pwm.setPWMFreq(FREQUENCY);
  //posIniciales();
  Serial.println("Listo!");
}
/******************************************** FIN INICIO ***************************************************/

//       __  __          _                 _               
//      |  \/  |   ___  | |_    ___     __| |   ___    ___ 
//      | |\/| |  / _ \ | __|  / _ \   / _` |  / _ \  / __|
//      | |  | | |  __/ | |_  | (_) | | (_| | | (_) | \__ \
//      |_|  |_|  \___|  \__|  \___/   \__,_|  \___/  |___/
 
void doMove(int pin,int posicion){
  pwm.setPWM(pin, 0, posicion);
  delay(500);
}


//      ____                                _             
//     |  _ \    ___   _ __    ___    ___  | |__     __ _ 
//     | | | |  / _ \ | '__|  / _ \  / __| | '_ \   / _` |
//     | |_| | |  __/ | |    |  __/ | (__  | | | | | (_| |
//     |____/   \___| |_|     \___|  \___| |_| |_|  \__,_|
void RD(){
  doMove(pinDerBr,derDown);
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinDerBr,derMid);
  doMove(pinDerAg,derAgarrado);
}

void R(){
  doMove(pinDerBr,derUp);
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinDerBr,derMid);
  doMove(pinDerAg,derAgarrado);
}


//       ___                         _                     _         
//      |_ _|  ____   __ _   _   _  (_)   ___   _ __    __| |   __ _ 
//       | |  |_  /  / _` | | | | | | |  / _ \ | '__|  / _` |  / _` |
//       | |   / /  | (_| | | |_| | | | |  __/ | |    | (_| | | (_| |
//      |___| /___|  \__, |  \__,_| |_|  \___| |_|     \__,_|  \__,_|
//                      |_|                                          
void LD(){
  doMove(pinIzqBr,izqDown);
  doMove(pinIzqAg,izqDesAgarrado);
  doMove(pinIzqBr,izqMid);
  doMove(pinIzqAg,izqAgarrado);
}

void L(){
  doMove(pinIzqBr,izqUp);
  doMove(pinIzqAg,izqDesAgarrado);
  doMove(pinIzqBr,izqMid);
  doMove(pinIzqAg,izqAgarrado);
}

//          _                    _   _             
//         / \     _ __   _ __  (_) | |__     __ _ 
//        / _ \   | '__| | '__| | | | '_ \   / _` |
//       / ___ \  | |    | |    | | | |_) | | (_| |
//      /_/   \_\ |_|    |_|    |_| |_.__/   \__,_|

void UD(){
  doMove(pinUpBr,upR);
  doMove(pinUpAg,upDesAgarrado);
  doMove(pinUpBr,upMid);
  doMove(pinUpAg,upAgarrado);
}

void U(){
  doMove(pinUpBr,upL);
  doMove(pinUpAg,upDesAgarrado);
  doMove(pinUpBr,upMid);
  doMove(pinUpAg,upAgarrado);
}


//          _      _                 _         
//         / \    | |__     __ _    (_)   ___  
//        / _ \   | '_ \   / _` |   | |  / _ \ 
//       / ___ \  | |_) | | (_| |   | | | (_) |
//      /_/   \_\ |_.__/   \__,_|  _/ |  \___/ 
//                                |__/ 

void DD(){
  doMove(pinBajoBr,bajoR);
  doMove(pinBajoAg,bajoDesAgarrado);
  doMove(pinBajoBr,bajoMid);
  doMove(pinBajoAg,bajoAgarrado);
}

void D(){
  doMove(pinBajoBr,bajoL);
  doMove(pinBajoAg,bajoDesAgarrado);
  doMove(pinBajoBr,bajoMid);
  doMove(pinBajoAg,bajoAgarrado);
}



/******************************************** FIN METODOS ***************************************************/

//     _                             
//    | |       ___     ___    _ __  
//    | |      / _ \   / _ \  | '_ \ 
//    | |___  | (_) | | (_) | | |_) |
//    |_____|  \___/   \___/  | .__/ 
//                            |_|    

void loop() {

  while (Serial.available() == 0){ }
  int ang = Serial.parseInt();

  
  
  Serial.println("terminado");
}
