/**
* @author Pablo Lanaspa
* @version
* @date 02/11/2013
*/

package practica1.ejercicio21;

class Sigo implements Runnable{

	/**
	* El atributo datosComunes almacena la referencia a un objeto de tipo DatosComunes. 
	* 
	*/	

	private DatosComunes datosComunes;

	/** 
	* Define el constructor de objetos Sigo especific�ndole el valor del atributo datosComunes.
	* 
	* @param DatosComunes d indica el valor que se desea asignar al atributo datosComunes
	* 
	*/

	public Sigo(DatosComunes d){
		this.datosComunes = d;
	}

	/** 
	* Devuelve void. 
	* Realiza iterativamente una llamada al m�todo getSeguir del atributo datosComunes.
	* Si el valor devuelto por dicho m�todo es true entonces imprime un mensaje por pantalla
	* indicanto cuantas veces ha realizado esta acci�n y vuelve a llamar al m�todo getSeguir.   
	* Si el valor devuelto por dicho m�todo es false entonces imprime un mensaje por pantalla 
	* indicando cuantas llamadas ha realizado al m�todo getSeguir y finaliza la ejecuci�n.
	* 
	*/

	public void run(){
		int i = 0;
		while(datosComunes.getSeguir()){
			i = i + 1;
			System.out.println("Sigo: "+i);
		}	
		System.out.println("Sigo: "+i);
	}
}

