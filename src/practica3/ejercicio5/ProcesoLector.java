/**
* @author Pablo Lanaspa
* @version
* @date 06/11/2013
*/
package practica3.ejercicio5;

import java.util.Random;

/**
 * 
 * @author Pablo
 * Proceso que simula a un usuario, el cual realiza <LECTURAS> operaciones de 
 * lecturas de datos sobre la base de datos indicada en el constructor, de acuerdo
 * a las especificaciones del enunciado.
 */
public class ProcesoLector implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private BaseDeDatos bd;				//Base de datos correspondiente
	private DatosComunes dC;			//Datos Comunes necesarios para la correcta implementación
	private final int LECTURAS = 110;	//Numero de lecturas que realiza el usuario
	
	
	/**
	 * Constructor
	 */
	public ProcesoLector (BaseDeDatos base, DatosComunes datos) {
		bd = base;
		dC= datos;
	}
	
	/**
	 * Se ejecuta el protocolo de entrada necesario dando preferencia a los procesos
	 * escritores sobre los procesos lectores pero permitiendo a varios procesos 
	 * lectores leer a la vez de un documento
	 * @param pos --> registro de clave sobre el que se esta trabajando
	 */
	private void protocoloEntrada(int pos){
		
		/*
		 * if escritor escribiendo OR esperando
		 * 		esperarParaLeer
		 * end if
		 * avisar “sig.” lector espera
		 */
		
		dC.waitMutex(pos);
		if (dC.getEscribiendo(pos) || dC.getNumEE(pos)>0){
			dC.AumentarNumLE(pos);
			dC.signalMutex(pos);
			dC.waitPuedesLeer(pos);
			dC.DisminuirNumLE(pos);
		}
		dC.AumentarNumLec(pos);
		if (dC.getNumLE(pos)>0) dC.signalPuedesLeer(pos);
		else dC.signalMutex(pos);
	}
	
	/**
	 * Se ejecuta el protocolo de salida necesario dando preferencia a los procesos
	 * escritores sobre los procesos lectores pero permitiendo a varios procesos 
	 * lectores leer a la vez de un documento
	 * @param pos --> registro de clave sobre el que se esta trabajando
	 */
	private void protocoloSalida(int pos){
		
		/*
		 * if último AND escritor espera
		 * 		avisarle
		 * end if
		 */
		
		dC.waitMutex(pos);
		dC.DisminuirNumLec(pos);
		if (dC.getNumLec(pos)==0 && dC.getNumEE(pos)>0) dC.signalPuedesEscribir(pos);
		else dC.signalMutex(pos);
	}
	
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en leer de una base de datos
	* según las especificaciones del ejercicio 5 de la práctica.	
	 */
	public void run (){
		//Generador de números aleatorios
		Random generador = new Random();
		
		/*
		 * Simula un usuario de la base de datos que realiza <LECTURAS> operaciones de 
		 * lectura de datos
		 */
		for (int i=0;i<LECTURAS; i++){
			//Genera la clave aleatoria
			int aleatorio = generador.nextInt(100);
			int clave_aleatoria = aleatorio+1001;
			
			//Inicio protocolo de entrada
			protocoloEntrada(aleatorio);
			
			//Lee los datos de la clave elegida
			bd.getNombre(clave_aleatoria);
			bd.getApellidos(clave_aleatoria);
			bd.getDireccion(clave_aleatoria);
			
			//Inicio protocolo de salida
			protocoloSalida(aleatorio);
			
		}
	}

}
