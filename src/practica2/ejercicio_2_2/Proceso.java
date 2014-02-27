/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_2_2;

public class Proceso implements Runnable{

	private int posicion;		//Índice que ocupa en el vector C
	private DatosComunes dC;	//Referencia a los datos comunes del ejercicio
	
	/**
	 * Metodo constructor
	 * @param pos --> posicion
	 * @param datos --> dC
	 */
	public Proceso (int pos, DatosComunes datos){
		posicion = pos;
		dC = datos;
	}
	
	/** 
	    * Devuelve void. 
	    * Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	    * el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	    * Cuando finaliza la ejecución del método run() el thread muere.	
	    * En este caso la ejecución del método run() consiste en ordenar un vector en otro vector.	
	    */
		public void run(){
			
			/*
			 * Algoritmo libro de M.Ben-Ari
			 */
			
	    	int myNumber = dC.getC()[posicion];
	    	int count=0;
	    	
	    	for (int i= 0; i<dC.getC().length;i++){
	    		if (dC.getC()[i]<myNumber) count++;
	    	}
	    	
	    	dC.setValor(count, myNumber);
	    	dC.heTerminado(myNumber); //Anoto que ya he terminado
	    	
	    }
	
}
