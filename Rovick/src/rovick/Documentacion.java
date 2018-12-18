//      ____                          ____           _                  _                   _ 
//     |  _ \    __ _    __ _        |  _ \   _ __  (_)  _ __     ___  (_)  _ __     __ _  | |
//     | |_) |  / _` |  / _` |       | |_) | | '__| | | | '_ \   / __| | | | '_ \   / _` | | |
//     |  __/  | (_| | | (_| |  _    |  __/  | |    | | | | | | | (__  | | | |_) | | (_| | | |
//     |_|      \__,_|  \__, | (_)   |_|     |_|    |_| |_| |_|  \___| |_| | .__/   \__,_| |_|
//                      |___/                                              |_|                

/** \mainpage Página principal
 *
 * \section Concepto Concepto del proyecto
 *
 *Este proyecto es el trabajo final del grado superior DAM (Desarrollo de Aplicaciones en Multiplataforma), 
 *cursado en el centro Gregorio Fernández (Valladolid), realizado por Víctor Pastor Urueña.<br>
 *El objetivo es crear un robot real y funcional que cumpla el requisito de resolver un cubo de Rubik.
 *<h3>Quiero destacar</h3>
 * Que en la realizacion de éste, he aprovechar partes de otros proyectos como por ejemplo,
 * el modelo 3D de las piezas o la clase de Java que genera el algoritmo de resolución.<br>
 * Más detalles sobre esto en el página de [Recursos](@ref Rescusos)
 * 
 * \section Demostracion Demostración
 * [Vídeo de demostración](https://www.youtube.com/watch?v=c-J2XP4FGs8&feature=youtu.be)
 * 
 * \section Requisitos Requisitos
 * - Cubo de rubik sin pegatina de la marca en el centro blanco.
 * - 2 puetos USB's libres.
 * 
 * \section Instrucciones  Instrucciones
 * - Conectar el Arduino y la cámara a los USB's del ordenador <b>antes</b> de cargar la aplicación.
 * - Si estás con un portatil, asegurate de que se usa la cámara correcta.
 * - Ejecutar el ".jar" de la aplicacion Rovick
 * - Colocar el cubo en el gancho inferior de forma centrada.
 * - Disfrutar de esta increíble aplicación.
 *  
 * \section Enlaces Enlaces
 * - [Página web de Víctor Pastor](http://victorpastor.com/)
 * - [GitHub del proyecto](https://github.com/pokevik21/Rovick)
 */

/********************************************************************************************************/

//         ____           _                                 
//        / ___|  _   _  (_)   __ _     _   _   ___    ___  
//       | |  _  | | | | | |  / _` |   | | | | / __|  / _ \ 
//       | |_| | | |_| | | | | (_| |   | |_| | \__ \ | (_) |
//        \____|  \__,_| |_|  \__,_|    \__,_| |___/  \___/ 


/**
 * \page Uso Guía de uso
 * Descripción de como utilizar la interface.
 * \section Botones Botones de movimiento
 * ![botón de movimiento] (DoxyRecursos/images/R.png)
 * Cuando lo pulsamos y no esta activado el CheckBox de "Según pulsas", se acumulará 
 * visualizándose en el el área de texto:
 * ![Ejemplo boton movimiento] (DoxyRecursos/images/BotonesMov_ej.png)
 * Hay unos botones a la derecha del área de texto que sirven para borrar los movimientos:<br>
 * el primer movimiento, el ultimo o todos.<br>
 * 
 * Cada vez que añadimos un movimiento acumulado o lo eliminamos, se ajusta el tiempo y
 * el número de movimientos en el contador de tiempo, arriba a la izquierda.
 * ![Contador de tiempo] (DoxyRecursos/images/contadoTiempoMovs.png)
 * 
 * \section Otros_controles Otros controles
 * <h3>Botón "Soltar"</h3>
 * Sirve para que el robot suelte el cubo.<br>
 * Para que lo vuelva a agarrar solo tienes que dar a un movimiento y antes de hacerlo, lo agarrará.
 * ![Botón soltar] (DoxyRecursos/images/BotonSoltar.png)
 * 
 * <h3>Botón "Parar"</h3>
 * Cuando está realizando todos los movimientos y analizando el cubo,
 * este botón se activa y sirve para parar dichos procesos.
 * ![Botón Parar] (DoxyRecursos/images/BotonParar.png)
 * 
 * <h3>CheckBox "Luz"</h3>
 * Controla el apagado y el encendido de la luz.
 * ![CheckBox Luz] (DoxyRecursos/images/BLuz.png)
 * 
 * <h3>Botón "Gen. Random"</h3>
 * Genera el número de movimientos aleatorios que tenga el Spinner de al lado,
 * por defecto 25.
 * ![Botón Gen. Random] (DoxyRecursos/images/GenRandom.png)
 * 
 * <h3>Botón "Realizar movimientos"</h3>
 * Realiza todos los movimientos acumulados en el área de texto.<br>
 * Empezará un proceso de cuenta atrás e irá avanzando la 
 * barra de progreso hasta que finalice.
 * ![Bóton Realizar movimientos] (DoxyRecursos/images/RMovs.png)
 * 
 * <h3>Botón "Resolver cubo"</h3>
 * Analiza el cubo usando la cámara del robot. Si detecta todos los colores, 
 * genera el algorítmo para resolverlo y lo pone en el área de texto.<br>
 * No obstante, hay que tener en cuenta el CheckBox "Sólo algoritmo".<br>
 * Si está activado, al terminar el análisis del cubo, 
 * mostrará los movimientos necesarios para su resolución.<br>
 * Si no lo está, lo resolverá automáticamente.  
 * ![Botón Resolver cubo] (DoxyRecursos/images/Rcubo.png)
 * 
 * <h3>Puerto</h3>
 * Informa del puerto al que se ha conectado.
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
 * Página donde se visualizan los diversos diagramas.
 * 
 * \section De_Uso De uso
 * 
 * \subsection Hacer_movimiento Hacer movimiento y acumular
 * ![Diagrama hacer movimiento] (DoxyRecursos/images/uso_clickMov.png)
 * \subsection Resolver_cubo Resolver cubo
 * ![Diagrama hacer movimiento] (DoxyRecursos/images/uso_resolverCubo.png)
 * \section color Reconocimiento de colores
 * Una de las primeras estrategias es desenfocar la cámara. Con ello conseguimos unos colores suavizados 
 * quedando una imagen como la siguiente:
 * ![Foto y coordenadas] (DoxyRecursos/images/coords.PNG)
 * Hacemos que el robot enseñe todas las caras a la cámara haciendo fotos de cada una.
 * Luego las analizamos de esta manera: (las coordenadas estan mapeadas)
 * - Por cada imagen cogemos las coordenadas centrales, y de esta posición cogemos un cuadrado de 40x40 pixeles. Hacemos la media de RGB de dicha zona, obteniendo así colores centrslrd.
 * - Por cada imagen vamos a cada coordenada iterando de arriba abajo y de izquierda a derecha.<br>
 * De cada posición cogemos un área de 10x10 pixeles y calculamos su media de RGB.
 * - A continuación calculamos una distancia entre la media y los colores centrales, con la fórmula:
 * ![] (DoxyRecursos/images/Formula.PNG)
 * - Nos quedamos con la distancia más pequeña, y ese será su color.
 */

/********************************************************************************************************/

//      ____                                                   
//     |  _ \    ___    ___   _   _   _ __   ___    ___    ___ 
//     | |_) |  / _ \  / __| | | | | | '__| / __|  / _ \  / __|
//     |  _ <  |  __/ | (__  | |_| | | |    \__ \ | (_) | \__ \
//     |_| \_\  \___|  \___|  \__,_| |_|    |___/  \___/  |___/


/**
 * \page Recusos Recusos
 * Página en la que se muestran los materiales usados.
 * 
 * \section Impresora_3D Impresora 3D
 * El modelo de la impresora 3D es una Anet-8,
 * pero se puede hacer con cualquier otra.
 * 
 * \section Modelo_3d Modelo 3D
 * [Model 3D] (https://www.thingiverse.com/thing:2471044)<br>
 * [Página Oficial] (http://www.rcr3d.com/)
 * 
 * \section material-precio Material-precio
 * |Material|Cantidad|Precio|
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
 * |Métrica M3-12 Tornillo cabeza avellanada|80|5,00€|
 * |Métrica 12 - Tuercas|40|3,00€|
 * |Tornillo perforante - 2mm|10|1,00€|
 * |Tira de LED's luz fría|1|12,36€|
 * |Cubo de Rubik|1|12,00€|
 * |TOTAL||147,32€|
 * 
 * \section clases_externas Clases externas
 * - [SolverCube](@ref SolveCube.java), principal<br>
 * Y las necesarias para que esta clase funcione:
 * - [TableGenerator](@ref TableGenerator.java)
 * - stage0.txt
 * - stage1.txt
 * - stage2.txt
 * - stage3.txt
 * 
 * Estas clases están sacadas de otro proyecto de gitHub:<br>
 * [GitHub del proyecto] (https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver)
 * \section Librerias Librerías
 * - Librería de Arduino, [PanamaHitek] (http://panamahitek.com/arduino-java/)
 * - Librería de WebCam, [webcam-capture] (http://www.webcam-capture.sarxos.pl/)
 */

/********************************************************************************************************/

//      _____   _                 _                            _                
//     | ____| | |   ___    ___  | |_   _ __    ___    _ __   (_)   ___    __ _ 
//     |  _|   | |  / _ \  / __| | __| | '__|  / _ \  | '_ \  | |  / __|  / _` |
//     | |___  | | |  __/ | (__  | |_  | |    | (_) | | | | | | | | (__  | (_| |
//     |_____| |_|  \___|  \___|  \__| |_|     \___/  |_| |_| |_|  \___|  \__,_|

/**
 * \page Electrónica Electrónica
 * Apartado de conexiones y hardware.
 * \section Montaje Montaje
 * El montaje está detallado en la página oficial del robot, apartado de hardware:<br>
 * [Página Oficial - Hardware] (http://www.rcr3d.com/hardware.html)<br>
 * 
 * Fotos de todas las piezas:<br>
 * ![Todas las piezas] (DoxyRecursos/images/TodasLasPiezas.jpg)
 * Puedes seguir el montaje hasta el punto número 6, luego seguir el siguiente esquema:
 * \section Esquemas Esquema
 * ![Esquema de conexiones] (DoxyRecursos/images/esquema_elecronica.png)
 * Para verlo con más perspectiva:
 * ![Pines] (DoxyRecursos/images/visual_pines.jpg)
 * Recomiendo fuertemente clasificar los cables, es decir, poner un distintivo en cada uno de ellos.<br>
 * Puedes usar unar herramienta llamada Dynamo o celo y papel:
 * ![Cables clasificados] (DoxyRecursos/images/cabes_cladificados.jpg)
 */

/********************************************************************************************************/

//       ____                     _   _   _                 
//      / ___|  _ __    ___    __| | (_) | |_    ___    ___ 
//     | |     | '__|  / _ \  / _` | | | | __|  / _ \  / __|
//     | |___  | |    |  __/ | (_| | | | | |_  | (_) | \__ \
//      \____| |_|     \___|  \__,_| |_|  \__|  \___/  |___/

/**
 * \page Créditos Créditos
 * Reconocimiento a las personas que han ayudado en el proyecto.
 * \section Testers Testers
 * - Jesús Martín Maderos
 * 
 * \section Ayudantes Ayudantes
 * - Máximo Castañeda (compañero de Atos).<br> Ayuda con el reconocimiento de colores.
 * 
 * \section impresion Impresion
 * - Ángel Urueña Miguel.<br> De la empresa [3DSouvenirs](https://drive.google.com/open?id=1Vcz_chFV9uM-dkAUWGEENjm8wSkfJtXG)
 * , ayudo imprimiendo el logo, la palabra "ROVICK" y el código QR. Ya que no podía imprimir en varios colores.
 * 
 * \section correctora Correctora
 * - Isabel Durán Pérez, ayuda con la ortografía y gramática de la documentacion.
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
 * Tiempo invertido en el proyecto.
 * \section Hardware Hardware
 * - Tiempo total de impresión: 98h 50min  [Más detalle...](https://github.com/pokevik21/Rovick/blob/master/Rovick/doc/DoxyRecursos/Tiempos.txt)
 * - Preparación de las piezas: 40min
 * - Retirar piezas y configurar siguiente: 20min
 * - Tiempo de calibrar la impresora: 1h(varias veces) 
 * - Lijado de piezas: 1h 30min
 * - Montaje del Robot: 2h 30min
 * - Calibrar un brazo: 2h 30min
 * - Calibrar el resto: 2h 30min
 * - Clasificar cables y conectarlos : 30min
 * - Ajustar cámara: 1h
 * - Soldar tira LEDs: 1h 30min
 * - Colocar LEDs: 20min
 * - Añadir el nombre, QR y logo : 1h 
 * <br><br>
 * - Total hardware: 13h 20min
 * \section Software Software
 * - Calibrar variables de posiciones: 1h 30min
 * - Movimientos, Arduino: 5h 30min
 * - Interfaz, sin nada: 1h
 * - Evento de movimiento, acumular, sumar tiempo... : 6h
 * - Evento de hacer todos los movimientos: 2h
 * - Proceso de cuenta atras y progress bar: 1h 30min
 * - Pantalla de carga: 3h
 * - Auto-detectar puerto de Arduino: 1h
 * - Generar random: 15min
 * - Controles de borrar acumulados: 1h 30min
 * - Clase de Utils: 30min
 * - Clase de WebCamController: 1h
 * - Detector de color: 5h
 * - Calibrar coordenadas de colores: 1h
 * - Documentar: 4h
 * - Aprender Doxigen: 2h
 * - Diseñar imágenes: 1h 30min
 * <br><br>
 * - Total software: 38h 15min
 *  
 * \section Total Total
 * <h2>51h 35min</h2> Aproximado. 
 */


/********************************************************************************************************/