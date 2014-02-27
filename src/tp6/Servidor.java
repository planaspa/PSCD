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
* @author Pablo Lanaspa y Manuel Lagunas
* @version Servidor Modificado
* @date 12/01/2014
*/
package tp6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Inicializa un nuevo Socket con puerto [SERVER_PORT]. Lanzara un proceso
 * por cada conexion entrante de un Cliente, mientras el administrador no
 * decida cerrarlo, en cuyo caso atenderá los clientes que ya estuvieran
 * negociando y cerrara la conexion.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Servidor {
	

	/*
	 * VARIABLES PROPIAS DEL SERVIDOR
	 */
	
	final static int TIMEOUT_SERVER = 1000; //1000 ms --> 1 second para socket no bloqueante
	final static int NUM_IMAGENES = 10; //Número máximo de imágenes en la cola
	final static int TIMEOUT = 10000; //Tiempo en ms necesario para que una conexión espire si no hay respuesta en los correspondientes read
	
	/**
	 * Método privado que permite la creación de un servidor que vaya creando threads 
	 * distintos para cada una de las peticiones de conexión de cada cliente.
	 * @throws IOException
	 */
	public static void main (String [] Argumentos) throws IOException{
		//Creamos el monitor que gestionará la información del servidor
		Monitor monitor = new Monitor (NUM_IMAGENES);
		
		//Creamos el gestor de imágenes, que gestionará la valla publicitaria
		GestorImagenes gestor = new GestorImagenes (monitor);

		
		int SERVER_PORT = 32002;
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
		serverSocket.setSoTimeout(TIMEOUT_SERVER); //Timeout para socket bloqueante
		boolean salir = false;
		while (!salir){
			Socket clientSocket = null;
			
			try {
				//A cada nueva petición de conexión al servidor, se le acepta y
				//se le crea un thread nuevo
				clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(TIMEOUT);
				Thread conexion = new Thread (
						new Conexiones (clientSocket, monitor, gestor));
				//Lanzamos el thread correspondiente
				conexion.start();
				salir = monitor.getApagar();
			} 
			catch (SocketTimeoutException er){
				//Ha saltado el tiemout del socket.accept, miramos si hay que apagar el server
				salir = monitor.getApagar();
			}
			catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}
		
		monitor.todoAcabado();
		gestor.cerrarVentanas();
		//Apagamos el servidor
		serverSocket.close();
		System.exit(0);
	}
}