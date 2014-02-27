/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_2;

/**
 * 
 * @author Pablo
 *
 *	Clase que implementa una abstracción del brazo robotizado del problema. Cada brazo
 *	es un proceso en el sistema concurrente objetivo de este ejercicio, por tanto, esta
 *	clase de proceso implementa la interfaz Runnable
 */
public class Brazo  implements Runnable{

	/*
	 * Atributos de la clase
	 */
	int id;					//identificador único asignado al brazo
	int total_piezas;		//número de piezas que deben ser cogidas por el brazo 
							//durante su actividad
	int cuenta_piezas;		//número de piezas cogidas hasta el momento
	Contenedor cont;		//contenedor sobre el que trabaja
	
	
	/*
	 * Métodos de la clase
	 */
	/**
	 * El constructor tiene tres parámetros:
	 * @param identificador --> identificador único asignado al brazo
	 * @param numero_piezas --> número de piezas que deben ser cogidas por el brazo 
	 * 							durante su actividad
	 * @param contenedor	--> contenedor sobre el que debe de trabajar
	 */
	public Brazo (int identificador, int numero_piezas, Contenedor contenedor){
		id = identificador;
		total_piezas = numero_piezas;
		cuenta_piezas = 0;
		cont = contenedor;
	}
	
	/**
	 * Es el responsable de poner en marcha el brazo para que coja del contenedor
	 * compartido el número de piezas indicado
	 */
	public void run () {
		
		//Variables booleanas que sirven para facilitar la lectura del código
		boolean entrar = false;
		boolean ocupado = false;
		
		while (cuenta_piezas != total_piezas){
			//Espera activa, si está ocupado no entrar, devuelve true si queda libre
			while (!cont.getOcupado().compareAndSet(ocupado, !entrar)) {;}
			
			//Sección crítica
			//Descarga una pieza del contenedor
			cont.descargarUnaPieza();

			//Protocolo de salida, indica que el contenedor ya no está ocupado y que se 
			//puede entrar a el
			cont.getOcupado().compareAndSet(!ocupado, entrar);

			//Sección no crítica
			//Añade una pieza a la cuenta de piezas descargadas
			cuenta_piezas++;
			
			/*
			 * Muestra por pantalla su identificador único y número de piezas cogidas
			 * hasta el momento
			 */
			System.out.printf("Brazo %d descarga una pieza %d de %d del contenedor...%n",
					id, cuenta_piezas, total_piezas);
		}
	}

}
