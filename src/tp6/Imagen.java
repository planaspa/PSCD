/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

/**
 * Obtiene un entero correspondiente a la posicion libre dentro de la ventana,
 * lee la informacion de un Nodo obtiene el tiempo y la imagen, la muestra en
 * la ventana el tiempo obtenido y al finalizar dicho tiempo elimina la imagen
 * y deja el hueco libre en pantalla, informando al monitor. 
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Imagen implements Runnable {

	// Atributos de la clase
	private GestorImagenes gestor;
	private Monitor monitor;
	
	/**
	 * Constructor del objeto Imagen, recibe un monitor para gestionar la informacion
	 * y un objeto GestorImagenes para la insercion y eliminacion de imagenes
	 * @param gestor
	 * @param monitor
	 */
	public Imagen(GestorImagenes gestor, Monitor monitor){
		this.gestor = gestor;
		this.monitor = monitor;
	}
	
	@Override
	public void run() {
		// Obtengo la posicion del hueco libre en la ventana
		int numero = gestor.publicarImagen();
		
		// Obtenemos la informacion de la imagen a insertar
		NodoNegociacion nodo = monitor.siguienteEnCola();
		System.out.println("Inserto imagen para "+ nodo.getTiempo()+ " segundos");
		
		// Inserto la imagen en el panel y espero el tiempo que indica el cliente
		gestor.insertarImagen(nodo.getImagen(),numero);
		try {
			Thread.sleep((int)nodo.getTiempo()*1000);//tiempo en ms
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Finalizado el tiempo de muestra de la imagen informo del hueco libre
		// y borro la imagen. Por ultimo informo al monitor de que tiene un 
		// hueco mas libre en la pantalla
		gestor.dejoHueco(numero);
		gestor.eliminarImagen(numero);
		monitor.dejoHuecoLibreEnPantalla();
		
		
	}

}
