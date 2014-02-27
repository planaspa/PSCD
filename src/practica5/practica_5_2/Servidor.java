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
* @version Servidor moficado
* @date 10/12/2013
*/
package practica5.practica_5_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {


	public static void main(String[] args) throws IOException {

		int SERVER_PORT = 32023;
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
				Thread conexion = new Thread (new Conexiones (clientSocket));
				//Lanzamos el thread correspondiente
				conexion.start();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}
		// UNREACHABLE
	}
}