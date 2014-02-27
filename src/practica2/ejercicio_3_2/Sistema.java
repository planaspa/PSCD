/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_2;

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
		Contenedor contenedor = new Contenedor ("Contenedor", 50);
		
		//Declaramos el vector que contendrá los cinco brazos
		Thread [] vectorBrazos = new Thread [5];
		
		/*
		 * Declaramos los cinco brazos que van a actuar sobre el contenedor
		 * y creamos un hilo diferente para cada brazo
		 */
		for (int i = 0; i<5; i++){
			vectorBrazos[i] = new Thread (new Brazo (i, 10, contenedor));
			//Creamos un hilo diferente para cada brazo
		}
		
		//Ejecutamos todos los hilos
		for (int i = 0; i<5; i++){
			vectorBrazos[i].start();
		}
	}

}
