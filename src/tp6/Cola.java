/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

/**
 * Gestiona un objeto Cola de maxima dimension [numElementos], con la ayuda de metodos
 * que realizan acciones como la Inserccion de un elemento, la Extraccion de un elemento, 
 * el tamaño de la Cola,comprueban si la Cola esta llena y un nuevo metodo especifico 
 * que calcula el tiempo minimo de la cola de NodosNegociacion.
 * 
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Cola {
	
	private NodoNegociacion [] elementos;	// Negociaciones encoladas
	private int cursor;						// Cursor de apoyo para añadir nuevos elementos
	private int cursorSalida;				// Cursor de apoyo para ir sacando por orden los elementos
	private int elementosEnCola;			// Número de elementos actualmente en la cola
	private int numElementos;				// Tamaño de la Cola
	
	/**
	 * Constructor del objeto Cola, recibe un entero correspondiente
	 * al tamaño de la Cola
	 * @param numElementos
	 */
	public Cola (int numElementos){
		// Inicializacion de atributos
		this.numElementos=numElementos;
		cursor=0;
		cursorSalida = 0;
		elementosEnCola=0;
		elementos = new NodoNegociacion [numElementos];
		for (int i=0; i<elementos.length; i++) elementos[i] = null;
	}

	/**
	 * El metodo recibe un objeto NodoNegociacion. Si la cola no es mayor
	 * de numElementos añade el objeto NodoNegociacion a la cola.
	 * Devuelve un booleano que marcara si se ha añadido correctamente (true)
	 * o si no ha sido posible añadirlo (false)
	 * @param nodo
	 * @return
	 */
	public boolean añadir (NodoNegociacion nodo){
		boolean aniadido = false;
		
		// Compruebo que no se excede de el numero maximo de elementos de la cola
		// y añado
		if (elementosEnCola < numElementos){
			elementos[cursor] = nodo;
			cursor = (cursor + 1) % numElementos;
			elementosEnCola++;
			aniadido = true;
		}	
		return aniadido;
	}
	
	/**
	 * El metodo comprueba que hay elementos en la cola, en caso afirmativo
	 * devuelve el elemento al que le correspondia salir, en caso contrario
	 * devuelve null
	 * @return
	 */
	public NodoNegociacion sacar (){
		
		// Comprobamos que hay elementos en cola y devolvemos el elemento
		// al que le corresponda salir
		if (elementosEnCola > 0){
			NodoNegociacion aux = elementos[cursorSalida];
			cursorSalida = (cursorSalida + 1) % numElementos;
			elementosEnCola --;
			return aux;
		}
		else return null;
	}
	
	/**
	 * El metodo devuelve true si los elementos encolados es menor
	 * que el numero maximo de elementos en Cola, en caso contrario
	 * devuelve false
	 * @return
	 */
	public boolean noLlena (){
		return (elementosEnCola<numElementos);
	}
	
	/**
	 * El metodo devuelve el numero de elementos encolados
	 * @return
	 */
	public int elementosEnCola () {
		return elementosEnCola;
	}
	
	/**
	 * El metodo calcula el el menor tiempo de los elementos mostrados en la 
	 * ventana y de los elementos que se añadiran posteriormente hasta llegar
	 * a la imagen de la cual quiero obtener la hora a la que se mostrara, en
	 * cuyo caso devolvera la hora estimada de publicacion de la imagen.
	 * @return
	 */
	public int tiempoEnSalirUltimoElemento(){
		
		// Inicializamos las distintas variables a usar
		int salida = 0;
		int [] vector = new int [4];
		for (int i= 0; i<vector.length; i++) vector[i] = 0;
		int pos = cursorSalida;
		
		// Mientras no haya llegado a la imagen a mostrar
		// calculo tiempos minimos de las imagenes encoladas
		// y las almaceno en un vector de enteros
		while (pos != cursor){
			for (int i = 0; i<vector.length; i++){
				vector [i] += elementos[pos].getTiempo();
				pos = (pos + 1)%numElementos;
				if (pos == cursor) i = vector.length;
			}
			
			// Ordena el vector para obtener siempre el tiempo minimo
			// en la posicion 0 del vector y almaceno dicho resultado
			// en un entero auxiliar que devolvere al llegar a la imagen
			// a mostrar
			vector = ordenar (vector);
			salida = vector[0];
		}
		
		return salida;
	}
	/**
	 * Ordena de menor a mayor el vector
	 * @param vector
	 * @return
	 */
	private int[] ordenar(int [] vector){
		// Recorremos el vector comparando posiciones y dejando
		// el contenido menor en las componentes mas bajas
		for (int i=0; i<vector.length; i++){
			for (int j=i; j<vector.length;j++){
				if (vector[j]<vector[i]) {
					int aux = vector [i];
					vector [i] = vector[j];
					vector[j] = aux;
				}
			}
		}
		return vector;
	}
}
