/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio1;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author Pablo
 * Proceso que simula a un usuario, el cual realiza <ACUTALIZACIONES> operaciones de 
 * actualización de datos sobre la base de datos indicada en el constructor, de acuerdo
 * a las especificaciones del enunciado.
 */
public class Proceso implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private BaseDeDatos bd;			// Base de datos a la que hace referencia
	private static Semaphore  mutex = new Semaphore (1);	//Semáforo para exclusión mutua
	private final int ACTUALIZACIONES = 110;	//Numero de actualizaciones que realiza el usuario
	
	/**
	 * Constructor
	 */
	public Proceso (BaseDeDatos base) {bd = base;}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en actualizar una base de datos
	* según las especificaciones de cada ejercicio de la práctica.	
	*/
	public void run (){
		//Generador de numeros aleatorios
		Random generador = new Random();
		
		/*
		 * Simula un usuario de la base de datos que realiza 110 operaciones de 
		 * actualización de datos
		 */
		for (int i=0;i<ACTUALIZACIONES; i++){
			//Generamos clave aleatoria
			int clave_aleatoria = generador.nextInt(100)+1001;
			
			try {
				// Wait(mutex)
				mutex.acquire();
				//Obtenemos nombre
				String nombre = bd.getNombre(clave_aleatoria);
				//Aislamos el número de modificaciones y lo aumentamos
				String veces_updated = nombre.substring(12);
				int veces = Integer.parseInt(veces_updated);			
				veces++;			
				//Actualizamos la base de datos con las modificaciones correspondientes
				bd.updateNombre(clave_aleatoria, "Nombre_"+clave_aleatoria+"_"+veces);
				bd.updateApellidos(clave_aleatoria, "Apellidos_"+clave_aleatoria+"_"+veces);
				bd.updateDireccion(clave_aleatoria, "C\\ María de Luna "+clave_aleatoria+"_"+veces);
				
				//Signal(Mutex)
				mutex.release();
			} 
			catch (InterruptedException e) {e.printStackTrace();}			
		}
	}

}
