/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_1;

/**
 * 
 * @author Pablo
 *	Interfaz de contenedores que obliga a que todo contenedor deba tener un m�todo
 *	para poder descargar una pieza de �l mismo.
 */
public interface Contenedores {

	/**
	 * Este m�todo decrementa en una unidad el n�mero de piezas existentes en el 
	 * contenedor, y representa una abstracci�n de la operaci�n f�sica por la cual 
	 * un brazo robotizado saca una pieza del contenedor.
	 */
	public void descargarUnaPieza();


}
