/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_1;

/**
 * 
 * @author Pablo
 *	Aplicación que configura y ejecuta el sistema. Contiene un método main() donde son
 *	declarados los elementos que constituyen el sistema: un contenedor de 50 piezas y 
 *	dos brazo robotizados que tendrán como objetivo el vaciado del mismo.
 */
public class Sistema {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Declaramos el contenedor sobre el que vamos a trabajar
		Contenedor contenedor = new Contenedor ("Contenedor", 10);
		
		//Declaramos los dos brazos que van a actuar sobre el contenedor
		Brazo brazo_A = new Brazo (0, 5, contenedor);
		Brazo brazo_B = new Brazo (1, 5, contenedor);
		
		//Creamos un hilo diferente para cada brazo
		Thread brazo_A_thread = new Thread (brazo_A);
		Thread brazo_B_thread = new Thread (brazo_B);
		
		//Ejecutamos ambos hilos
		brazo_A_thread.start();
		brazo_B_thread.start();
	}

}
