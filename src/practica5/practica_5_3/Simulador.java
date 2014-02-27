/**
* @author Pablo Lanaspa
* @version
* @date 10/12/2013
*/
package practica5.practica_5_3;


public class Simulador {
	
	final static int FILAS = 10;	  //Filas del vagón
	final static int COLUMNAS = 4;	  //Columnas del vagón
	final static int NUM_THREADS = 9; //Numero de clientes para realizar compras
	final static int ITERACIONES = 5; //Número de compras que realizará cada cliente
	
	/**
	 * Método main del ejercicio que permite la simulación a través de los parámetros
	 * declarados como constantes del mismo.
	 * @param args
	 */
	public static void main(String[] args){
		
		//Creamos el servidor y lo lanzamos
		System.out.print("Creando servidor... ");
		Servidor servidor =  new Servidor(FILAS, COLUMNAS);
		Thread server = new Thread(servidor);
		server.start();
		
		System.out.println("Servidor creado correctamente.");
		System.out.print("Creando clientes... ");
		
		//Creamos los clientes...
		Thread [] clientes = new Thread [NUM_THREADS];
		
		for (int i=0; i<clientes.length; i++) 
			clientes [i] = new Thread (new Cliente (i, FILAS, COLUMNAS, ITERACIONES));
		
		System.out.println("Clientes creados correctamente.");
		System.out.println("Lanzamos los clientes para las compras.");
		
		//... Y los lanzamos
		for (int i=0; i<clientes.length; i++) clientes[i].start();
		
		//Cuando todos han terminado informamos de ello
		for (int i=0; i<clientes.length; i++){
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		System.out.println("Todos los clientes han finalizado sus compras, solo queda vivo el servidor.");
		System.out.println("--- FIN DE LA SIMULACIÓN ---");
	}
}
