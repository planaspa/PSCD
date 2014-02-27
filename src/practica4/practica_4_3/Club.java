/**
* @author Pablo Lanaspa
* @version
* @date 25/11/2013
*/
package practica4.practica_4_3;


/**
 * 
 * @author a586125
 * La clase Club implementa el monitor que gestiona la concurrencia en el uso del material
 * disponible en el club. Cada objeto de la clase es f�cilmente configurable desde el 
 * punto de vista de la cantidad de recursos inicialmente disponibles utilizando las 
 * constantes correspondientes en la clase Simulador. Adem�s en esta segunda versi�n de
 * la tarea se han a�adido m�s m�todos que nos permiten la sincronizaci�n entre dos
 * jugadores para que se repartan las tareas de reservado y devoluci�n del material.
 */
public class Club {
	
	/*
	 * Atributos de la clase
	 */
	private int pelotas_disponibles;	//N�mero de pelotas disponibles en el Club
	private int palos_disponibles;		//N�mero de palos disponibles en el Club
	private int esperando;				//FLAG indicativo de si hay alguien esperando a una pareja
	private int id_esperando;			//ID del jugador que est� esperando una nueva pareja
	/*
	 * Array de IDs. En la posici�n <ID> correspondiente a cada jugador, 
	 * se encuentra la Id de su pareja. -1 si no tiene pareja.
	 */
	private int [] parejas;	
	private boolean [] material;		//Array de booleanos que indica si el ID correspondiente tiene el material o no
	
	/**
	 * Constructor: Asignaci�n de las variables e inicializaci�n de los vectores
	 */
	public Club (int pelotas, int palos, int jugadores){
		pelotas_disponibles = pelotas;
		palos_disponibles = palos;
		esperando = 0;
		parejas = new int [jugadores];
		material = new boolean [jugadores];
		for (int i=0; i<parejas.length;i++) {	
			parejas [i]=-1;
			material [i] = false;
		}
	}
	
	/**
	 * M�todo que realiza la asignaci�n de parejas entre jugadores. Si no hay pareja
	 * para emparejar mantiene a la espera a dicho jugador hasta que aparezca alguien
	 * con qui�n jugar. El jugador que ha estado esperando se encargar� de coger los palos,
	 * el otro se encargar� de coger las pelotas.
	 * @param id
	 * @return true si se encarga de coger las pelotas, false si se encarga de coger los palos
	 */
	public synchronized boolean asignarPareja (int id){
		if (esperando == 0){ //Si no hay nadie esperando pareja
			esperando++;
			id_esperando = id; //ID del jugador que va a esperar pareja
			while (parejas[id] == -1){ //Mientras no se le asigne pareja espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int aux = parejas [id];
			parejas[aux] = id; //Apunta a su pareja su ID
			notifyAll();	//Avisa de que ya est� preparado para reservar el material
			return false; //Se encarga de no coger las pelotas (coger los palos)
		}
		else {		
			parejas[id_esperando]= id; //Apunta al jugador que estaba esperando (su pareja) su ID
			esperando--;
			notifyAll();
			while (parejas[id]== -1){//Mientras no se le asigne pareja espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true; //Se encarga de coger las pelotas
		}
		
	}
	/**
	 * @param id
	 * @return el ID de la pareja de <id>
	 */
	public synchronized int miPareja (int id){
		return parejas[id];
	}
	
	/**
	 * Si hay palos disponibles los reserva, si no espera. Cuando los ha reservado espera
	 * a que su pareja haya cogido su material correspondiente tambi�n.
	 * @param id
	 * @param palos
	 */
	public synchronized void reservarPalos (int id, int palos){
		while (palos_disponibles < palos){ //Si no hay palos suficientes espera
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Notifica de que los ha cogido y actualiza variables
		palos_disponibles = palos_disponibles - palos;
		material[id] = true;
		notifyAll(); 
		if (!material[parejas[id]]){
			System.out.printf("%d espera a %d para ir a jugar.%n", id, parejas[id]);
			while (!material[parejas[id]]){ //Mientras que su pareja no tenga el material espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Si hay pelotas disponibles los reserva, si no espera. Cuando los ha reservado
	 * espera a que su pareja haya cogido su material correspondiente tambi�n.
	 * @param id
	 * @param pelotas
	 */
	public synchronized void reservarPelotas (int id, int pelotas){
		while (pelotas_disponibles < pelotas){ //Si no hay pelotas suficientes espera
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Notifica de que las ha cogido y actualiza variables
		pelotas_disponibles = pelotas_disponibles - pelotas;
		material[id] = true;
		notifyAll();
		if (!material[parejas[id]]){
		System.out.printf("%d espera a %d para ir a jugar.%n", id, parejas[id]);
			while (!material[parejas[id]]){//Mientras que su pareja no tenga el material espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * M�todo que permite devolver los palos usados al Club y que hace esperar al jugador
	 * hasta que la pareja no ha devuelto su material.
	 * @param id
	 * @param palos
	 */
	public synchronized void devolverPalos (int id, int palos){	
		//Actualiza variables notificando que ha devuelto el material
		palos_disponibles = palos_disponibles + palos;
		material[id] = false;
		int elOtro = parejas[id];
		parejas [id] = -1;
		notifyAll();
		if (parejas[elOtro]!= -1){
			System.out.printf("%d espera a %d para ir a descansar.%n", id, elOtro);
			while (material[elOtro]){ //Mientras que la pareja no haya devuelto el material espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * M�todo que permite devolver las pelotas usados al Club y que hace esperar al 
	 * jugador hasta que la pareja no ha devuelto su material.
	 * @param id
	 * @param pelotas
	 */
	public synchronized void devolverPelotas (int id, int pelotas){	
		//Actualiza variables notificando que ha devuelto el material
		pelotas_disponibles = pelotas_disponibles + pelotas;
		material[id] = false;
		int elOtro = parejas[id];
		parejas [id] = -1;
		notifyAll();
		if (parejas[elOtro]!= -1){
			System.out.printf("%d espera a %d para ir a descansar.%n", id, elOtro);
			while (material[elOtro]){ //Mientras que la pareja no haya devuelto el material espera
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

}
