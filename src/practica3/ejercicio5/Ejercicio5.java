/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio5;

/**
 * 
 * @author Pablo
 * Modificación de la solución al ejercicio 3 de manera que hay 5 procesos
 * escritores realizando operaciones de actualización y 5 procesos accediendo
 * en modo lectura a los datos. Cada proceso lector realiza 110 operaciones de lectura,
 * sobre datos de claves aleatorias. El programa debe permitir varias lecturas 
 * concurrentes.
 */
public class Ejercicio5 {

	/*
	/*
	 * Constantes del ejercicio
	 */
	private final static int REPETICIONES = 20;	//Número de veces que se realiza el ejercicio
	private final static int NUM_ESCRITORES = 5 ;	//Número de escritores que se quieren ejecutar
	private final static int NUM_LECTORES = 5;	//Número de lectores que se quieren ejecutar
	
	/**
	 * Método de main
	 */
	public static void main(String[] args) {

		// Creación de la base de datos y del vector de hilos
		BaseDeDatos bd = new BaseDeDatos();
		Thread [] escritores = new Thread [NUM_ESCRITORES];
		Thread [] lectores = new Thread [NUM_LECTORES];
		
		//Creación de los datos Comunes
		DatosComunes dC = new DatosComunes();
		
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
	        for (int i=0;i<escritores.length;i++) 
	        	escritores [i] = new Thread (new ProcesoEscritor (bd, dC));
	        	
	        for (int i=0;i<lectores.length;i++) 
	        	lectores [i] = new Thread (new ProcesoLector (bd, dC));
	        
	        /*
	         * Inicio de todos los hilos
	         */
	        long tiempoInicio, totalTiempo;
	        tiempoInicio = System.currentTimeMillis();
	        for (int i=0;i<escritores.length;i++) escritores[i].start();
	        for (int i=0;i<lectores.length;i++) lectores[i].start();
	        //Esperamos a que todos hayan terminado
	        for (int i=0;i<escritores.length;i++){
	        	try {escritores[i].join();} 
	        	catch (InterruptedException e) {e.printStackTrace();}
	        }
	        for (int i=0;i<lectores.length;i++){
	        	try {lectores[i].join();} 
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
