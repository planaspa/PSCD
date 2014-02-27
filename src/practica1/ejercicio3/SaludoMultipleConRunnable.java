/**
* @author R. Trillo
* @version
* @date 11/09/2011
*/

package practica1.ejercicio3;

import practica1.ejercicio3.SaludoRunnable;

public class SaludoMultipleConRunnable{


   /** 
   * Devuelve void. 
   * Se crean tres objetos del tipo SaludoRunnable y a cada uno de ellos se le asocia un thread.
   * A continuación se lanza la ejecución de los tres threads llamando a sus métodos start().
   * Después de arrancar la ejecución de un thread realizamos una llamada a su método join(). 	
   *
   */

   public static void main(String args[])throws InterruptedException{
         Thread t1,t2,t3;
         SaludoRunnable r1,r2,r3;	

         //Creamos los objetos Runnable
         r1 = new SaludoRunnable ("Hola, soy A", 10);
         r2 = new SaludoRunnable ("Hola, soy B", 15);
         r3 = new SaludoRunnable ("Hola, soy C", 9);

        //Creamos los threads para que se ejecuten los
        //objetos Runnable

        t1 = new Thread(r1);
        t2 = new Thread(r2);
        t3 = new Thread(r3);

        // Arrancamos los threads llamando a su método start
	// Después de arrancar realizamos una llamada a su método join()
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }   
}

