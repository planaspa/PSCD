/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_2_1;
public class ejercicio2_1 {
	
	/**
	 * Método main del ejercicio
	 */
	public static void main(String[] args) {

		//Creamos los datos comunes para el ejercicio
		DatosComunes dC = new DatosComunes();
		
		//Creamos los vectores de procesos e hilos
		Proceso vector [] = new Proceso [10];
		Thread vectorThread [] = new Thread [10];
		
		//Creamos cada proceso del vector uno a uno
		for (int i=0;i<10;i++){vector[i] = new Proceso(i,dC);}		
		
		//Creamos cada hilo del vector uno a uno
		for (int i=0;i<10;i++){vectorThread[i] = new Thread (vector[i]);}
		
		//Iniciamos los threads
		for (int i=0; i<10; i++){vectorThread[i].start();}
		
		//Mostramos por pantalla conforme vayan terminando
		for (int i=0;i<10;i++){
			try { //Implemento espera no activa
				vectorThread[i].join();
				System.out.printf("%d%n", dC.getD()[i]);
			} catch (InterruptedException e) {e.printStackTrace();}
		}

	}

}
