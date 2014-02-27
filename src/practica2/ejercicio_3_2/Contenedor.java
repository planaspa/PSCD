/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_2;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * 
 * @author Pablo
 *
 *	La clase Contenedor implementa una abstracci�n del contenedor del problema, que
 *	es objeto del proceso vaciado. Es, por tanto, el recurso compartido por los brazos
 *	robotizados.
 */
public class Contenedor implements Contenedores {

	/*
	 * Atributos de la clase
	 */
	String id;					//Identificador �nico del contenedor
	int num_piezas_restantes;	//N�mero de piezas que contiene actualmente el contenedor
	AtomicBoolean ocupado;		//Guarda como current value un booleano que indica si 
								//el contenedor est� ocupado o no
	
	/**
	 * El constructor asigna:
	 * @param identificador --> identificador �nico del contenedor
	 * @param piezas		--> n�mero de piezas que contiene inicialmente;
	 */
	public Contenedor (String identificador, int total_piezas){
		id = identificador;
		num_piezas_restantes = total_piezas;
		ocupado = new AtomicBoolean(); //Por defecto se le asigna false
	}
	
	/**
	 * Este m�todo decrementa en una unidad el n�mero de piezas existentes en el 
	 * contenedor, y representa una abstracci�n de la operaci�n f�sica por la cual 
	 * un brazo robotizado saca una pieza del contenedor.
	 */
	public void descargarUnaPieza() {num_piezas_restantes --;}
	
	/**
	 * @return Atomicboolean que indica si el contenedor esta ocupado o no
	 */
	public AtomicBoolean getOcupado(){return ocupado;}

}
