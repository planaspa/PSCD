/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio2;

import java.util.concurrent.Semaphore;

/**
 * 
 * @author Pablo
 * Implementaci�n del programa definido en la pr�ctica estableciendo una pol�tica
 * de bloqueo a nivel de tabla de la base de datos. Es decir, el acceso a cada una
 * de las tablas se realiza en exclusi�n mutua. Sin embargo, dos o m�s procesos
 * distintos pueden modificar diferentes tablas de forma concurrente.
 */
public class Ejercicio2 {

	/*
	 * Constantes del ejercicio
	 */
	private final static int REPETICIONES = 20;	//N�mero de veces que se realiza el ejercicio
	private final static int NUM_TAREAS = 10;	//N�mero de tareas que se quieren ejecutar
	
	/**
	 * M�todo de main
	 */
	public static void main(String[] args) {

		// Creaci�n de la base de datos y del vector de hilos
		BaseDeDatos bd = new BaseDeDatos();
		Thread [] tareas = new Thread [NUM_TAREAS];
		
		//Creaci�n del vector de sem�foros
		Semaphore [] mutex_vector = new Semaphore [3];
		for (int i=0; i<mutex_vector.length; i++)mutex_vector[i] = new Semaphore (1);
		
		//Creaci�n de un vector que acumule los tiempos
		long [] tiempos = new long [REPETICIONES];
        
		//Se ejecuta <REPETICIONES> veces lo mismo para tener datos estad�sticos m�s fiables
		for (int j=0 ; j<REPETICIONES; j++){
			/*
			 * Inicializaci�n de la base de datos
			 */
	        for (int i=0; i<100; i++){
	        	int clave = i+1001;
	        	bd.insertarRegistro(clave, "Nombre_"+clave+"_0", "Apellidos_"+clave+"_0",
	        			"1001"+clave, "C\\ Mar�a de Luna "+clave+"_0");
	        }
	        
	        // Creaci�n del vector de threads
	        for (int i=0;i<tareas.length;i++) 
	        	tareas [i] = new Thread (new Proceso (bd, mutex_vector));
	        
	        /*
	         * Inicio de todos los hilos
	         */
	        long tiempoInicio, totalTiempo;
	        tiempoInicio = System.currentTimeMillis();
	        for (int i=0;i<tareas.length;i++) tareas [i].start();
	        //Esperamos a que todos hayan terminado
	        for (int i=0;i<tareas.length;i++){
	        	try {tareas[i].join();} 
	        	catch (InterruptedException e) {e.printStackTrace();}
	        }
	        // Se calculan tiempos, se guardan y se muestran por pantalla
	        totalTiempo = System.currentTimeMillis() - tiempoInicio;
	        System.out.println("Tiempo transcurrido: "+totalTiempo);
	        tiempos [j] = totalTiempo;
		}
		
		/*
		 * Calculo de los datos estad�sticos: media y desviaci�n t�pica
		 */
		//Media
		long media = 0;
		for (int i = 0; i<tiempos.length; i++) media = tiempos[i] + media;
		media = media/tiempos.length;
		System.out.printf("La media de los tiempos es = %d %n", media);
		
		//Desviaci�n t�pica
        Long desviacion = new Long (0);
        for (int i = 0; i<tiempos.length; i++){
			desviacion = (media - tiempos[i])*(media - tiempos[i]) + desviacion;
		}
        desviacion = desviacion/tiempos.length;
        desviacion = (long) Math.sqrt(desviacion.doubleValue());
        System.out.printf("La desviaci�n t�pica de los tiempos es = %d %n", desviacion);
	}
}
