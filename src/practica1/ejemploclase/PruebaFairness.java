/**
* @author J. Ezpeleta
* @version
* @date 11/08/2011
*/

package practica1.ejemploclase;

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

		s.start();
		a.start();
	}
}

