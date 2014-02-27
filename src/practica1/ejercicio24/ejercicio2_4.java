/**
* @author Pablo Lanaspa
* @version
* @date 02/11/2013
*/

package practica1.ejercicio24;

/** 
* Devuelve void. 
* Se crean tres threads del tipo Thread y se lanza su ejecución llamando a sus métodos start()
*
*/
public class ejercicio2_4 {
	
	public static void main(String args[]){
		
		Soy A, B, C;
		Thread A_t, B_t, C_t;
		
		A = new Soy ("A", Integer.parseInt(args[0]));
		B = new Soy ("B", Integer.parseInt(args[0]));
		C = new Soy ("C", Integer.parseInt(args[0]));
		
		A_t = new Thread (A);
		B_t = new Thread (B);
		C_t = new Thread (C);
		
		A_t.start();
		B_t.start();
		C_t.start();
		
	}

}
