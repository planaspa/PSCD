/**
* @author Pablo Lanaspa
* @version
* @date 10/12/2013
*/
package practica5.practica_5_3;

public class Monitor {

	//matriz de dimensión 10 x 4 que representa los asientos de un vagón de tren
	private boolean [][] asientos;
	private int compras;	//Número de asientos reservados
	private int filas;		//Número de filas de asientos del vagón
	private int columnas;	//Número de columnas de asientos del vagón
	
	/**
	 * Método constructor
	 * @param filas
	 * @param columnas
	 */
	public Monitor(int filas, int columnas){
		compras = 0;
		this.filas = filas;
		this.columnas = columnas;
		
		//Inicialización de la matriz de loas asientos
		asientos = new boolean [filas][columnas];
		for (int i=0; i<filas; i++){
			for (int j=0; j<columnas; j++){
				asientos[i][j]=true;//Ponemos los asientos libres
			}
		}
		
	}
	
	/**
	 * Método que permite la reserva o no de los billetes del vagón. Si se puede reservar
	 * se reserva, si el asiento está ocupado se devuelve una lista con los asientos 
	 * libres, y si el vagón está completo se le informa al cliente.
	 * @param fila
	 * @param columna
	 * @return String con la respuesta a su petición
	 */
	public synchronized String reservar (int fila, int columna){
		
		if (compras == filas*columnas) return "VAGON COMPLETO\n";
		if (asientos[fila][columna]){
			//Si está libre lo ocupamos y devolvemos mensaje de que se ha reservado
			asientos[fila][columna]= false;
			compras++;
			return "RESERVADO\n";
		}
		else {
			//El asiento no está libre devolvemos la lista con los asientos libres
			String libres = ((filas*columnas) - compras ) + " asientos libres:\n";
			for (int i=0; i<filas; i++){
				for (int j=0; j<columnas; j++){
					if (asientos[i][j]){
						libres += "Asiento libre, Fila: "+ i +" Columna: "+ j +"\n";
					}
				}
			}
			
			return libres;
		}
	}
}
