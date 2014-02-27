/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio5;

import java.util.concurrent.Semaphore;

/**
 * 
 * @author Pablo
 *	Clase de datos comunes que permiten el acceso a varios threads sobre la misma
 *	información.
 */
public class DatosComunes {

	/*
	 * Atributos de la clase
	 */
	private final int NUM_REGISTROS = 100;		//Numero de registros de todos los vectores
	private int [] numLec;	//Vector que indica el número de lectores que hay leyendo en cada posición
	private int [] numLE;	//Vector que indica el número de lectores que hay esperando para leer en cada posición
	private int [] numEE;	//Vector que indica el número de escritores que hay esperando para escribir en cada posición
	private Semaphore [] mutex;	//Vector que permite exclusión mutua de los protocolos de entrada y salida en cada posición
	private Semaphore [] puedesLeer;	//Vector que avisa de que si se puede leer o no sobre cada posición
	private Semaphore [] puedesEscribir;	//Vector que avisa de que si se puede escribir o no sobre cada posición
	private boolean [] escribiendo;	//Vector que indica si hay proceso escritor escribiendo sobre cada posición
	
	/**
	 * Constructor de la clase
	 */
	public DatosComunes (){
		
		//Creación de todos los vectores
		numLec = new int [NUM_REGISTROS];
		numLE = new int [NUM_REGISTROS];
		numEE = new int [NUM_REGISTROS];
		mutex = new Semaphore [NUM_REGISTROS];
		puedesLeer = new Semaphore [NUM_REGISTROS];
		puedesEscribir = new Semaphore [NUM_REGISTROS];
		escribiendo = new boolean[NUM_REGISTROS];
		
		//Inicialización de cada uno de ellos
		for (int i=0;i<NUM_REGISTROS;i++){
			numLec[i] = 0;
			numLE [i] = 0;
			numEE [i] =0;
			mutex [i] = new Semaphore (1);
			puedesLeer [i] = new Semaphore (0);
			puedesEscribir [i] = new Semaphore (0);
			escribiendo[i] = false;
		}
	}
	
	/**
	 * Aumenta el número de lectores que están leyendo sobre una <pos> en concreto
	 * @param pos
	 */
	public void AumentarNumLec (int pos){numLec[pos]++;}
	
	/**
	 * Disminuye el número de lectores que están leyendo sobre una <pos> en concreto
	 * @param pos
	 */
	public void DisminuirNumLec (int pos) {numLec[pos]--;}
	
	/**
	 * Aumenta el número de lectores que están esperando para leer sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void AumentarNumLE (int pos){numLE[pos]++;}
	
	/**
	 * Disminuye el número de lectores que están esperando para leer sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void DisminuirNumLE (int pos) {numLE[pos]--;}
	
	/**
	 * Aumenta el número de escritores que están esperando para escribir sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void AumentarNumEE(int pos){numEE[pos]++;}
	
	/**
	 * Disminuye el número de escritores que están esperando para escribir sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void DisminuirNumEE (int pos) {numEE[pos]--;}
	
	/**
	 * 
	 * @param pos
	 * @return el número de lectores que están leyendo sobre una <pos> en concreto
	 */
	public int getNumLec (int pos){return numLec[pos];}
	
	/**
	 * 
	 * @param pos
	 * @return el número de escritores que están esperando para escribir sobre una <pos>
	 *  en concreto
	 */
	public int getNumEE (int pos){return numEE[pos];}
	
	/**
	 * 
	 * @param pos
	 * @return el número de lectores que están esperando para leer sobre una <pos>
	 *  en concreto
	 */
	public int getNumLE (int pos){return numLE[pos];}
	
	/**
	 * 
	 * @param pos
	 * @return true si hay alguien escribiendo sobre la <pos> indicada, false en caso
	 * contrario
	 */
	public boolean getEscribiendo (int pos){return escribiendo[pos];}
	
	/**
	 * 
	 * @param pos
	 * @param estado Indica que un proceso escritor está escribiendo (estado = true)
	 * sobre la <pos> indicada o que no lo está haciendo (estado = false)
	 */
	public void setEscribiendo(int pos, boolean estado){escribiendo[pos] = estado;}
	
	/**
	 * Efectúa un wait sobre la variable <pos> del vector de semáforos. 
	 * @param pos
	 */
	public void waitMutex (int pos){
		try {
		mutex[pos].acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * Efectúa un signal sobre la variable <pos> del vector de semáforos
	 * @param pos
	 */
	public void signalMutex(int pos){mutex[pos].release();}
	
	/**
	 * Efectúa un wait sobre la variable <pos> del vector de semáforos
	 * @param pos
	 */
	public void waitPuedesLeer (int pos){
		try {
		puedesLeer[pos].acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * Efectúa un signal sobre la variable <pos> del vector de semáforos
	 * @param pos
	 */
	public void signalPuedesLeer(int pos){puedesLeer[pos].release();}
	
	/**
	 * Efectúa un wait sobre la variable <pos> del vector de semáforos
	 * @param pos
	 */
	public void waitPuedesEscribir (int pos){
		try {
		puedesEscribir[pos].acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * Efectúa un signal sobre la variable <pos> del vector de semáforos
	 * @param pos
	 */
	public void signalPuedesEscribir(int pos){puedesEscribir[pos].release();}
}
