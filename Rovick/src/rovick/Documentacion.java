//      ____                          ____           _                  _                   _ 
//     |  _ \    __ _    __ _        |  _ \   _ __  (_)  _ __     ___  (_)  _ __     __ _  | |
//     | |_) |  / _` |  / _` |       | |_) | | '__| | | | '_ \   / __| | | | '_ \   / _` | | |
//     |  __/  | (_| | | (_| |  _    |  __/  | |    | | | | | | | (__  | | | |_) | | (_| | | |
//     |_|      \__,_|  \__, | (_)   |_|     |_|    |_| |_| |_|  \___| |_| | .__/   \__,_| |_|
//                      |___/                                              |_|                

/** \mainpage Pagina principal
 *
 * \section intro_sec Concepto del proyecto
 *
 *Este proyecto es un proyecto fin de modulo superior, para ser más exacto, 
 *DAM (Desarrollo de aplicaciones en multiplataforma), de un alumno del centro Gregorio Fernandez (Valladolid),
 *cuyo nombre es Víctor Pastor Urueña, el objetivo es crear un proyecto real.<br>
 *Cumpliendo con los requisitos del dicho proyecto, es decir, que resuelva cubos de Rubik.
 *
 * \section Requisitos Requisitos
 * - Cubo de rubik sin pegatina en la cara del blanco
 * 
 * \section Instrucciones  Instrucciones
 * - Conectar el Arduino y la camara a uno de los USB del ordenador <b>antes</b> de cargar la aplicación.
 * - Ejecutar el .jar de la aplicacion Rovick.jar
 * - Colocar el cubo en el gancho inferior de froma centrada.
 * - Disfrutar de esta increible aplicacion.
 *  
 */

/********************************************************************************************************/

//         ____           _                                 
//        / ___|  _   _  (_)   __ _     _   _   ___    ___  
//       | |  _  | | | | | |  / _` |   | | | | / __|  / _ \ 
//       | |_| | | |_| | | | | (_| |   | |_| | \__ \ | (_) |
//        \____|  \__,_| |_|  \__,_|    \__,_| |___/  \___/ 


/**
 * \page Uso Guia de uso
 * \section Botones Botones de movimiento
 * Los botones de movimiento son como este:
 * ![boton de movimiento] (DoxyRecursos/images/R.png)
 * Cuando lo pulsamos y no esta activado el CheckBox de "Según pulsas", se acumulará 
 * visualizándose en el el área de texto:
 * ![Ejemplo boton movimiento] (DoxyRecursos/images/BotonesMov_ej.png)
 * Hay unos botones a la derecha de área de texto que sirven para borrar los movimientos,
 * El primer movimiento, El ultimo o todos.
 * 
 * Cada vez que añadimos un movimiento acumulado o lo eliminamos se ajusta el tiempo y
 * el número de movimientos en el contador del tiempo de arriba a la izquierda.
 * ![Contador de tiempo] (DoxyRecursos/images/contadoTiempoMovs.png)
 * 
 * \section Otros_controles Otros controles
 * <h3>Botón "Soltar"</h3>
 * Este botón sirve para cuando el robot tiene el cubo agarrado 
 * y por lo que sea queremos que lo suelte, para que lo vuelva a agarrar solo 
 * tienes que dar a un movimiento y antes de hacer dicho movimiento lo volverá a agarrar.
 * ![Botón soltar] (DoxyRecursos/images/BotonSoltar.png)
 * 
 * <h3>Botón "Parar"</h3>
 * Cuando está realizando todos los movimientos y analizando el cubo,
 * este botón se activa y sirve para parar dichos procesos.
 * ![Botón Parar] (DoxyRecursos/images/BotonParar.png)
 * 
 * <h3>CheckBox "Luz"</h3>
 * Cuando está activada las luces del robot se encienden y cuando lo desactivamos se apagan.
 * ![CheckBox Luz] (DoxyRecursos/images/BLuz.png)
 * 
 * <h3>Botón "Gen. Random"</h3>
 * Genera el número de movimientos aleatorios que tenga el Spinner de al lado,
 * por defecto 25.
 * ![Botón Gen. Random] (DoxyRecursos/images/GenRandom.png)
 * 
 * <h3>Botón "Realizar movimientos"</h3>
 * Realiza todos los movimientos acumulados en el área de texto,
 * empezará un proceso que hará la cuenta atrás de tiempo he ira avanzando la 
 * barra de progreso cuando esta y el tiempo acaben, significara que se han 
 * realizado todos los movimientos.
 * ![Bóton Realizar movimientos] (DoxyRecursos/images/RMovs.png)
 * 
 * <h3>Botón "Resolver cubo"</h3>
 * Analiza el cubo usando la cámara del robot, si detecta todos los colores
 * bien genera el algoritmo para resolverlo y lo pone en el área de texto,
 * a continuación, si no está activado el CheckBox "Solo algoritom"
 * realizará dichos movimientos ,resolviendo el cubo, de lo contrario,
 * no hará ningún movimiento.
 * ![Botón Resolver cubo] (DoxyRecursos/images/Rcubo.png)
 * 
 * <h3>Puerto</h3>
 * Informa de en que puesto se ha conectado, si todo salió bien en la configuración,
 * será el puerto en el que esté el Arduino.
 * ![Botón Puerto] (DoxyRecursos/images/puerto.png)
 */

/********************************************************************************************************/

//       ____    _                                                          
//      |  _ \  (_)   __ _    __ _   _ __    __ _   _ __ ___     __ _   ___ 
//      | | | | | |  / _` |  / _` | | '__|  / _` | | '_ ` _ \   / _` | / __|
//      | |_| | | | | (_| | | (_| | | |    | (_| | | | | | | | | (_| | \__ \
//      |____/  |_|  \__,_|  \__, | |_|     \__,_| |_| |_| |_|  \__,_| |___/
//                           |___/                                          


/**
 * \page Diagramas Diagramas
 * \section De_Uso De uso
 * 
 * \subsection Hacer_movimiento Hacer movimiento
 * 
 * \subsection Acumular_movimientos Acumular movimientos
 * 
 * \subsection Hacer_todos_los_movimientos Hacer todos los movimientos
 * 
 * \subsection Resolver_cubo Resolver cubo
 * 
 */

/********************************************************************************************************/


//        __  __           _                   _           _              
//       |  \/  |   __ _  | |_    ___   _ __  (_)   __ _  | |   ___   ___ 
//       | |\/| |  / _` | | __|  / _ \ | '__| | |  / _` | | |  / _ \ / __|
//       | |  | | | (_| | | |_  |  __/ | |    | | | (_| | | | |  __/ \__ \
//       |_|  |_|  \__,_|  \__|  \___| |_|    |_|  \__,_| |_|  \___| |___/


/**
 * \page Materiales Materiales
 * \section Impresora_3D Impresora 3D
 * 
 * El modelo de la impresora con que se hizo este proyecto ha sido una Anet-8,
 * pero se puede hacer con cualquir impresora 3d.
 * 
 * \section material-precio material-precio
 * 
 * \subsection Tabla Tabla materiales
 * 
 * |Matrial|Cantidad|Precio(€)|
 * |:-----:|:-----:|:-------:|
 * |PLA-negro |1Kg|16,00€|
 * |PLA-blanco|1Kg|16,00€|
 * |DS3218 Servo Motor with Horn|4|En pack de 4 - 24,38€|
 * |Hitec HS-311 Servo Motor|4|En pack de 4 - 36,60€|
 * |150mm Servo Extension Lead, Male-to-Female|4|En pack de 10 - 1,24€|
 * |PCA9685 16 Canal 12-bit PWM/servo Driver|1|3,13€|
 * |Arduino NANO|1|2,15€|
 * |Rock PD cargador(alimentar motores)|1|8,97€|
 * |Kebidumei USB 30 m(camara)|1|3,78€|
 * |Cables macho y hembras para Arduino|1|Pack - 1,71|
 * |Metrica M3-12 Tornillo cabeza avellanda|80|5,00€|
 * |Metrica 12 - Tuercas|40|3,00€|
 * |Tornillo perforante - 2mm|10|1,00€|
 * |Tira de LED's luz fria|1|12,36€|
 * ||||
 * 
 * \subsection total Total
 * 
 */

/********************************************************************************************************/

//      ____                                                   
//     |  _ \    ___    ___   _   _   _ __   ___    ___    ___ 
//     | |_) |  / _ \  / __| | | | | | '__| / __|  / _ \  / __|
//     |  _ <  |  __/ | (__  | |_| | | |    \__ \ | (_) | \__ \
//     |_| \_\  \___|  \___|  \__,_| |_|    |___/  \___/  |___/


/**
 * \page Rescusos Rescusos
 * \section Modelo_3d Modelo 3D
 * [Model 3D] (https://www.thingiverse.com/thing:2471044)<br>
 * [Pagina Oficial] (http://www.rcr3d.com/)
 * \section Librerias Librerias
 * 
 * \section clases_externas Clases externas
 * 
 */

/********************************************************************************************************/

//      _____   _                 _                            _                
//     | ____| | |   ___    ___  | |_   _ __    ___    _ __   (_)   ___    __ _ 
//     |  _|   | |  / _ \  / __| | __| | '__|  / _ \  | '_ \  | |  / __|  / _` |
//     | |___  | | |  __/ | (__  | |_  | |    | (_) | | | | | | | | (__  | (_| |
//     |_____| |_|  \___|  \___|  \__| |_|     \___/  |_| |_| |_|  \___|  \__,_|

/**
 * \page Electronica Electronica
 * \section Montaje Montaje
 * El montaje del robot esta detellado en la página oficial del robot
 * 
 * \section Esquemas Esquemas
 * 
 * \section clases_externas Clases externas
 * 
 */

/********************************************************************************************************/

//       ____                     _   _   _                 
//      / ___|  _ __    ___    __| | (_) | |_    ___    ___ 
//     | |     | '__|  / _ \  / _` | | | | __|  / _ \  / __|
//     | |___  | |    |  __/ | (_| | | | | |_  | (_) | \__ \
//      \____| |_|     \___|  \__,_| |_|  \__|  \___/  |___/

/**
 * \page Creditos Creditos
 * \section Testers Testers
 * 
 * \section Ayudantes Ayudantes
 * 
 * 
 */


/********************************************************************************************************/

//       _____   _                                          
//      |_   _| (_)   ___   _ __ ___    _ __     ___    ___ 
//        | |   | |  / _ \ | '_ ` _ \  | '_ \   / _ \  / __|
//        | |   | | |  __/ | | | | | | | |_) | | (_) | \__ \
//        |_|   |_|  \___| |_| |_| |_| | .__/   \___/  |___/
//                                     |_|               

/**
 * \page Tiempos Tiempos
 * \section Montaje Montaje
 * 
 * \subsection Hardware Hardware
 * 
 * \section Software Software
 * 
 * \subsection Arduino Arduino
 * 
 * \subsection Java Java
 * 
 */