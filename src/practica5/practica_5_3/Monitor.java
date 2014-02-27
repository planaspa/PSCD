/**
* @author Pablo Lanaspa
* @version
* @date 10/12/2013
*/
package practica5.practica_5_3;

public class Monitor {

	//matriz de dimensi�n 10 x 4 que representa los asientos de un vag�n de tren
	private boolean [][] asientos;
	private int compras;	//N�mero de asientos reservados
	private int filas;		//N�mero de filas de asientos del vag�n
	private int columnas;	//N�mero de columnas de asientos del vag�n
	
	/**
	 * M�todo constructor
	 * @param filas
	 * @param columnas
	 */
	public Monitor(int filas, int columnas){
		compras = 0;
		this.filas = filas;
		this.columnas = columnas;
		
		//Inicializaci�n de la matriz de loas asientos
		asientos = new boolean [filas][columnas];
		for (int i=0; i<filas; i++){
			for (int j=0; j<columnas; j++){
				asientos[i][j]=true;//Ponemos los asientos libres
			}
		}
		
	}
	
	/**
	 * M�todo que permite la reserva o no de los billetes del vag�n. Si se puede reservar
	 * se reserva, si el asiento est� ocupado se devuelve una lista con los asientos 
	 * libres, y si el vag�n est� completo se le informa al cliente.
	 * @param fila
	 * @param columna
	 * @return String con la respuesta a su petici�n
	 */
	public synchronized String reservar (int fila, int columna){
		
		if (compras == filas*columnas) return "VAGON COMPLETO\n";
		if (asientos[fila][columna]){
			//Si est� libre lo ocupamos y devolvemos mensaje de que se ha reservado
			asientos[fila][columna]= false;
			compras++;
			return "RESERVADO\n";
		}
		else {
			//El asiento no est� libre devolvemos la lista con los asientos libres
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
