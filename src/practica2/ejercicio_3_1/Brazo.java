/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_1;

/**
 * 
 * @author Pablo
 *
 *	Clase que implementa una abstracci�n del brazo robotizado del problema. Cada brazo
 *	es un proceso en el sistema concurrente objetivo de este ejercicio, por tanto, esta
 *	clase de proceso implementa la interfaz Runnable
 */
public class Brazo  implements Runnable{

	/*
	 * Atributos de la clase
	 */
	int id;					//identificador �nico asignado al brazo
	int total_piezas;		//n�mero de piezas que deben ser cogidas por el brazo 
							//durante su actividad
	int cuenta_piezas;		//n�mero de piezas cogidas hasta el momento
	Contenedor cont;		//contenedor sobre el que trabaja
	
	
	/*
	 * M�todos de la clase
	 */
	/**
	 * El constructor tiene tres par�metros:
	 * @param identificador --> identificador �nico asignado al brazo
	 * @param numero_piezas --> n�mero de piezas que deben ser cogidas por el brazo 
	 * 							durante su actividad
	 * @param contenedor	--> contenedor sobre el que debe de trabajar
	 */
	public Brazo (int identificador, int numero_piezas, Contenedor contenedor){
		id = identificador;
		total_piezas = numero_piezas;
		cuenta_piezas = 0;
		cont = contenedor;
	}
	
	/**
	 * Es el responsable de poner en marcha el brazo para que coja del contenedor
	 * compartido el n�mero de piezas indicado. Aplicamos el algoritmo de Peterson.
	 */
	public void run () {
		
		//Obteniendo la id del otro brazo
		int otro = 0;
		if (id == 0) otro = 1;
		
		cont.quieroEntrar(id, false);
		while (cuenta_piezas != total_piezas){
			
			//Iniciamos protocolo de entrada
			cont.quieroEntrar(id, true);
			cont.soyElUltimo(id);
			
			//Espera activa
			while (cont.haSolicitadoEntrar(otro) && cont.quienEsElUltimo() == id) {;}

			//Secci�n cr�tica
			//Descarga una pieza del contenedor
			cont.descargarUnaPieza();

			//Protocolo de salida
			cont.quieroEntrar(id, false);

			//Secci�n no cr�tica
			//A�ade una pieza a la cuenta de piezas descargadas
			cuenta_piezas++;
			
			/*
			 * Muestra por pantalla su identificador �nico y n�mero de piezas cogidas
			 * hasta el momento
			 */
			System.out.printf("Brazo %d descarga una pieza %d de %d del contenedor...%n",
					id, cuenta_piezas, total_piezas);
		}
	}

}
