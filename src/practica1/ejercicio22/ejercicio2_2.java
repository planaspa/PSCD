/**
* @author Pablo Lanaspa
* @version
* @date 02/11/2013
*/

package practica1.ejercicio22;

/** 
* Devuelve void. 
* Se crean tres threads del tipo SaludoThread y se lanza su ejecución llamando a sus métodos start()
*/
public class ejercicio2_2 {
	
	public static void main(String args[]){
		
		Soy A, B, C;
		Thread A_t, B_t, C_t;
		
		A = new Soy ("A", 10);
		B = new Soy ("B", 15);
		C = new Soy ("C", 9);
		
		A_t = new Thread (A);
		B_t = new Thread (B);
		C_t = new Thread (C);
		
		A_t.start();
		B_t.start();
		C_t.start();
		
	}

}
