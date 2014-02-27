/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_3;

import java.util.Random;

/**
 * 
 * @author a586125
 * La clase Jugador implementa los procesos del sistema. El constructor de la clase est�
 * programado para asignar un identificador �nico. Internamente, el comportamiento que
 * exhibe un jugador (reservar material, jugar, devolver material y descansar) es repetido
 * un n�mero de veces concretas que tambi�n configurable a trav�s del constructor.
 * El tiempo que dedica a jugar y descansar es aleatorio (no superior a un segundo para 
 * cada actividad) y puede ser diferente en cada ocasi�n. Adem�s en esta modificaci�n del
 * ejercicio este proceso se debe realizar por parejas como se especifica en la clase
 * Simulador.
 */
public class Jugador implements Runnable {
	
	private int id;				//Identificador �nico del jugador
	private int iteraciones; 	//N�mero de veces que cada jugador realiza el comportamiento definido
	private Club club;			//Club al que pertenece el jugador

	/**
	 * Constructor
	 * @param identificador
	 * @param veces
	 */
	public Jugador (Club sitio, int identificador,  int veces){
		id = identificador;
		iteraciones = veces;
		club = sitio;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en reservar material, jugar, 
	* devolver material y descansar un n�mero <iteraciones> de veces por parejas con 
	* otro jugador.
	 */
	public void run (){
		
		//Generador de n�meros aleatorios
		Random generador = new Random();
		
		for (int i=0; i<iteraciones; i++){
			
			/*
			 * Inicializaci�n de las variables para cada iteraci�n
			 */
			int pelotas = 0;
			int palos = 2;
			
			System.out.printf("%d entra al club.%n",id);
			if (club.asignarPareja(id)){
				//A este jugador le ha tocado pedir las pelotas
				palos = 0;
				pelotas = generador.nextInt(4)+1;//M�nimo coger� siempre un palo para poder jugar
				System.out.printf("%d - reserva [%d,%d]%n", id, pelotas,palos);
				club.reservarPelotas(id, pelotas);
				//Esperamos a que nuestro compa�ero haya cogido los palos
			}
			else{
				//A este jugador le ha tocado pedir los palos
				System.out.printf("%d - reserva [%d,%d].%n", id, pelotas,palos);
				club.reservarPalos(id, palos);
				System.out.printf("%d y %d se van al campo a jugar.%n", id, club.miPareja(id));
				//Esperamos a que nuestro compa�ero haya cogido las pelotas
			}
						
			//Juega un tiempo aleatorio no superior a un segundo
			try {
				Thread.currentThread().sleep(generador.nextInt(1000));//Como mucho 1000 ms
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.printf("%d - devuelve [%d,%d]. %n", id, pelotas,palos);
			//Devuelve el material
			if (pelotas == 0) club.devolverPalos(id, palos);
			else club.devolverPelotas(id, pelotas);
			System.out.printf("%d - devueltos y a descansar [%d,%d].%n", id, pelotas,palos);
			//No se va a descansar hasta que su pareja haya devuelto el material
			//Descansa un tiempo aleatorio no superior a un segundo
			try {
				Thread.currentThread().sleep(generador.nextInt(1000));//Como mucho 1000 ms
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
