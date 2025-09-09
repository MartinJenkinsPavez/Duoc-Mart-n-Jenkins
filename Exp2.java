
package exp2;

import java.util.Scanner;
public class Exp2 {


    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

/*
¡Hola! ¿Que tal? Esta es mi cuarta entrega de fundamentos de programación. Soy Martín Jenkins
Respecto a no haber entregado el pseudo codigo la vez anterior, simplemente no se me ocurrió y pensé que podría afectar la nota. Para la proxima sumativa lo haré.
No pude asistir a la ultima clase, así que si dijeron algo clave ahí que se note ahora en esta entega, quizas sea por eso, la semana la tuve con harto trabajo y con fiebre.
       
Hoy re hice el teatro moro de la vez pasada, aunque copie algunos textos para hacerlo mas facil
       
Como abstracción, utilicé exactamente el mismo metodo de la entrega de la semana pasada, solo se agregaron elementos para mayor complejidad

       
Para que estos elementos nuevos funcionen, consideré lo siguente
       Para que el programa pueda permitir varias compras, considere un do while para almenos ejecutarlo una vez, y establecer una condicion para que siga o salga, dependiendo lo que diga el usuario
       Luego, detecta el precio de un array dependiendo de la seleccion de asiento.
       Luego, solicita edad, la valida y dependiendo de la edad, aplica o no descuento y muestra el resumen
       finalmente, se vuelve a ejecutar el codigo del do while
       
       Entendí de mejor forma el while de la validación de datos enteros. Se considera el while true, para que este se ejecute solo si una expresion sea verdadera
       La funcion scanner has next int solo se toma como verdadera si el usuario digita un entero en el proximo escaner
       Luego, se puede detectar el dato del usuario.
       por ende, se detecta un if con esta condicion, y si se cumple, se ejecuta un break para salir del while.
       si no, se usa un else que indique el error al usuario.
       
       
       

       
*/       

        int[]       precios_general = new int[4]; //Arreglo donde se almacenaran los precios base de las entradas, similar a la experiencia anterior
        String[]    nombre_entrada  = new String[4]; //Arreglo donde se almacenaran los nombres de cada entrada
        int         validacion_iteracion=0;//Variable que indica que el ciclo iterativo debe repetirse y continuar
        int         tipo_entrada=0;         //Variable para representar el tipo de asiento/entrada seleccionada
        double      monto_total=0;          //Variable que representará el monto total a pagar. Debe ser double para detectar decimales, ya que conlleva una multiplicacion por decimal.
                                            // Si es 1 o mayor, se sigue el codigo
                                            //si es 0 o menor, termina el codigo
        int         edad=0;
       
        precios_general[0] = 35000; //Monto de entrada VIP, para Público General.
	precios_general[1] = 25000; //Monto de entrada platea alta, para Público General.
	precios_general[2] = 15000; //Monto de entrada platea baja, para Público General.
	precios_general[3] = 11000; //Monto de entrada Palcos, para Público General.
       
        nombre_entrada[0] = "VIP";
	nombre_entrada[1] = "en Platea baja";
	nombre_entrada[2] = "en Platea alta";
	nombre_entrada[3] = "en Palcos";
       
       
        
       
       /*Aquí estará definido todo el codigo. Estará dentro de un do while,
       ya que así corre codigo al menos una vez, y luego pregunta si se quiere salir o no.
       
       Además, uso un if, que almacena todo el codigo del teatro moro, y se ejecuta solo si el usuario digita 1, representando que quiere comprar
       si no, se salta todo el codigo y sale del while, ya que este solo se repite si el valor de la variable que representa la intencion de comprar, es 1
       del while.
        
       */
        do //do while que almacena el codigo
        {
            System.out.println( "Bienvenido al Teatro Moro."); //menu principal
            System.out.println(".");
            System.out.println(".");
            System.out.println("MENU PRINCIPAL___");
            System.out.println("- Comprar una entrada");
            System.out.println("- Salir");
            System.out.println(".");
            System.out.println("Por favor, digite 1 si desea comprar entrada. Si desea salir, presione cualquier otro numero ");
            
            while (true) {      //while true, que valida que el dato ingresado sea un entero, y se repite hasta que se ingrese un numero valido
                if (scanner.hasNextInt()){
                validacion_iteracion  = scanner.nextInt(); //Aqui el usuario indica 1 si quiere comprar, y si no, cualquier  otro numero. 
                break;
                }
                
                else{
                System.out.println("Por favor, digite un numero valido");
                scanner.next();
                }
                
            }
            
                if (validacion_iteracion == 1) //este if almacena todo el codigo de la compra de teatro moro, y solo se activa si el cliente manifiesta que desea comprar. (osea que haya digitado 1)
                {
                    System.out.println( "¡Genial! La siguente función presenta los 4 tipos de entrada");
                   
                    System.out.println("A continuación, se le indicarán las alternativas disponibles para su próxima función, con sus precios asociados");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("."); //Tabla hecha rudimentariamente, jugando con los espacios, y utilizando las posiciones de los arreglos, en caso que se desearan cambiar los precios se mantenga la tabla, siempre que se respeten la cantidad de digitos                    
                    System.out.println("_____________________________________________________");
                    System.out.println(" / Opcion (1): VIP           : $"+precios_general[0]+" /");
                    System.out.println(" / Opcion (2): Platea baja   : $"+precios_general[1]+" /");
                    System.out.println(" / Opcion (3): Platea alta   : $"+precios_general[2]+" /");
                    System.out.println(" / Opcion (4): Palcos        : $"+precios_general[3]+" /");
                    System.out.println("_____________________________________________________"); 
                    System.out.println(".");
                    System.out.println("."); //A continuación, se le solicita al usuario que elija la entrada que desee, asignado cada numero a un tipo de entrada
                    System.out.println("A continuacion, se le solicitara seleccionar la opcion que mas le acomode para las entradas");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println( "Para entrada VIP, digite 1");
                    System.out.println( "Para Platea baja, digite 2");
                    System.out.println( "Para Platea alta, digite 3");
                    System.out.println( "Para Palcos     , digite 4");
                    System.out.println( "Por favor, a continuacion digite el numero de su seleccion");
                    
                    while (true) { //Esta función compara si el siguente dato ingresado por el usuario calza con el solicitado, y pasa al else si no calza. 
                        
                        if (scanner.hasNextInt()) { //esta funcion arroja verdadero si el dato digitado por el usuario es entero. Por tanto, el if solo se activa si se digita un entero.
                        tipo_entrada  = scanner.nextInt();
                            if (tipo_entrada>0&&tipo_entrada<=4) { //adicionalmente, este if revisa que el dato solicitado solamente sea uno valido como opcion, osea, del 1 al 4
                                break;
                            }
                            else{
                            System.out.print("Por favor, un numero valido");    
                            }
                            
                        }

                        
                        else {     
                        System.out.print("Por favor, digite correctamente el numero de su seleccion");
                        scanner.next();
                        }
                    }
                   
                    tipo_entrada=tipo_entrada-1;
                    System.out.println("¡Genial! A continuación, por favor indique su edad"); //Aqui se le solicita la edad al usuario
                    
                    while (true) { //otro while true, que permite validar que el dato del usuario ingresado sea correcto
                        
                        if (scanner.hasNextInt()){
                        edad= scanner.nextInt();
                            if(edad>0&&edad<100){//además, solo se puede acceder al break de este while si la edad es mayor a 0, o menor a 100. Considere menor a 100 solamente para que no se ponga como edad 10000 o numeros muy grandes
                                break;                               
                                }
                            else{
                            System.out.println("Por favor, ingrese una edad Valida");
                            }
                        }
                        else{
                        System.out.println("Por favor, ingrese una edad valida");
                        scanner.next();
                        }
                    }
                    //A continuación, se calcula el descuento o la ausencia de este, y se presenta el resumen, para los 3 casos. Si es estudiante, adulto mayor, o cliente general.        
                        if (edad<=18){ //Estudiante
                            System.out.println("Usted se encuentra aplicable para el descuento de ESTUDIANTE. Se le rebajará de su monto total");
                            monto_total=precios_general[tipo_entrada]*0.9;
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("A continuación, se le inda el resumen de su compra");
                            System.out.println(".");
                            System.out.println("Tipo de entrada:        "+nombre_entrada[tipo_entrada]);
                            System.out.println("Precio base entrada:    "+precios_general[tipo_entrada]);
                            System.out.println("Descuento aplicado:    ESTUDIANTE (10%)");
                            System.out.println(".");
                            System.out.println("MONTO TOTAL A CANCELAR: "+monto_total);
                        }
                        else if(edad>=65) { //Adulto mayor
                            System.out.println("Usted se encuentra aplicable para el descuento de ADULTO MAYOR. Se le rebajará de su monto total");
                            monto_total=precios_general[tipo_entrada]*0.85;
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("A continuación, se le inda el resumen de su compra");
                            System.out.println(".");
                            System.out.println("Tipo de entrada:        "+nombre_entrada[tipo_entrada]);
                            System.out.println("Precio base entrada:    "+precios_general[tipo_entrada]);
                            System.out.println("Descuento aplicado:    ADULTO MAYOR (15%)");
                            System.out.println(".");
                            System.out.println("MONTO TOTAL A CANCELAR: "+monto_total);
                        }
                        else{ //Cliente general
                            System.out.println("¡Excelente");
                            monto_total=precios_general[tipo_entrada]*0.85;
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("A continuación, se le inda el resumen de su compra");
                            System.out.println(".");
                            System.out.println("Tipo de entrada:        "+nombre_entrada[tipo_entrada]);
                            System.out.println("Precio base entrada:    "+precios_general[tipo_entrada]);
                            System.out.println("Descuento aplicado:    SIN DESCUENTO APLICADO");
                            System.out.println(".");
                            System.out.println("MONTO TOTAL A CANCELAR: "+monto_total);                            System.out.println("Usted se encuentra aplicable para el descuento de ADULTO MAYOR. Se le rebajará de su monto total");
                            monto_total=precios_general[tipo_entrada];
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("A continuación, se le inda el resumen de su compra");
                            System.out.println(".");
                            System.out.println("Tipo de entrada:        "+nombre_entrada[tipo_entrada]);
                            System.out.println("Precio base entrada:    "+precios_general[tipo_entrada]);
                            System.out.println("Descuento aplicado:    SIN DESCUENTO APLICADO");
                            System.out.println(".");
                            System.out.println("MONTO TOTAL A CANCELAR: "+monto_total);
                        }
                        System.out.println(".");
                        System.out.println("¡Gracias por su compra!");
                        System.out.println("A continuación, serás redireccionado al menu principal");
                    
                }
            
                
                
            else {
            //Mensaje de salida
            validacion_iteracion=0;
            System.out.println( "¡Gracias por Contar con el Teatro Moro");
            }
                
        }
        while (validacion_iteracion == 1);
       
       
       
       
       
       
    }
    
}
