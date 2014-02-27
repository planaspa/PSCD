/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio1;

/**
 * 
 * @author Pablo
 * Implementación del programa definido estableciendo una política de bloqueo a nivel de
 * base de datos completa. Es decir, el acceso para la modificación de cualquier dato
 * contenido en la base de datos se realiza en exclusión mutua (bloqueo a nivel de base
 * de datos).
 */
public class Ejercicio1 {
	
	/*
	 * Constantes del ejercicio
	 */
	private final static int REPETICIONES = 20;	//Número de veces que se realiza el ejercicio
	private final static int NUM_TAREAS = 10;	//Número de tareas que se quieren ejecutar
	
	/**
	 * Método de main
	 */
	public static void main(String[] args) {

		// Creación de la base de datos y del vector de hilos
		BaseDeDatos bd = new BaseDeDatos();
		Thread [] tareas = new Thread [NUM_TAREAS];
		
		//Creación de un vector que acumule los tiempos
		long [] tiempos = new long [REPETICIONES];
        
		//Se ejecuta <REPETICIONES> veces lo mismo para tener datos estadísticos más fiables
		for (int j=0 ; j<REPETICIONES; j++){
			/*
			 * Inicialización de la base de datos
			 */
	        for (int i=0; i<100; i++){
	        	int clave = i+1001;
	        	bd.insertarRegistro(clave, "Nombre_"+clave+"_0", "Apellidos_"+clave+"_0",
	        			"1001"+clave, "C\\ María de Luna "+clave+"_0");
	        }
	        
	        // Creación del vector de threads
	        for (int i=0;i<tareas.length;i++) tareas [i] = new Thread (new Proceso (bd));
	        
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
		 * Calculo de los datos estadísticos: media y desviación típica
		 */
		//Media
		long media = 0;
		for (int i = 0; i<tiempos.length; i++) media = tiempos[i] + media;
		media = media/tiempos.length;
		System.out.printf("La media de los tiempos es = %d %n", media);
		
		//Desviación típica
        Long desviacion = new Long (0);
        for (int i = 0; i<tiempos.length; i++){
			desviacion = (media - tiempos[i])*(media - tiempos[i]) + desviacion;
		}
        desviacion = desviacion/tiempos.length;
        desviacion = (long) Math.sqrt(desviacion.doubleValue());
        System.out.printf("La desviación típica de los tiempos es = %d %n", desviacion);
	}

}
