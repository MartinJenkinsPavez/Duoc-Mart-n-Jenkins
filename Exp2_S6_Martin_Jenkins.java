/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s6_martin_jenkins;

import java.util.Scanner;
public class Exp2_S6_Martin_Jenkins {


        // mostrar resumen actual de lo que tiene en "carrito"
        // usar un for para mostrar las opciones
        // usar un switch case para seleccionar las opciones
        // usar el default del switch case para los errores
    

    
    //Variables estaticas
        private  static int total_asientos = 21;
        private static String nombre_teatro = "Teatro Moro";
        private static int total_reservados = 0;
        private static int total_vendidos = 0;
        private static int total_disponibles = total_asientos;
        private static int costo_asientos = 5000;
        private static int monto_total = 0;
        private static boolean[]  asientos_disponibles = new boolean[total_asientos];
        private static boolean[]  asientos_reservados = new boolean[total_asientos];
        private static boolean[]  asientos_vendidos = new boolean[total_asientos];
       private static int asiento_seleccionado;

      
       
   public static void seteador_arreglos(){ //este metodo permite definir el valor de los arreglos, antes de comenzar con el programa.
       for(int i = 0; i <= total_asientos-1; i++){
           asientos_disponibles[i] = true;
           asientos_reservados[i] = false;
           asientos_vendidos[i] = false;
       }
   } 
   
   public static void contador_asientos(){
       total_disponibles=0;
       total_reservados=0;
       total_vendidos=0;
       
       for(int i = 1; i <= total_asientos-1; i++){
           if(asientos_disponibles[i]){
               total_disponibles++;
           }
           if(asientos_reservados[i]){
               total_reservados++;
           }
           if(asientos_vendidos[i]){
               total_vendidos++;
           }
           
       }
           System.out.println("");
           System.out.println("Asientos disponibles: "+total_disponibles);
           System.out.println("Asientos reservados: "+total_reservados);
           System.out.println("Asientos vendidos: "+total_vendidos);
           System.out.println("");
           System.out.println("");
   }

   public static boolean reservar_asiento(){ //este metodo permite reservar un asiento
       if(asientos_disponibles[asiento_seleccionado] && !asientos_reservados[asiento_seleccionado] && !asientos_vendidos[asiento_seleccionado]){ //antes de reservar, consulta en el arreglo si el asiento seleccionado está disponible. 
           asientos_reservados[asiento_seleccionado]=true; //si esta disponible, lo reserva cambiando el estado de los reservados, a true, y de los disponibles a false
           asientos_disponibles[asiento_seleccionado]=false;
           System.out.println("");
           System.out.println("Asiento "+asiento_seleccionado+" reservado con exito"); 
           contador_asientos();
           return true; //retorna un true por que fue capaz de hacer la reserva
 
            }
       System.out.println("Ups! El asiento seleccionado no está disponible, por favor, selecciona otro");//si no pasa al if para salir del metodo, retorna falso por no poder reservar el asiento
       System.out.println("");
       return false;
       
   }    
    
   public static boolean vender_asiento(){
       if(asientos_disponibles[asiento_seleccionado] && !asientos_vendidos[asiento_seleccionado]){
           asientos_vendidos[asiento_seleccionado]=true;
           asientos_disponibles[asiento_seleccionado]=false;
           asientos_reservados[asiento_seleccionado]=false;
           System.out.println("Asiento "+asiento_seleccionado+" comprado con exito");
           contador_asientos();
           return true;    
            }
       System.out.println("Ups! El asiento seleccionado no está disponible, por favor, selecciona otro");//si no pasa al if para salir del metodo, retorna falso por no poder reservar el asiento
       System.out.println("");
       return false;
   }
      
   public static boolean modificar_asiento(){
        if(asientos_reservados[asiento_seleccionado] && !asientos_vendidos[asiento_seleccionado]){
            asientos_reservados[asiento_seleccionado]=false;
            asientos_disponibles[asiento_seleccionado]=true;
            System.out.println("Asiento "+asiento_seleccionado+" modificado con exito");
            contador_asientos();
            return true;
            }
        System.out.println("Ups! El asiento seleccionado no está disponible, por favor, selecciona otro");//si no pasa al if para salir del metodo, retorna falso por no poder reservar el asiento
        System.out.println("");
        return false;
    }

   public static void mostrar_disponibles()
           {
       for (int i = 1; i <= total_asientos-1; i++){
           if(asientos_disponibles[i]){
               System.out.print(""+i+"[ ]");
           }
           if(asientos_vendidos[i]){
               System.out.print(""+i+"[X]");
           }
           if(asientos_reservados[i]){
               System.out.print(""+i+"[-]");
           }
           if(i==10){
               System.out.println("");
           }
           
       }
               System.out.println("");
       
   }
    
    
    
    
    public static void main(String[] args) {
    //Reseteador de los estados booleanos de los arreglos con un for    
    //do while con el codigo
    //default -> que desea seguir
    //if si desea seguir -> todo el codigo
    //else break
    //while desea seguir
   seteador_arreglos();
   Scanner usuario = new Scanner(System.in);
   int opcion;
   System.out.println("¡Bienvenido al "+nombre_teatro+"! A continuación, te mostramos el menú de opciones");
        //Crear metodo para resumen
       do {
      System.out.println("Menu de Opciones:");
      System.out.println("1. Reservar asiento");
      System.out.println("2. Modificar asiento");
      System.out.println("3. Comprar asiento");
      System.out.println("4. Imprimir boleta y salir");
      System.out.println("");
      System.out.println("Por favor, digita la opcion que quieres ejecutar");
      System.out.println("");
      opcion = usuario.nextInt();
      System.out.println("");
      switch (opcion) {
            case 1 -> {// Reservar un asiento
                System.out.println("OK, por favor, seleccione un asiento disponible");
                mostrar_disponibles();
                asiento_seleccionado =usuario.nextInt();
                //validar ingreso usuario
                reservar_asiento();
            }
            case 2 -> { //modificar asiento
                System.out.println("OK, por favor, seleccione un asiento reservado para modificar");
                mostrar_disponibles();
                asiento_seleccionado =usuario.nextInt();
                //validar ingreso usuario
                modificar_asiento();
           }
            case 3 -> {
                System.out.println("OK, por favor, seleccione un asiento disponible");
                mostrar_disponibles();
                asiento_seleccionado =usuario.nextInt();
                //validar ingreso usuario
                vender_asiento();
           }
            case 4 -> {
                System.out.println("gracias por comprar en teatro moro");
           }
            default -> System.out.println("Opción no valida. Por favor, digite una opción valida");
              
      }
      
      
     
       }
       while(opcion!=4);
     usuario.close();    
    }
      
               
    
    
    
    
    
    
    
    
    
    }


