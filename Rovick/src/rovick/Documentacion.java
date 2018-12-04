//      ____                          ____           _                  _                   _ 
//     |  _ \    __ _    __ _        |  _ \   _ __  (_)  _ __     ___  (_)  _ __     __ _  | |
//     | |_) |  / _` |  / _` |       | |_) | | '__| | | | '_ \   / __| | | | '_ \   / _` | | |
//     |  __/  | (_| | | (_| |  _    |  __/  | |    | | | | | | | (__  | | | |_) | | (_| | | |
//     |_|      \__,_|  \__, | (_)   |_|     |_|    |_| |_| |_|  \___| |_| | .__/   \__,_| |_|
//                      |___/                                              |_|                

/** \mainpage Pagina principal
 *
 * \section Concepto Concepto del proyecto
 *
 *Este proyecto es un proyecto fin de modulo superior, para ser más exacto, 
 *DAM (Desarrollo de aplicaciones en multiplataforma), de un alumno del centro Gregorio Fernandez (Valladolid),
 *cuyo nombre es Víctor Pastor Urueña, el objetivo es crear un proyecto real.<br>
 *Cumpliendo con los requisitos del dicho proyecto, es decir, que resuelva cubos de Rubik.
 *<h3>A dejar en claro</h3>
 * El proyecto no lo he hecho yo al 100% algunas partes de el la he reutilizado de 
 * otros proyectos, por ejemplo el modelo 3D no lo he diseñado yo ni tampoco
 * la clase de java que me genera el algoritomo de resolucion, los detalles 
 * sobre los esto está en el pagina de [Recursos](@ref Rescusos)
 * 
 * \section Demostracion Demostración
 * [Video de demostracion](youtube.com)
 * 
 * \section Requisitos Requisitos
 * - Cubo de rubik sin pegatina de la marca en el centro blanco.
 * - 2 puetos USB's libres en el ordenador al que lo conecteis.
 * 
 * \section Instrucciones  Instrucciones
 * - Conectar el Arduino y la camara a uno de los USB del ordenador <b>antes</b> de cargar la aplicación.
 * - Ejecutar el .jar o .exe de la aplicacion Rovick
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
 * Descripcion de como utilizar esta aplicación.
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
 * Pagina donde se visualizan los diversos diagramas.
 * 
 * \section De_Uso De uso
 * 
 * \subsection Hacer_movimiento Hacer movimiento y acumular
 * ![Diagrama hacer movimiento] (DoxyRecursos/images/uso_clickMov.png)
 * \subsection Resolver_cubo Resolver cubo
 * ![Diagrama hacer movimiento] (DoxyRecursos/images/uso_resolverCubo.png)
 * 
 * \section color Reconocimiento de color
 */

/********************************************************************************************************/

//      ____                                                   
//     |  _ \    ___    ___   _   _   _ __   ___    ___    ___ 
//     | |_) |  / _ \  / __| | | | | | '__| / __|  / _ \  / __|
//     |  _ <  |  __/ | (__  | |_| | | |    \__ \ | (_) | \__ \
//     |_| \_\  \___|  \___|  \__,_| |_|    |___/  \___/  |___/


/**
 * \page Rescusos Rescusos
 * Pagina en la que se muestran todo
 * 
 * \section Impresora_3D Impresora 3D
 * El modelo de la impresora con que se hizo este proyecto ha sido una Anet-8,
 * pero se puede hacer con cualquir impresora 3d.
 * 
 * \section Modelo_3d Modelo 3D
 * [Model 3D] (https://www.thingiverse.com/thing:2471044)<br>
 * [Pagina Oficial] (http://www.rcr3d.com/)
 * 
 * \section material-precio material-precio
 * <h3>Tabla materiales</h3>
 * |Matrial|Cantidad|Precio|
 * |:-----|:-----:|:-------|
 * |PLA-negro |1Kg|16,00€|
 * |PLA-blanco|1Kg|16,00€|
 * |DS3218 Servo Motor with Horn|4|En pack de 4 - 24,38€|
 * |Hitec HS-311 Servo Motor|4|En pack de 4 - 36,60€|
 * |150mm Servo Extension Lead, Male-to-Female|4|En pack de 10 - 1,24€|
 * |PCA9685 16 Canal 12-bit PWM/servo Driver|1|3,13€|
 * |Arduino NANO - CH340|1|2,15€|
 * |Rock PD cargador(alimentar motores)|1|8,97€|
 * |Kebidumei USB 30 m(camara)|1|3,78€|
 * |Cables macho y hembras para Arduino|1|Pack - 1,71€|
 * |Metrica M3-12 Tornillo cabeza avellanda|80|5,00€|
 * |Metrica 12 - Tuercas|40|3,00€|
 * |Tornillo perforante - 2mm|10|1,00€|
 * |Tira de LED's luz fria|1|12,36€|
 * |Cubo de Rubick|1|12,00€|
 * <h3>Total</h3>
 * 
 * Sumando todo lo anterior el total es: 147,32€
 * 
 * \section clases_externas Clases externas
 * Las clses extrenas son:
 * - [SolverCube](@ref SolveCube.java), principal<br>
 * Y las necesarias para que esta clase funcione:
 * - [TableGenerator](@ref TableGenerator.java)
 * - stage0.txt
 * - stage1.txt
 * - stage2.txt
 * - stage3.txt<br>
 * Estas clases están sacadas del otro proyecto de gitHub:<br>
 * [GitHub del proyecto] (https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver)
 * \section Librerias Librerias
 * - Libreria de Arduino, [PanamaHitek] (http://panamahitek.com/arduino-java/)
 * - Libreria de WebCam, [webcam-capture] (http://www.webcam-capture.sarxos.pl/)
 */

/********************************************************************************************************/

//      _____   _                 _                            _                
//     | ____| | |   ___    ___  | |_   _ __    ___    _ __   (_)   ___    __ _ 
//     |  _|   | |  / _ \  / __| | __| | '__|  / _ \  | '_ \  | |  / __|  / _` |
//     | |___  | | |  __/ | (__  | |_  | |    | (_) | | | | | | | | (__  | (_| |
//     |_____| |_|  \___|  \___|  \__| |_|     \___/  |_| |_| |_|  \___|  \__,_|

/**
 * \page Electronica Electronica
 * Apartado de conexiones y hardware.
 * \section Montaje Montaje
 * El montaje del robot esta detellado en la página oficial del robot, apartado de hardware:<br>
 * [Pagina Oficial - Hardware] (http://www.rcr3d.com/hardware.html)<br>
 * 
 * Fotos de todas las piezas:<br>
 * ![Bóton Realizar movimientos] (DoxyRecursos/images/TodasLasPiezas.jpg)
 * Puedes seguir el montaje hasta el punto numero 6, luego seguir el siguiente esquema:
 * \section Esquemas Esquema
 * ![Esquema de conexiones] (DoxyRecursos/images/esquema_elecronica.png)
 * Para verlo con mas prespetiva:
 * ![Pines] (DoxyRecursos/images/visual_pines.jpg)
 * Recomiendo fuerte mente clasificar los cables, es decir, poner un papelito donde se van a conectar.<br>
 * Recomiendo unsar unar herramienta llamada Dynamo para clasificar, pero también se puede hacer con un poco de
 * celo y papel:
 * ![Cables clasificados] (DoxyRecursos/images/cabes_cladificados.jpg)
 */

/********************************************************************************************************/

//       ____                     _   _   _                 
//      / ___|  _ __    ___    __| | (_) | |_    ___    ___ 
//     | |     | '__|  / _ \  / _` | | | | __|  / _ \  / __|
//     | |___  | |    |  __/ | (_| | | | | |_  | (_) | \__ \
//      \____| |_|     \___|  \__,_| |_|  \__|  \___/  |___/

/**
 * \page Creditos Creditos
 * Reconocimiento a las personas que han ayudado en el proyecto.
 * \section Testers Testers
 * - Jesús Martín Maderos
 * 
 * \section Ayudantes Ayudantes
 * - Máximo Castañeda (compañero de Atos).<br> Ayuda con el reconocimiento de colores.
 * 
 * \section impresion Impresion
 * - Angel Urueña Mieguel.<br> De la empresa [3DSouvenirs](https://drive.google.com/open?id=1Vcz_chFV9uM-dkAUWGEENjm8wSkfJtXG)
 * , ayudo imprimiendo el logo, la palabra "ROVICK" y el código QR. Ya que no podia imprimir en varios colores.
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
 * Cuenta del tiempo invertido en el proyecto.
 * \section Hardware Hardware
 * - Tiempo total de impresion: 98h 50min  [Mas detalle...](https://github.com/pokevik21/Rovick/blob/master/Rovick/doc/DoxyRecursos/Tiempos.txt)
 * - Preparacion de las piezas: 40min
 * - Retirar piezas y configurar sigiente: 20min
 * - Tiempo de calibrar la impresora: 1h(varias veces) 
 * - Lijado de piezas: 1h 30min
 * - Montaje del Robot: 2h 30min
 * - Calibrar un brazo: 2h 30min
 * - Calibrar el resto: 2h 30min
 * - Clasificar cables y conectarlos : 30min
 * - Ajustar camara: 1h
 * - Soldar tira LEDs: 1h 30min
 * - Colocar LEDs: 20min
 * <br><br>
 * - Total hardware: 12h 20min
 * \section Software Software
 * - Calibrar variables de posiciones: 1h 30min
 * - Movimientos, Aruino: 5h 30min
 * - Interfaz, sin nada: 1h
 * - Evento de movimiento, acumular, sumar tiempo... : 6h
 * - Evento de hacer todos los movimientos: 2h
 * - Proceso de cunta atras y progress bar: 1h 30min
 * - Pantalla de carga: 3h
 * - Auto-detectar pueto de Arduino: 1h
 * - Generar random: 15min
 * - Controles de borrar acumulados: 1h 30min
 * - Clase de Utils: 30min
 * - Clase de Camara: 1h
 * - Detector de color: 5h
 * - Calibrar coodenadas de colores: 1h
 * - Documentar: 4h
 * - Aprender Doxigen: 2h
 * - Diseñar imagenes: 1h 30min
 * <br><br>
 * - Total software: 38h 15min
 *  
 * \section Total Total
 * <h2>50h 35min</h2> Aproximada, pues no me he conometrado en cada parte. 
 */


/********************************************************************************************************/