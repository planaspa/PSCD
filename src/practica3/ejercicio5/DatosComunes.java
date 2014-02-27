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
 *	informaci�n.
 */
public class DatosComunes {

	/*
	 * Atributos de la clase
	 */
	private final int NUM_REGISTROS = 100;		//Numero de registros de todos los vectores
	private int [] numLec;	//Vector que indica el n�mero de lectores que hay leyendo en cada posici�n
	private int [] numLE;	//Vector que indica el n�mero de lectores que hay esperando para leer en cada posici�n
	private int [] numEE;	//Vector que indica el n�mero de escritores que hay esperando para escribir en cada posici�n
	private Semaphore [] mutex;	//Vector que permite exclusi�n mutua de los protocolos de entrada y salida en cada posici�n
	private Semaphore [] puedesLeer;	//Vector que avisa de que si se puede leer o no sobre cada posici�n
	private Semaphore [] puedesEscribir;	//Vector que avisa de que si se puede escribir o no sobre cada posici�n
	private boolean [] escribiendo;	//Vector que indica si hay proceso escritor escribiendo sobre cada posici�n
	
	/**
	 * Constructor de la clase
	 */
	public DatosComunes (){
		
		//Creaci�n de todos los vectores
		numLec = new int [NUM_REGISTROS];
		numLE = new int [NUM_REGISTROS];
		numEE = new int [NUM_REGISTROS];
		mutex = new Semaphore [NUM_REGISTROS];
		puedesLeer = new Semaphore [NUM_REGISTROS];
		puedesEscribir = new Semaphore [NUM_REGISTROS];
		escribiendo = new boolean[NUM_REGISTROS];
		
		//Inicializaci�n de cada uno de ellos
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
	 * Aumenta el n�mero de lectores que est�n leyendo sobre una <pos> en concreto
	 * @param pos
	 */
	public void AumentarNumLec (int pos){numLec[pos]++;}
	
	/**
	 * Disminuye el n�mero de lectores que est�n leyendo sobre una <pos> en concreto
	 * @param pos
	 */
	public void DisminuirNumLec (int pos) {numLec[pos]--;}
	
	/**
	 * Aumenta el n�mero de lectores que est�n esperando para leer sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void AumentarNumLE (int pos){numLE[pos]++;}
	
	/**
	 * Disminuye el n�mero de lectores que est�n esperando para leer sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void DisminuirNumLE (int pos) {numLE[pos]--;}
	
	/**
	 * Aumenta el n�mero de escritores que est�n esperando para escribir sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void AumentarNumEE(int pos){numEE[pos]++;}
	
	/**
	 * Disminuye el n�mero de escritores que est�n esperando para escribir sobre una <pos> 
	 * en concreto
	 * @param pos
	 */
	public void DisminuirNumEE (int pos) {numEE[pos]--;}
	
	/**
	 * 
	 * @param pos
	 * @return el n�mero de lectores que est�n leyendo sobre una <pos> en concreto
	 */
	public int getNumLec (int pos){return numLec[pos];}
	
	/**
	 * 
	 * @param pos
	 * @return el n�mero de escritores que est�n esperando para escribir sobre una <pos>
	 *  en concreto
	 */
	public int getNumEE (int pos){return numEE[pos];}
	
	/**
	 * 
	 * @param pos
	 * @return el n�mero de lectores que est�n esperando para leer sobre una <pos>
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
	 * @param estado Indica que un proceso escritor est� escribiendo (estado = true)
	 * sobre la <pos> indicada o que no lo est� haciendo (estado = false)
	 */
	public void setEscribiendo(int pos, boolean estado){escribiendo[pos] = estado;}
	
	/**
	 * Efect�a un wait sobre la variable <pos> del vector de sem�foros. 
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
	 * Efect�a un signal sobre la variable <pos> del vector de sem�foros
	 * @param pos
	 */
	public void signalMutex(int pos){mutex[pos].release();}
	
	/**
	 * Efect�a un wait sobre la variable <pos> del vector de sem�foros
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
	 * Efect�a un signal sobre la variable <pos> del vector de sem�foros
	 * @param pos
	 */
	public void signalPuedesLeer(int pos){puedesLeer[pos].release();}
	
	/**
	 * Efect�a un wait sobre la variable <pos> del vector de sem�foros
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
	 * Efect�a un signal sobre la variable <pos> del vector de sem�foros
	 * @param pos
	 */
	public void signalPuedesEscribir(int pos){puedesEscribir[pos].release();}
}
