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
		// conexi�n de un cliente al servidor mediante
		// el m�todo accept()
		
		while (true){
			Socket clientSocket = null;
			try {
				//A cada nueva petici�n de conexi�n al servidor, se le acepta y
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