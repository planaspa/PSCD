/**
* @author J. Ezpeleta
* @version
* @date 11/08/2011
*/

package practica1.ejemploclase;

class Acabo extends Thread{
	/**
	* El atributo datosComunes almacena la referencia a un objeto de tipo DatosComunes. 
	* 
	*/	
	private DatosComunes datosComunes;

	/** 
	* Define el constructor de objetos Acabo especificándole el valor del atributo datosComunes.
	* 
	* @param DatosComunes d indica el valor que se desea asignar al atributo datosComunes
	* 
	*/

	public Acabo(DatosComunes d){
		this.datosComunes = d;
	}

	/** 
	* Devuelve void. 
	* Realiza una llamada al método setSeguir del atributo datosComunes.  
	* 
	*/

	public void run(){
		datosComunes.setSeguir(false);
	}
}
