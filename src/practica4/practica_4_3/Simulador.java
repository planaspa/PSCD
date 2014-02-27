/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_3;

/**
 * 
 * @author a586125
 * La clase Simulador es la aplicaci�n Java que configura y ejecuta el sistema.
 * Contiene el m�todo main() donde son declarados los elementos que constituyen el 
 * sistema: los jugadores, el club y el material disponible inicialmente. 
 * En esta segunda versi�n del programa Java los jugadores se organizan de dos en dos 
 * (asumiremos que todos son novatos) para compartir tanto las pelotas como los palos de
 * golf. El funcionamiento debe ser como sigue:
 * 	Cuando un jugador llega al club, espera a que haya otro; acuerdan entonces cu�ntas 
 * 	pelotas van a pedir y, mientras uno de ellos las pide, el otro pide los dos palos 
 * 	necesarios. Cuando ambos han obtenido lo que esperaban, van juntos al terreno de golf
 * 	(es decir,el primero que acaba debe esperar al otro).
 */
public class Simulador {

	/*
	 * Constantes del ejercicio
	 */
	final static int NOVATOS = 12;		 //N�mero de jugadores
	final static int ITERACIONES = 2;	 //N�mero de iteraciones para la simulaci�n
	final static int PELOTAS = 20;		 //N�mero de pelotas en el Club
	final static int PALOS = 20;		 //N�mero de palos en el Club
	
	/**
	 * 
	 */
	public static void main(String [] Args){
		
		//Creamos el club
		Club club = new Club (PELOTAS, PALOS, NOVATOS);
		
		//Creamos los threads de los jugadores
		Thread [] novatos = new Thread [NOVATOS];
		
		for (int i = 0; i<novatos.length;i++){ //identificador de cada jugador = i + EXPERIMENTADOS
			novatos[i]= new Thread (new Jugador (club, i , ITERACIONES));
		}
		
		//Inicializamos los threads
		for (int i = 0; i<novatos.length;i++) novatos[i].start();
		
	}
}
