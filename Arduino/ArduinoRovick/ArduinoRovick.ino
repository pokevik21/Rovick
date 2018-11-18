#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
boolean inPosIni = false;

//     __     __                 _           _       _              
//     \ \   / /   __ _   _ __  (_)   __ _  | |__   | |   ___   ___ 
//      \ \ / /   / _` | | '__| | |  / _` | | '_ \  | |  / _ \ / __|
//       \ V /   | (_| | | |    | | | (_| | | |_) | | | |  __/ \__ \
//        \_/     \__,_| |_|    |_|  \__,_| |_.__/  |_|  \___| |___/
int pinLED = 6;
#define FREQUENCY 50
//DERECHO
#define pinDerBr 0
#define pinDerAg 8
#define derUp 503
#define derMid 300
#define derDown 102
#define derAgarrado 91
#define derDesAgarrado 256

//IZQUIERDA
#define pinIzqBr 4
#define pinIzqAg 12
#define izqUp 482
#define izqMid 278
#define izqDown 95
#define izqAgarrado 255
#define izqDesAgarrado 440

//ARRIBA
#define pinUpBr 6
#define pinUpAg 14
#define upR 90
#define upMid 290
#define upL 487
#define upAgarrado 245
#define upDesAgarrado 440

//ABAJO
#define pinBajoBr 2
#define pinBajoAg 10
#define bajoR 78
#define bajoMid 276
#define bajoL 468
#define bajoAgarrado 270
#define bajoDesAgarrado 460

//otras
char inChar;
String toDo="";
boolean TransmisionCompleta=false;
boolean noMove=false;

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
  pinMode(pinLED,OUTPUT);
  Serial.begin(9600);
  Serial.println("Rovick");
  pwm.begin();
  toDo.reserve(100);
  pwm.setPWMFreq(FREQUENCY);
  fin();
  Serial.println("¡Listo!");
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
  sameTime(pinDerAg,pinIzqAg,derDesAgarrado,izqDesAgarrado);
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
  R();
  vuelta();
}

void FD(){
  ida();
  RD();
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

// Start BACK
void s1(){ //timpo 0.5s
  sameTime(pinUpAg,pinBajoAg,upDesAgarrado,bajoDesAgarrado);
}

void s2(){ //timpo 1s
  sameTime(pinUpAg,pinBajoAg,upAgarrado,bajoAgarrado);
  sameTime(pinDerAg,pinIzqAg,derDesAgarrado,izqDesAgarrado);
}

void s3(){ //timpo 0.5s
  sameTime(pinUpBr,pinBajoBr,upR,bajoL);
}

void s4(){//timpo 1s
  sameTime(pinUpBr,pinBajoBr,upMid,bajoMid);
  sameTime(pinUpBr,pinBajoBr,upL,bajoR);
}

void s5(){//timpo 2s         
  sameTime(pinUpBr,pinBajoBr,upMid,bajoMid);
  sameTime(pinDerAg,pinIzqAg,derAgarrado,izqAgarrado);
  sameTime(pinUpAg,pinBajoAg,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinDerBr,pinIzqBr,derUp,izqDown);
}                             

void s6(){//timpo 1          
  sameTime(pinDerBr,pinIzqBr,derMid,izqMid);
  sameTime(pinDerBr,pinIzqBr,derDown,izqUp);
}                            

void s7(){//timpo 6.5          
  sameTime(pinDerBr,pinIzqBr,derMid,izqMid);
  sameTime(pinUpAg,pinBajoAg,upAgarrado,bajoAgarrado);
  ida();
  //ida menos el ultimo movimiento
  sameTime(pinDerAg,pinIzqAg,derDesAgarrado,izqDesAgarrado);
  sameTime(pinBajoBr,pinUpBr,bajoL,upR);
  sameTime(pinDerAg,pinIzqAg,derAgarrado,izqAgarrado);
  sameTime(pinUpAg,pinBajoAg,upDesAgarrado,bajoDesAgarrado);
  sameTime(pinUpBr,pinBajoBr,upMid,bajoMid);
}                            

void s8(){//timpo 1s         
  sameTime(pinUpAg,pinBajoAg,upAgarrado,bajoAgarrado);
  sameTime(pinDerAg,pinIzqAg,derDesAgarrado,izqDesAgarrado);
}

void s9(){//timpo 6s     
  vuelta();
  vuelta();
}


void encenderLED(){
  digitalWrite(pinLED,HIGH);
}

void apargarLED(){
  digitalWrite(pinLED,LOW);
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
     char first = toDo.charAt(0);
     
    switch(first){
         case 'Z':
            encenderLED();
            noMove=true;
          break;   
       case 'X':
            apargarLED();
            noMove=true;
          break;
    }

   if(!noMove){
       if(!inPosIni){
        posIni();
        inPosIni = true; 
       }
      
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
         case 'I':
              ida();
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
         case '7':
              s7();
            break;
         case '8':
              s8();
            break;
         case '9':
              s9();
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
   }
    toDo = "";  //Limpiar el String
    TransmisionCompleta = false;  //Limpiar la bandera
    noMove=false;
}

}
  

