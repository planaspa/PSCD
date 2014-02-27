/*
 * File:    Servidor.java
 *
 * Version: V1.0
 *
 * Date:    Noviembre 2013
 *
 * Com:     Implementa un servidor que atiende a un �nico cliente. Procesa cadenas de
 *          texto que le env�a (cuenta su n�mero de vocales), hasta que llega una concreta
 *          para marcar la intenci�n de cerrar la conexi�n.
 *          Material suministrado para la pr�ctica 5 de PSCD, Universidad de Zaragoza
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
	
	private int filas;	//Filas del vag�n
	private int columnas;	//Columnas del vag�n
	
	/**
	 * M�todo Constructor
	 * @param filas
	 * @param columnas
	 */
	public Servidor(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;	
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en la creaci�n de un servidor
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
	 * M�todo privado que permite la creaci�n de un servidor que vaya creando threads 
	 * distintos para cada una de las peticiones de conexi�n de cada cliente.
	 * @throws IOException
	 */
	private void servidor () throws IOException{
		//Creamos el monitor que gestionar� la informaci�n del servidor
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
		// conexi�n de un cliente al servidor mediante
		// el m�todo accept()
		while (true){
			Socket clientSocket = null;
			try {
				//A cada nueva petici�n de conexi�n al servidor, se le acepta y
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