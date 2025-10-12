/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_martin_jenkins;
import java.util.Scanner;                               //Comando para añadir el Scanner que leera los datos ingresados por usuario
import java.util.ArrayList;                             //Comando para añadir las funciones de las listas dinamicas.

public class EFT_S9_Martin_Jenkins {

private static Scanner usuario = new Scanner(System.in);
private static final int total_asientos = 30;                        //Variable que almacena la capacidad maxima de asientos del teatro
private static final String nombre_lugar = "Teatro Moro";            //Variable que almacena el nombre del teatro
private static final boolean[]  asientos = new boolean[total_asientos];    //Arreglo booleano que almacena los asientos ocupados y disponibles. (True = ocupado; False = disponible)

private static final int monto_vip = 50000;
private static final int monto_palco = 30000;
private static final int monto_plateab = 20000;
private static final int monto_plateaa=15000;
private static final int monto_galeria=10000;

private static final int monto_asientos = 10000;                     //Variable que almacena el costo base de un asiento 


private static final double dscto_kids =         0.95;               //De 0 a 12 años, se considera niño
private static final double dscto_mujer =        0.93;              // True, para mujer. False, para hombre. Se descuenta solo si es mujer, y no es kids, ni estudiante, ni tercera edad.
private static final double dscto_estudiante =   0.75;              //De 12 a 18, se considera estudiante.
private static final double dscto_3edad =        0.7;               // De 65 en adelante, adulto mayor.        

private static int asientos_disponibles;                       //Variable que indica los asientos disponibles para la compra actual.
private static int asientos_ocupados;                          //Variable que indica los asientos ocupados para la compra actual.
private static int asiento_seleccionado;                       //Variable para almacenar asiento seleccionado de la compra actual, la cual de ejecutarse, se almacena en las listas
private static String tipo_asiento_seleccionado;
private static double total_compra_actual;                     //Variable para almacenar el decimal del total de la compra actual, la cual de ejecutarse, se almacena en las listas



private static int edad;                                       //Variable para almacenar la edad del usuario en la compra actual.
                                                               
private static boolean genero;                                 //Variable para almacenar genero del usuario en la compra actual. True, para mujer. False, para hombre
private static String generoS;


private static ArrayList<Integer>  lista_asientos = new ArrayList<>();   // Lista para almacenar el numero de asiento reservados por cada compra
private static ArrayList<Double>   lista_totales = new ArrayList<>();    // Lista para almacenar los montos totales de cada compra
private static ArrayList<String>  lista_tipos = new ArrayList<>();      //Lista que almacena que tipo de asiento se compró, platea, galeria, etc.


public static void seteador_arreglos(){                        //este metodo permite definir el valor inicial de los arreglos, antes de comenzar con el programa.
       for(int i = 0; i <= total_asientos-1; i++){
           asientos[i] = false;                         //El estado TRUE indica que el asiento está ocupado, y FALSE que está disponible. ¿Esta ocupado? si (true) no (false)

       }
   } 
public static void mostrar_asientos(){                  //esta clase imprime los asientos disponibles
    System.out.println("");
    for(int i= 0; i<=total_asientos-1; i++) {
        int a;
        
        if(i==0){
            System.out.println("");
            System.out.print("VIP:          ");
        }
        if(i==4){
            System.out.println("");
            System.out.print("PALCO:        ");
        }
        if(i==9){
            System.out.println("");
            System.out.print("P. BAJA:      ");
        }
        if(i==14){
            System.out.println("");
            System.out.print("P. ALTA   :   ");
        }
        if(i==19||i==24){
            System.out.println("");
            System.out.print("GALERIA  :    ");
        }
        if(asientos[i]){
            System.out.print("[X] ");                 //Los asientos ocupados los muestra así: [X]  
        }
        else{
            a=i+1;
            System.out.print("["+a+"] ");             //Los asientos disponibles los muestra asi [numeroasiento]. Se le suma 1 a la i, para que la posicion del arreglo calze (posicion 0 = asiento 1)
        }

    }
    System.out.println("");
}

public static int calcular_disponibles (){                     //Clase que calcula los asientos disponibles en el momento
    for(int i = 0; i<=total_asientos-1;i++) {

        if(!asientos[i]){
            asientos_disponibles++;
        }
    }
    return asientos_disponibles;                        //devuelve el total de asientos disponibles
    
}
public static int calcular_ocupados (){                        
    for(int i = 0; i<=total_asientos-1;i++) {           //Clase que calcula los asientos ocupados en el momento.
        
        if(asientos[i]){
            asientos_ocupados++;
        }
    }
    return asientos_ocupados;                           //devuelve el total de asientos ocupados
    
}

public static boolean validar_asiento (){
    //Clase para reservar un asiento. Comprueba si no está ocupado primero
    if (asiento_seleccionado<0||asiento_seleccionado>asientos.length) {
        return false;
    }                    
    return !asientos[asiento_seleccionado-1];

    
}


public static boolean eliminar_compra(){
    int largo_lista=lista_asientos.size();
    if (asiento_seleccionado>largo_lista||asiento_seleccionado<1) {
        System.out.println("Ups! La compra a eliminar no es valida. Por favor, elija una venta ya realizada");
        return false;
    }
    else{
        asientos[lista_asientos.get(asiento_seleccionado-1)]=false;
        lista_asientos.remove(asiento_seleccionado-1);
        lista_totales.remove(asiento_seleccionado-1);
        lista_tipos.remove(asiento_seleccionado-1);
        System.out.println("");
        System.out.println("Listo! Compra eliminada con exito");
        return true;
    }
 
}
public static void realizar_compra(){
       lista_asientos.add(asiento_seleccionado-1);
       asientos[asiento_seleccionado-1]=true;
       System.out.println("Listo! Has comprado el asiento "+asiento_seleccionado);
       if(asiento_seleccionado-1<5){
           lista_tipos.add("VIP");
           System.out.println("Tipo de asiento: VIP");
           tipo_asiento_seleccionado="VIP";
       }
       if(asiento_seleccionado-1>=5&&asiento_seleccionado-1<10){
           lista_tipos.add("PALCO");
           System.out.println("Tipo de asiento: PALCO");
           tipo_asiento_seleccionado="PALCO";
       }
        if(asiento_seleccionado-1>=10&&asiento_seleccionado-1<15){
           lista_tipos.add("P. ALTA");
           System.out.println("Tipo de asiento: Platea ALTA");
           tipo_asiento_seleccionado="PLATEA ALTA";
       }
       if(asiento_seleccionado-1>=15&&asiento_seleccionado-1<20){
           lista_tipos.add("P. BAJA");
           System.out.println("Tipo de asiento: Platea Baja");
           tipo_asiento_seleccionado="PLATEA BAJA";
       }
       if(asiento_seleccionado-1>20){
           lista_tipos.add("GALERIA");
           System.out.println("Tipo de asiento: Galeria");
           tipo_asiento_seleccionado="GALERIA";
       }
       
   }
public static void almacenar_monto(){
    if(asiento_seleccionado-1<5){                                //VIP
        total_compra_actual=monto_vip;
    }
    if(asiento_seleccionado-1>=5&&asiento_seleccionado-1<10){   //PALCO
        total_compra_actual=monto_palco;
    }
    if(asiento_seleccionado-1>=10&&asiento_seleccionado-1<15){  //Platea Alta
        total_compra_actual=monto_plateaa;
    }
    if(asiento_seleccionado-1>=15&&asiento_seleccionado-1<20){ //Platea baja
        total_compra_actual=monto_plateab;
    }
    if(asiento_seleccionado-1>20){
        total_compra_actual=monto_galeria;                      //Galeria
    }
    
    if(genero && edad>=18 && edad<=65){
        total_compra_actual=total_compra_actual*dscto_mujer;
    }
    if(edad<12){
        total_compra_actual=total_compra_actual*dscto_kids;
    }
    if(edad>=12 && edad<18){
        total_compra_actual=total_compra_actual*dscto_estudiante;
    }
    if(edad>65){
        total_compra_actual=total_compra_actual*dscto_3edad;
    }
      lista_totales.add(total_compra_actual);           // no borrar
}
public static void mostrar_menu(){                                     //Clase para mostrar el texto del menu de opciones
    System.out.println("");
    System.out.println("Bienvenido al "+nombre_lugar);
    System.out.println(" A continuacion, te mostramos el menu de opciones");
    System.out.println("");
    System.out.println("Menu de Opciones:");
    System.out.println("1. Comprar una entrada");
    System.out.println("2. Gestionar tus compras");
    System.out.println("3. Salir");
    System.out.println("Por favor, digita la opcion que quieres ejecutar");
    System.out.println("");
    
}
public static void mostrar_menu_gestion(){
                  System.out.println("Que deseas realizar?");
                  System.out.println("");
                  System.out.println("Opcion 1: Eliminar una compra");
                  System.out.println("Opcion 2: Volver al menu principal");
                  System.out.println("");
                  System.out.println("Por favor, digite una opcion");
                  System.out.println("");
}
public static void imprimir_boleta(){
    System.out.println("¡Gracias por comprar en "+nombre_lugar+"!");
    System.out.println("Recibo de tu compra");
    System.out.println("-----------------------------------------------");
    System.out.println("");
    System.out.println("Asiento comprado:                    "+asiento_seleccionado);
    System.out.println("Tipo de asiento:                    "+tipo_asiento_seleccionado);
    if(genero){
        System.out.println("Genero:                             Mujer");
        System.out.println("Aplica Descuento Mujer:             7% de Descto.");
    }
    else{
        System.out.println("Genero:                             Hombre");
    }
    System.out.println("Edad:                               "+edad);
    if (edad<13) {
        System.out.println("Aplica Descuento Kids:              5% de Descto.");
    }
    if (edad<=13 && edad<=18) {
        System.out.println("Aplica Descuento Estudiante:        25% de Descto.");  
    }
    if(edad>=65){
        System.out.println("Aplica Descuento Adulto Mayor:      30% de Descto.");
    }
    System.out.println("");
    System.out.println("Monto total:                        "+total_compra_actual);
    System.out.println("");
    System.out.println("------------------------------------------------------");
}
public static void insertar_datos(){
    System.out.println("Genial! Ahora, se solicitaran tus datos para calcular el monto de tu compra");
    System.out.println("");
    System.out.println("Por favor, digite su edad");
    do{
        try
        {
        edad=usuario.nextInt();                         //Leer edad
        }
        
        catch(java.util.InputMismatchException e)
        {
        System.out.println("Por favor, ingrese una edad correcta");
        System.out.println("");
        usuario.next();
        edad=-1;
        }
        if(edad<0||edad>120){
            System.out.println("Por favor, ingrese una edad correcta");
        }
    }
    while(edad<0||edad>120);

    System.out.println("");  
    do{
        System.out.println("Por favor, digite M para Mujer, y H para hombre");
        generoS=usuario.next();                             //Leer genero. Lo lee en String, y lo convierte a boolean para un manejo mas facil. 
    }    
    while(!generoS.equalsIgnoreCase("h")&&!generoS.equalsIgnoreCase("m"));
    if(generoS.equalsIgnoreCase("h")){
        genero=false;
    }
    if(generoS.equalsIgnoreCase("m")){
        genero=true;
    }
    
    }

public static void imprimir_compras_anteriores(){
    System.out.println("A continuacion, se mostraran las compras realizadas anteriormente");
    System.out.println("");
    for(int i = 0; i <= lista_asientos.size()-1; i++)
        {
        int a=i+1;
        System.out.println("");
        System.out.println("------------------");
        System.out.println("Compra numero: "+a);
        int c=lista_asientos.get(i);
        System.out.print("Asiento comprado: ");
        System.out.println(c+1);
        System.out.println("Tipo de asiento: "+lista_tipos.get(i));
        System.out.println("Monto de compra: "+lista_totales.get(i));
        System.out.println("------------------");
        }
    System.out.println("");
    }
    

//INCLUIR UNA CLASE PARA TOMAR DATOS y calcular precio???
    public static void main(String[] args){
         seteador_arreglos();
        

        int opcion;
        do{
        mostrar_menu();  
        opcion = usuario.nextInt();
            System.out.println("");
                                                    //Validar dato correcto, que no sea letra o algo
        switch(opcion) {
              case 1 ->     {       //Con esta opción, se ejecuta la secuencia de compra, preguntando el asiento a comprar, y luego pidiendo los datos, para finalmente imprimir boleta y almacenar.
                
                if(calcular_disponibles()<=0)
                {
                    System.out.println("Lo sentimos, pero no quedan asientos disponibles para comprar");
                }
                else
                {
                    do {
                    System.out.println("A continuacion, se mostratran los asientos disponibles");
                    mostrar_asientos();
                    System.out.println("");
                    System.out.println("Por favor, elige un asiento disponible");    
                      try {
                        asiento_seleccionado = usuario.nextInt();
                      }
                      catch (java.util.InputMismatchException e){
                      System.out.println("Ups. Por favor, seleccione un asiento valido");
                          System.out.println("");
                        usuario.next();
                        asiento_seleccionado =-1;
                      }
                    } while (!validar_asiento());
                    insertar_datos();
                    realizar_compra();
                    System.out.println("");
                    almacenar_monto();
                    imprimir_boleta();
                    }       
                }               //Fin de la opcion 1    
              
              case 2 ->     {
                  imprimir_compras_anteriores();
                  if(lista_asientos.isEmpty()){
                      System.out.println("Ups! No ha realizado compras anteriormente. Para poder gestionar sus compras, debe haber comprado antes");
                      System.out.println("");
                    }
                  else{
                      do
                    {
                        mostrar_menu_gestion();    
                    try{
                        opcion=usuario.nextInt();
                        }
                    catch(java.util.InputMismatchException e)
                        {
                      System.out.println("Ups, por favor, ingresa una opcion valida");
                      usuario.next();
                        }
                        if(opcion==1)
                            {
                         do
                            {
                            opcion=2;
                            System.out.println("Por favor, ingresa el numero de tu compra a eliminar");
                            try
                            {
                                asiento_seleccionado = usuario.nextInt();
                            }
                            catch (java.util.InputMismatchException e)
                            {
                                System.out.println("Ups. Por favor, seleccione un asiento valido");
                                System.out.println("");
                                usuario.next();
                                asiento_seleccionado =-1;
                            }
                        
                            }
                        while(!eliminar_compra());
                            }
                    }
                  while (opcion!=2);
                  }

                            }
              
              case 3 ->                 //Esta opción permite salir del menu y terminar el programa     
              {
                  opcion=4;
                  System.out.println("Hasta pronto!"); //Mejorar mensaje de despedida
                  
              }
              default -> System.out.println("Opción no valida. Por favor, digite una opción valida");
            
        }
        }
        while(opcion!=4);
        usuario.close();
     //FIN DEL PROGRAMA
        
        
    }
}
