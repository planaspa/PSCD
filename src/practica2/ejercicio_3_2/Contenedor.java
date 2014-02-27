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
 *	La clase Contenedor implementa una abstracción del contenedor del problema, que
 *	es objeto del proceso vaciado. Es, por tanto, el recurso compartido por los brazos
 *	robotizados.
 */
public class Contenedor implements Contenedores {

	/*
	 * Atributos de la clase
	 */
	String id;					//Identificador único del contenedor
	int num_piezas_restantes;	//Número de piezas que contiene actualmente el contenedor
	AtomicBoolean ocupado;		//Guarda como current value un booleano que indica si 
								//el contenedor está ocupado o no
	
	/**
	 * El constructor asigna:
	 * @param identificador --> identificador único del contenedor
	 * @param piezas		--> número de piezas que contiene inicialmente;
	 */
	public Contenedor (String identificador, int total_piezas){
		id = identificador;
		num_piezas_restantes = total_piezas;
		ocupado = new AtomicBoolean(); //Por defecto se le asigna false
	}
	
	/**
	 * Este método decrementa en una unidad el número de piezas existentes en el 
	 * contenedor, y representa una abstracción de la operación física por la cual 
	 * un brazo robotizado saca una pieza del contenedor.
	 */
	public void descargarUnaPieza() {num_piezas_restantes --;}
	
	/**
	 * @return Atomicboolean que indica si el contenedor esta ocupado o no
	 */
	public AtomicBoolean getOcupado(){return ocupado;}

}
