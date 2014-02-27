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
 * Proceso que simula a un usuario, el cual realiza <ACUTALIZACIONES> operaciones de 
 * actualización de datos sobre la base de datos indicada en el constructor, de acuerdo
 * a las especificaciones del enunciado.
 */
public class ProcesoEscritor implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private BaseDeDatos bd;						//Base de datos correspondiente
	private DatosComunes dC;					//Datos Comunes necesarios para la correcta implementación
	private final int ACTUALIZACIONES = 110;	//Numero de actualizaciones que realiza el usuario
	
	/**
	 * Constructor
	 */
	public ProcesoEscritor (BaseDeDatos base, DatosComunes datos) {
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
		 * if alguien escribiendo OR leyendo
		 * 		esperarParaEsc
		 * end if
		 */
		
		dC.waitMutex(pos);
		if (dC.getNumLec(pos)>0 || dC.getEscribiendo(pos)){
			dC.AumentarNumEE(pos);
			dC.signalMutex(pos);
			dC.waitPuedesEscribir(pos);
			dC.DisminuirNumEE(pos);
		}
		dC.setEscribiendo(pos, true);
		dC.signalMutex(pos);
	}
	
	/**
	 * Se ejecuta el protocolo de salida necesario dando preferencia a los procesos
	 * escritores sobre los procesos lectores pero permitiendo a varios procesos 
	 * lectores leer a la vez de un documento
	 * @param pos --> registro de clave sobre el que se esta trabajando
	 */
	private void protocoloSalida(int pos){
		
		/*
		 * if escritor esperando
		 * 		avisarle
		 * else if lector esperando
		 * 			avisarle
		 * 		end if
		 * end if
		 */
		
		dC.waitMutex(pos);
		dC.setEscribiendo(pos, false);
		if (dC.getNumEE(pos)>0) dC.signalPuedesEscribir(pos);
		else if (dC.getNumLE(pos)>0) dC.signalPuedesLeer(pos);
			else dC.signalMutex(pos);
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en actualizar una base de datos
	* según las especificaciones de cada ejercicio de la práctica.	
	*/
	public void run (){
		//Generador de números aleatorios
		Random generador = new Random();
		
		/*
		 * Simula un usuario de la base de datos que realiza <ACTUALIZACIONES> 
		 * operaciones de actualización de datos
		 */
		for (int i=0;i<ACTUALIZACIONES; i++){
			//Genera la clave aleatoria
			int aleatorio = generador.nextInt(100);
			int clave_aleatoria = aleatorio+1001;
			
			//Inicio protocolo de entrada
			protocoloEntrada(aleatorio);
			
			//Extraemos el número de modificaciones hasta el momento y lo aumentamos
			String nombre = bd.getNombre(clave_aleatoria);
			String veces_updated = nombre.substring(12);
			int veces = Integer.parseInt(veces_updated);			
			veces++;			
			//Actualizamos el resto de registros para dicha clave
			bd.updateNombre(clave_aleatoria, "Nombre_"+clave_aleatoria+"_"+veces);
			bd.updateApellidos(clave_aleatoria, "Apellidos_"+clave_aleatoria+"_"+veces);
			bd.updateDireccion(clave_aleatoria, "C\\ María de Luna "+clave_aleatoria+"_"+veces);
			
			//Inicio protocolo de salida
			protocoloSalida(aleatorio);
		}
	}

}
