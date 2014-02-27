/**
* @author R. Trillo
* @version
* @date 11/09/2011
*/

package practica1.ejercicio2;

import practica1.ejercicio2.SaludoRunnable;

/** 
* Devuelve void. 
* Se crean tres objetos del tipo SaludoRunnable y a cada uno de ellos se le asocia un thread.
* A continuación se lanza la ejecución de los tres threads llamando a sus métodos start().
*
*/

public class SaludoMultipleConRunnable{

    public static void main(String args[]){

         Thread t1,t2,t3;
         SaludoRunnable r1,r2,r3;
	 
	//Creamos los objetos Runnable
         r1 = new SaludoRunnable ("Hola soy R1", 4000);
         r2 = new SaludoRunnable ("Hola soy R2", 2000);
         r3 = new SaludoRunnable ("Hola soy R3", 100);

        //Creamos los threads para que se ejecuten los 
	//objetos Runnable
        t1 = new Thread(r1);
        t2 = new Thread(r2);
        t3 = new Thread(r3);

        //Arrancamos los threads
        t1.start();
        t2.start();
        t3.start();
    }   
}

