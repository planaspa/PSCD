/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio3;

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
	private BaseDeDatos bd;			//Base de datos correspondiente
	private Semaphore [] mutex_vector;	//Vector de 100 semáforos, uno por cada clave
	private final int ACTUALIZACIONES = 110;	//Numero de actualizaciones que realiza el usuario
	
	
	/**
	 * Constructor
	 */
	public Proceso (BaseDeDatos base, Semaphore [] vector) {
		bd = base;
		mutex_vector = vector;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en actualizar una base de datos
	* según las especificaciones de cada ejercicio de la práctica.	
	*/
	public void run (){
		
		//Generador de números aleatorios
		Random generador = new Random();
		
		/*
		 * Simula un usuario de la base de datos que realiza 110 operaciones de 
		 * actualización de datos
		 */
		for (int i=0;i<ACTUALIZACIONES; i++){
			//Genera la clave aleatoria
			int aleatorio = generador.nextInt(100);
			int clave_aleatoria = aleatorio+1001;
			try {
				//Wait(mutex[número aleatorio])
				mutex_vector[aleatorio].acquire();
				
				//Extraemos el número de modificaciones hasta el momento y lo aumentamos
				String nombre = bd.getNombre(clave_aleatoria);
				String veces_updated = nombre.substring(12);
				int veces = Integer.parseInt(veces_updated);			
				veces++;			
				
				//Actualizamos el resto de registros para dicha clave
				bd.updateNombre(clave_aleatoria, "Nombre_"+clave_aleatoria+"_"+veces);
				bd.updateApellidos(clave_aleatoria, "Apellidos_"+clave_aleatoria+"_"+veces);
				bd.updateDireccion(clave_aleatoria, "C\\ María de Luna "+clave_aleatoria+"_"+veces);
				
				//Signal(mutex[número aleatorio])
				mutex_vector[aleatorio].release();
			} 
			catch (InterruptedException e) {e.printStackTrace();}
			

		}
	}

}
