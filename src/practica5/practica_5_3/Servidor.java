/*
 * File:    Servidor.java
 *
 * Version: V1.0
 *
 * Date:    Noviembre 2013
 *
 * Com:     Implementa un servidor que atiende a un único cliente. Procesa cadenas de
 *          texto que le envía (cuenta su número de vocales), hasta que llega una concreta
 *          para marcar la intención de cerrar la conexión.
 *          Material suministrado para la práctica 5 de PSCD, Universidad de Zaragoza
 */
/**
* @author Pablo Lanaspa
* @version Servidor Modificado
* @date 10/12/2013
*/
package practica5.practica_5_3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor implements Runnable {
	
	private int filas;	//Filas del vagón
	private int columnas;	//Columnas del vagón
	
	/**
	 * Método Constructor
	 * @param filas
	 * @param columnas
	 */
	public Servidor(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;	
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en la creación de un servidor
	* que atienda a las peticiones de los clientes.	
	*/
	@Override
	public void run() {
		try {
			servidor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Método privado que permite la creación de un servidor que vaya creando threads 
	 * distintos para cada una de las peticiones de conexión de cada cliente.
	 * @throws IOException
	 */
	private void servidor () throws IOException{
		//Creamos el monitor que gestionará la información del servidor
		Monitor monitor = new Monitor (filas, columnas);
		
		int SERVER_PORT = 32001;
		// Inicializar el socket donde escucha el servidor en
		// local y en el puerto SERVER_PORT
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Problemas en puerto: " + SERVER_PORT);
			System.exit(1);
		}
		
		// Inicializar el socket del cliente con el que se va a
		// comunicar el servidor, es decir se acepta la
		// conexión de un cliente al servidor mediante
		// el método accept()
		while (true){
			Socket clientSocket = null;
			try {
				//A cada nueva petición de conexión al servidor, se le acepta y
				//se le crea un thread nuevo
				clientSocket = serverSocket.accept();
				Thread conexion = new Thread (new Conexiones (clientSocket, monitor));
				//Lanzamos el thread correspondiente
				conexion.start();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}
		//UNREACHABLE

	}
}