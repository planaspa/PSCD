/**
* @author Pablo Lanaspa
* @version
* @date 02/11/2013
*/

package practica1.ejercicio21;

class PruebaFairness {

	/** 
	* Crea un objeto DatosComunes que lo asigna a la variable dC y dos procesos de tipo Sigo y Acabo.
	* Lanza los dos procesos realizando una llamada al método start() de cada uno de ellos.
	* 
	*/

	public static void main(String args[]){
		DatosComunes dC;
		Sigo s;
		Acabo a;

		dC = new DatosComunes();
		s = new Sigo(dC);
		a = new Acabo(dC);
		
        //Creamos los threads para que se ejecuten los objetos Runnable
		Thread s_t = new Thread(s);
		Thread a_t = new Thread(a);
		
		s_t.start();
		a_t.start();
		
	}
}

