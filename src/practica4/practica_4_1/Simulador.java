/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_1;

/**
 * 
 * @author a586125
 * La clase Simulador es la aplicación Java que configura y ejecuta el sistema.
 * Contiene el método main() donde son declarados los elementos que constituyen el 
 * sistema: los jugadores, el club y el material disponible inicialmente. Inicialmente,
 * configurado con 14 jugadores (7 con experiencia y 7 novatos) para que repitan su
 * comportamiento 5 veces.
 */
public class Simulador {

	/*
	 * Constantes del ejercicio
	 */
	final static int EXPERIMENTADOS = 7; //Número de jugadores Experimentados
	final static int NOVATOS = 7;		 //Número de jugadores Novatos
	final static int ITERACIONES = 5;	 //Número de iteraciones para la simulación
	final static int PELOTAS = 20;		 //Número de pelotas en el Club
	final static int PALOS = 20;		 //Número de palos en el Club
	
	/**
	 * Método main encargado del lanzamiento de todos los threads y de la simulación
	 */
	public static void main(String [] Args){
		
		//Creamos el club
		Club club = new Club (PELOTAS, PALOS);
		
		//Creamos los threads de los jugadores
		Thread [] experimentados = new Thread [EXPERIMENTADOS];
		Thread [] novatos = new Thread [NOVATOS];
		
		for (int i = 0; i<experimentados.length;i++){ //identificador de cada jugador = i
			experimentados[i]= new Thread (new Jugador (club, i, "E", ITERACIONES));
		}
		for (int i = 0; i<novatos.length;i++){ //identificador de cada jugador = i + EXPERIMENTADOS
			novatos[i]= new Thread (new Jugador (club, i + EXPERIMENTADOS , "N", ITERACIONES));
		}
		
		//Inicializamos los threads
		for (int i = 0; i<experimentados.length;i++)experimentados[i].start();
		for (int i = 0; i<novatos.length;i++) novatos[i].start();
		
	}
}
