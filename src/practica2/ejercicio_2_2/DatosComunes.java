/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_2_2;
/**
 * 
 * @author Pablo
 *	Clase de datos comunes que permiten el acceso a varios threads sobre la misma
 *	información.
 */
class DatosComunes{

	private int C []={0,5,8,9,7,3,4,2,1,6};//El vector C guarda los valores desordenados
	private int D [];			// En el vector D se guardaran los valores de C ordenados
	private boolean [] terminado;	//Vector para saber que threads van terminando
	
	/**
	 * Constructor
	 */
	public DatosComunes(){
		D = new int [10];
		terminado = new boolean [10];
	}

	/**
	 * Colocamos en el vector D en valor num en la posición pos
	 * @param pos
	 * @param num
	 */
	public void setValor(int pos, int num){D [pos]=num;}

	/**
	 * 
	 * @return la referencia al vector C
	 */
	public int [] getC () {return C;}
	
	/**
	 * 
	 * @return la referencia al vector D
	 */
	public int [] getD () {return D;}
	
	/**
	 * Indica que la posicion pos ha terminado ya
	 * @param pos
	 */
	public void heTerminado(int pos) {terminado[pos]=true;}
	
	/**
	 * @param pos --> cursor para el vector del elemento que se quiere hallar
	 * @return el número total de threads por orden que han terminado, -1 si no ha
	 * terminado el primero
	 */
	public boolean getTerminados(int pos){return terminado[pos];}
	
}
