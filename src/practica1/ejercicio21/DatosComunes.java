/**
* @author J. Ezpeleta
* @version
* @date 11/08/2011
*/

package practica1.ejercicio21;

class DatosComunes{

	/**
	* La variable seguir almacena un valor booleano (true o false). 
	* Se inicializa a true cuando se crea un objeto de esta clase. 
	*/	
	private boolean seguir = true;

	/** 
	* Devuelve void  
	*  
	* @param boolean valor indica el valor que se desea asignar al dato en com�n
	*
	*/

	public void setSeguir(boolean valor){
		this.seguir = valor;
	}

	/** 
	* Devuelve el valor del dato en com�n (true o false). 
	* El valor por defecto es true pero alg�n proceso ha podido cambiarlo.  
	* 
	* @return boolean indica el valor del dato com�n. 
	*/

	public boolean getSeguir(){
		return seguir;
	}
}
