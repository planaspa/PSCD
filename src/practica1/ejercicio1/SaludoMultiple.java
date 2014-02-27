/**
* @author R. Trillo
* @version
* @date 11/09/2011
*/

package practica1.ejercicio1;

import practica1.ejercicio1.SaludoThread;

/** 
* Devuelve void. 
* Se crean tres threads del tipo SaludoThread y se lanza su ejecución llamando a sus métodos start()
*
*/

public class SaludoMultiple{
    public static void main(String args[]){
        SaludoThread t1,t2,t3;

        //Creamos los threads
        t1 = new SaludoThread("Hola, soy Thread 1", 151);
        t2 = new SaludoThread("Hola, soy Thread 2", 150);
        t3 = new SaludoThread("Hola, soy Thread 3", 100);

        //Arrancamos los threads, es decir, lanzamos la ejecución de los tres procesos.
        t1.start();
        t2.start();
        t3.start();
    }
}
