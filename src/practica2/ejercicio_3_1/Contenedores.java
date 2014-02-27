/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_1;

/**
 * 
 * @author Pablo
 *	Interfaz de contenedores que obliga a que todo contenedor deba tener un método
 *	para poder descargar una pieza de él mismo.
 */
public interface Contenedores {

	/**
	 * Este método decrementa en una unidad el número de piezas existentes en el 
	 * contenedor, y representa una abstracción de la operación física por la cual 
	 * un brazo robotizado saca una pieza del contenedor.
	 */
	public void descargarUnaPieza();


}
