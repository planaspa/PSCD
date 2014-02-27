/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio2;

import java.util.Random;
import java.util.concurrent.Semaphore;
/**
 * 
 * @author Pablo
 * Proceso que simula a un usuario, el cual realiza <ACUTALIZACIONES> operaciones de 
 * actualizaci�n de datos sobre la base de datos indicada en el constructor, de acuerdo
 * a las especificaciones del enunciado.
 */
public class Proceso implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private BaseDeDatos bd;			//Base de datos correspondiente
	private Semaphore [] mutex_vector;	//Vector de 3 sem�foros, uno por cada tabla
	private final int ACTUALIZACIONES = 110;	//Numero de actualizaciones que realiza el usuario

	
	
	/**
	 * Constructor
	 */
	public Proceso (BaseDeDatos base, Semaphore [] vector) {
		bd = base;	
		mutex_vector = vector;
	}
	
	/**
	 * M�todo que permite actualizar la tabla de nombres con exclusi�n mutua
	 * @param num --> Clave aleatoria generada
	 */
	private void updateNombre(int num){
		try {
			//Wait(mutex[0])
			mutex_vector [0].acquire();
			
			//Conseguimos el n�mero de modificaciones y lo aumentamos y actualizamos
			String nombre = bd.getNombre(num);
			String veces_updated = nombre.substring(12);
			int veces = Integer.parseInt(veces_updated);			
			veces++;			
			bd.updateNombre(num, "Nombre_"+num+"_"+veces);
			
			//Signal(mutex[0])
			mutex_vector[0].release();
		} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * M�todo que permite actualizar la tabla de apellidos con exclusi�n mutua
	 * @param num --> Clave aleatoria generada
	 */
	private void updateApellido(int num){
		try {
			//Wait(mutex[1])
			mutex_vector [1].acquire();
			
			//Conseguimos el n�mero de modificaciones y lo aumentamos y actualizamos
			String nombre = bd.getApellidos(num);
			String veces_updated = nombre.substring(15);
			int veces = Integer.parseInt(veces_updated);			
			veces++;			
			bd.updateApellidos(num, "Apellidos_"+num+"_"+veces);
			
			//Signal(mutex[1])
			mutex_vector[1].release();
		} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * M�todo que permite actualizar la tabla de direcciones con exclusi�n mutua
	 * @param num --> Clave aleatoria generada
	 */
	private void updateDireccion(int num){
		try {
			//Wait (mutex[2])
			mutex_vector [2].acquire();
			
			//Conseguimos el n�mero de modificaciones y lo aumentamos y actualizamos
			String nombre = bd.getDireccion(num);
			String veces_updated = nombre.substring(22);
			int veces = Integer.parseInt(veces_updated);			
			veces++;			
			bd.updateDireccion(num, "C\\ Mar�a de Luna "+num+"_"+veces);
			
			//Signal (mutex[2])
			mutex_vector[2].release();
		} 
		catch (InterruptedException e) {e.printStackTrace();}

	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en actualizar una base de datos
	* seg�n las especificaciones de cada ejercicio de la pr�ctica.	
	*/
	public void run (){
		//Generador de n�mero aleatorios
		Random generador = new Random();
		
		/*
		 * Simula un usuario de la base de datos que realiza 110 operaciones de 
		 * actualizaci�n de datos
		 */
		for (int i=0;i<ACTUALIZACIONES; i++){
			//Generamos la clave aleatoria
			int clave_aleatoria = generador.nextInt(100)+1001;
			/*
			 * Actualizamos cada una de las tablas
			 */
			updateNombre (clave_aleatoria);
			updateApellido (clave_aleatoria);
			updateDireccion (clave_aleatoria);
		}
	}

}
