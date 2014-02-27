/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_1;

/**
 * 
 * @author a586125
 * La clase Club implementa el monitor que gestiona la concurrencia en el uso del material
 * disponible en el club. Encapsula dos atributos que representen el número de pelotas y
 * palos de golf disponibles y dos métodos para su reserva y devolución. Cada objeto de
 * la clase es fácilmente configurable desde el punto de vista de la cantidad de recursos
 * inicialmente disponibles utilizando las constantes correspondientes en la clase
 * Simulador.
 */
public class Club {
	
	/*
	 * Atributos de la clase
	 */
	private int pelotas_disponibles;	//Número de pelotas disponibles en el Club
	private int palos_disponibles;		//Número de palos disponibles en el Club
	
	
	/**
	 * Constructor
	 */
	public Club (int pelotas, int palos){
		pelotas_disponibles = pelotas;
		palos_disponibles = palos;
	}
	
	
	/**
	 * Método que permite reservar las pelotas y palos determinados como parámetros.
	 * Si no hay suficientes palos y pelotas hay que esperar a que alguien devuelva los 
	 * suyos.
	 * @param pelotas
	 * @param palos
	 */
	public synchronized void reservar (int pelotas, int palos){
		while (pelotas_disponibles < pelotas && palos_disponibles < palos){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pelotas_disponibles = pelotas_disponibles - pelotas;
		palos_disponibles = palos_disponibles - palos;	
	}
	
	/**
	 * Método que permite devolver las pelotas y palos determinados por los parámetros
	 * para que otros jugadores puedan hacer uso de ellos. Avisa a los jugadores que
	 * estaban esperando de que han llegado palos y pelotas.
	 * @param pelotas
	 * @param palos
	 */
	public synchronized void devolver (int pelotas, int palos){	
		pelotas_disponibles = pelotas_disponibles + pelotas;
		palos_disponibles = palos_disponibles + palos;
		notifyAll();
	}
	

}
