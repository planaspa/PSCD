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

package practica5.practica_5_1;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Servidor {
	
	/**
	 * M�todo privado que calcula el n�mero de vocales en un determinado String
	 * @param frase
	 * @return
	 */
	private static int calculaNumeroVocales(String frase) {
		int resultado = 0;
		frase = frase.toUpperCase();
		for (int i=0; i<frase.length();i++){
			if (frase.charAt(i) == 'A' || frase.charAt(i) == 'E' || frase.charAt(i) == 'I' 
					|| frase.charAt(i) == 'O'|| frase.charAt(i) == 'U' || 
					frase.charAt(i) == '�' || frase.charAt(i) == '�' ||
					frase.charAt(i) == '�' || frase.charAt(i) == '�'|| 
					frase.charAt(i) == '�' || frase.charAt(i) == '�') resultado ++;	
		}
		
		return resultado;
	}

	public static void main(String[] args) throws IOException {

		int SERVER_PORT = 32013;
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
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		// Inicializar los canales de comunicaci�n usados en el
		// socket para comunicarse con el cliente.
		// El OutputStream permite enviar mensajes al cliente
		// El InputStream permite recibir al servidor
		// mensajes emitidos por el proceso cliente

		PrintWriter salidaHaciaElCliente = new PrintWriter(
				clientSocket.getOutputStream(), true);
		BufferedReader entradaDesdeElCliente = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));

		// Contar las vocales de las frases enviadas por el cliente
		String inputLine = "";
		inputLine = entradaDesdeElCliente.readLine();

		while ((inputLine != null)
				&& (!inputLine.equals("FINAL SERVICIO"))) {
			// Calcular el n�mero de vocales que
			// tiene la respuesta.
			String respuesta = "N�mero de vocales: "
					+ calculaNumeroVocales(inputLine);

			// Enviar la respuesta al cliente
			salidaHaciaElCliente.println(respuesta);

			// Recibir nueva petici�n del cliente
			inputLine = entradaDesdeElCliente.readLine();
		}

		// Al cerrar cualquiera de los canales de
		// comunicaci�n usados por un socket, el socket se cierra.
		// Para asegurarse que se env�an las respuestas que
		// est�n en el buffer cerramos el OutputStream.
		salidaHaciaElCliente.close();

		// Cierra el servidor de sockets
		serverSocket.close();

		System.out.println("Bye, bye.");
	}
}