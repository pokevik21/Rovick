#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
boolean inPosIni = false;

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
#define pinBajoBr 2
#define pinBajoAg 10
#define bajoR 85
#define bajoMid 276
#define bajoL 468
#define bajoAgarrado 290
#define bajoDesAgarrado 460

//otras
char inChar;
String toDo="";
boolean TransmisionCompleta=false;

/******************************************** FIN VARIABLES ***************************************************/


void posIni(){
  pwm.setPWM(pinDerBr, 0, derMid);
  pwm.setPWM(pinIzqBr, 0, izqMid);
  pwm.setPWM(pinUpBr, 0, upMid);
  pwm.setPWM(pinBajoBr, 0, bajoMid);
  delay(1000);

  sameTime(pinBajoAg,pinUpAg,bajoAgarrado,upAgarrado);
  sameTime(pinDerAg,pinIzqAg,derAgarrado,izqAgarrado);
}

void fin(){
  pwm.setPWM(pinDerBr, 0, derMid);
  pwm.setPWM(pinIzqBr, 0, izqMid);
  pwm.setPWM(pinUpBr, 0, upMid);
  pwm.setPWM(pinBajoBr, 0, bajoMid);
  delay(1000);
  
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinIzqAg,izqDesAgarrado);
  doMove(pinUpAg,upDesAgarrado);
  doMove(pinBajoAg,bajoDesAgarrado);
  inPosIni=false;
}

void setup()
{
  Serial.begin(9600);
  pwm.begin();
  toDo.reserve(100);
  pwm.setPWMFreq(FREQUENCY);
  fin();
  Serial.print("listo!");
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

void sameTime(int pin1, int pin2 , int pos1, int pos2){
  pwm.setPWM(pin1, 0, pos1);
  pwm.setPWM(pin2, 0, pos2);
  delay(500);
}

//          _                     _   _   _                             
//         / \     _   _  __  __ (_) | | (_)   __ _   _ __    ___   ___ 
//        / _ \   | | | | \ \/ / | | | | | |  / _` | | '__|  / _ \ / __|
//       / ___ \  | |_| |  >  <  | | | | | | | (_| | | |    |  __/ \__ \
//      /_/   \_\  \__,_| /_/\_\ |_| |_| |_|  \__,_| |_|     \___| |___/

void ida(){
  sameTime(pinDerAg,pinIzqAg,derDesAgarrado,izqDesAgarrado);
  sameTime(pinBajoBr,pinUpBr,bajoL,upR);
  sameTime(pinDerAg,pinIzqAg,derAgarrado,izqAgarrado);
  sameTime(pinUpAg,pinBajoAg,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinUpBr,pinBajoBr,upMid,bajoMid);
  sameTime(pinUpAg,pinBajoAg,upAgarrado,bajoAgarrado);
}

void vuelta(){
  sameTime(pinBajoBr,pinUpBr,bajoR,upL);
  sameTime(pinDerAg,pinIzqAg,derAgarrado,izqAgarrado);
  sameTime(pinUpAg,pinBajoAg,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinUpBr,pinBajoBr,upMid,bajoMid);
  sameTime(pinUpAg,pinBajoAg,upAgarrado,bajoAgarrado);
  
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



//      _____                          _             _ 
//     |  ___|  _ __    ___    _ __   | |_    __ _  | |
//     | |_    | '__|  / _ \  | '_ \  | __|  / _` | | |
//     |  _|   | |    | (_) | | | | | | |_  | (_| | | |
//     |_|     |_|     \___/  |_| |_|  \__|  \__,_| |_|

void Fo(){
  ida();
  doMove(pinDerBr,derUp);
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinDerBr,derMid);
  doMove(pinIzqAg,izqDesAgarrado);
  vuelta();
}

void FD(){
  ida();
  doMove(pinDerBr,derDown);
  doMove(pinDerAg,derDesAgarrado);
  doMove(pinDerBr,derMid);
  doMove(pinIzqAg,izqDesAgarrado);
  vuelta();
}

void FIda(){
  ida();
  R();
}

void FDda(){
  ida();
  RD();
}


//      ____                   _    
//     | __ )    __ _    ___  | | __
//     |  _ \   / _` |  / __| | |/ /
//     | |_) | | (_| | | (__  |   < 
//     |____/   \__,_|  \___| |_|\_\

void B(){
  ida();
  doMove(pinIzqBr,izqUp);
  doMove(pinIzqAg,izqDesAgarrado);
  doMove(pinIzqBr,izqMid);
  doMove(pinDerAg,derDesAgarrado);
  vuelta();
}

void BD(){
  ida();
  doMove(pinIzqBr,izqDown);
  doMove(pinIzqAg,izqDesAgarrado);
  doMove(pinIzqBr,izqMid);
  doMove(pinDerAg,derDesAgarrado);
  vuelta();
}

void BIda(){
  ida();
  L();
}

void BDIda(){
  ida();
  LD();
}


//       ____    _                        
//      / ___|  | |_    ___   _ __    ___ 
//      \___ \  | __|  / _ \ | '_ \  / __|
//       ___) | | |_  |  __/ | |_) | \__ \
//      |____/   \__|  \___| | .__/  |___/
//                           |_|          

void s1(){ //timpo 1s
  sameTime(pinDerBr,pinIzqBr,derDesAgarrado,izqDesAgarrado);
  sameTime(pinUpAg,pinBajoAg,upR,bajoR);
}

void s2(){//timpo 1s
  sameTime(pinUpAg,pinBajoAg,upMid,bajoMid);
  sameTime(pinUpAg,pinBajoAg,upL,bajoL);
}

void s3(){//timpo 2s
  sameTime(pinUpAg,pinBajoAg,upMid,bajoMid);
  sameTime(pinDerBr,pinIzqBr,derAgarrado,izqAgarrado);
  sameTime(pinUpBr,pinBajoBr,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinDerAg,pinIzqAg,derUp,izqUp);
}

void s4(){//timpo 1
  sameTime(pinDerAg,pinIzqAg,derMid,izqMid);
  sameTime(pinDerAg,pinIzqAg,derDown,izqDown);
}

void s5(){//timpo 3
  sameTime(pinUpBr,pinBajoBr,upAgarrado,bajoAgarrado);
  sameTime(pinDerBr,pinIzqBr,derDesAgarrado,izqDesAgarrado);
  sameTime(pinDerAg,pinIzqAg,derMid,izqMid);
  sameTime(pinDerBr,pinIzqBr,derAgarrado,izqAgarrado);
  sameTime(pinUpBr,pinBajoBr,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinDerAg,pinIzqAg,derDown,izqDown);
}

void s6(){//timpo 3s
  sameTime(pinDerAg,pinIzqAg,derMid,izqMid);
  sameTime(pinDerAg,pinIzqAg,derUp,izqUp);
  sameTime(pinUpBr,pinBajoBr,upAgarrado,bajoAgarrado);
  sameTime(pinDerBr,pinIzqBr,derDesAgarrado,izqDesAgarrado);
  sameTime(pinDerAg,pinIzqAg,derMid,izqMid);
  sameTime(pinDerBr,pinIzqBr,derAgarrado,izqAgarrado);
}




/******************************************** FIN METODOS ***************************************************/

void serialEvent() {
  while (Serial.available()) {

    char CharEntrada = Serial.read(); //Leer un byte del puerto serial

    toDo += CharEntrada;  //Agregar el char anterior al string

    if (CharEntrada == ';') {  //Si se detecta un fin de linea
      size_t len = toDo.length();
      toDo.remove(len-1);
      TransmisionCompleta = true;  //Se indica al programa que el usuario termino de ingresar la informacion
    }
  }
}


//     _                             
//    | |       ___     ___    _ __  
//    | |      / _ \   / _ \  | '_ \ 
//    | |___  | (_) | | (_) | | |_) |
//    |_____|  \___/   \___/  | .__/ 
//                            |_|    

void loop() {

   if (TransmisionCompleta) {
    
     if(!inPosIni){
      posIni();
      inPosIni = true; 
     }
     
    char first = toDo.charAt(0);
    if(toDo.length() == 1){
        //solo acciones con un caracter
      switch(first){
        case 'R':
            R();
          break;
        case 'L':
            L();
          break;
        case 'U':
            U();
          break;
        case 'D':
            D();
          break;
       case 'F':
            Fo();
          break;
       case 'B':
            B();
          break;
       case 'V':
            vuelta();
          break;
       case 'S':
            posIni();
          break;
       case 'E':
            fin();
          break; 
       case '1':
            s1();
          break; 
       case '2':
            s2();
          break; 
       case '3':
            s3();
          break; 
       case '4':
            s4();
          break; 
       case '5':
            s5();
          break; 
       case '6':
            s6();
          break; 
      }
      
    }else if(toDo.length() > 1){
      char sec = toDo.charAt(1);
      switch(first){
        case 'R':
            RD();
          break;
        case 'L':
            LD();
          break;
        case 'U':
            UD();
          break;
        case 'D':
            DD();
          break;
       case 'F':
            switch(sec){
              case 'D':
                if(toDo.length() > 2){
                  FIda();
                }else{
                  FD();
                }
                break;
              case 'I':
                  FIda();
                break;
            }
          break;
       case 'B':
            switch(sec){
              case 'D':
                if(toDo.length() > 2){
                  BDIda();
                }else{
                  BD();
                }
                break;
              case 'I':
                  BIda();
                break;
            }
      }
    }

    toDo = "";  //Limpiar el String
    TransmisionCompleta = false;  //Limpiar la bandera
}

}
  

