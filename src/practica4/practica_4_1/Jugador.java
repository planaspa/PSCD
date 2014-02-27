/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_1;

import java.util.Random;

/**
 * 
 * @author a586125
 * La clase Jugador implementa los procesos del sistema. El constructor de la clase está
 * programado para asignar un identificador único y el tipo de jugador al que pertenece
 * cada jugador en concreto. Internamente, el comportamiento que exhibe un jugador 
 * (reservar material, jugar, devolver material y descansar) es repetido un número de 
 * veces concretas que también es configurable a través del constructor. El tiempo que
 * dedica a jugar y descansar es aleatorio (no superior a un segundo para cada actividad)
 * y puede ser diferente en cada ocasión.
 */
public class Jugador implements Runnable {
	
	private int id;		//Identificador único del jugador
	private String tipo;	//Tipo de jugador --> "N" para novato y "E" para experimentado
	private int iteraciones; 	//Número de veces que cada jugador realiza el comportamiento definido
	private Club club;	//Club al que pertenece el jugador

	/**
	 * Constructor
	 * @param identificador
	 * @param tipo_jugador
	 * @param veces
	 */
	public Jugador (Club sitio, int identificador, String tipo_jugador, int veces){
		id = identificador;
		tipo = tipo_jugador;
		iteraciones = veces;
		club = sitio;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en reservar material, jugar, 
	* devolver material y descansar un número <iteraciones> de veces.
	*/
	public void run (){
		
		//Generador de números aleatorios
		Random generador = new Random();
			
		for (int i=0; i<iteraciones; i++){
			
			if (tipo.toUpperCase().equals("N")){
				//Ejecución para jugador novato
				
				/*
				 * Reserva el material:
				 * Un jugador novato suele alquilar un número random de pelotas (nunca más de 5 pelotas) y dos únicos palos
				 */
				int pelotas = generador.nextInt(4)+1;//Mínimo cogerá siempre un palo para poder jugar
				System.out.printf("%d - reserva [%d,%d]%n", id, pelotas,2);
				club.reservar(pelotas, 2);
				System.out.printf("%d - usa [%d,%d]%n", id, pelotas,2);
				
				//Juega un tiempo aleatorio no superior a un segundo
				try {
					Thread.currentThread().sleep(generador.nextInt(1000));//Como mucho 1000 ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.printf("%d - devuelve [%d,%d]%n", id, pelotas,2);
				//Devuelve el material
				club.devolver(pelotas,2);
				System.out.printf("%d - devueltos y a descansar [%d,%d]%n", id, pelotas,2);
				
				//Descansa un tiempo aleatorio no superior a un segundo
				try {
					Thread.currentThread().sleep(generador.nextInt(1000));//Como mucho 1000 ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (tipo.toUpperCase().equals("E")){
				//Ejecución para jugador experimentado
				
				/*
				 * Reserva el material:
				 * Los jugadores con experiencia siempre alquilan una única pelota y entre 2 y 5 palos para jugar
				 */
				int palos = generador.nextInt(3)+2;
				System.out.printf("%d - reserva [%d,%d]%n", id, 1,palos);
				club.reservar(1,palos);
				System.out.printf("%d - usa [%d,%d]%n", id, 1,palos);
				//Juega un tiempo aleatorio no superior a un segundo
				try {
					Thread.currentThread().sleep(generador.nextInt(1000)); //Como mucho 1000 ms
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Devuelve el material
				System.out.printf("%d - devuelve [%d,%d]%n", id, 1,palos);
				club.devolver(1,palos);
				System.out.printf("%d - ha devuelto y a descansar [%d,%d]%n", id, 1,palos);
				//Descansa un tiempo aleatorio no superior a un segundo
				try {
					Thread.currentThread().sleep(generador.nextInt(1000)); //Como mucho 1000 ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else System.out.println("Tipo de jugador erróneo");
			
		}
	}
}
