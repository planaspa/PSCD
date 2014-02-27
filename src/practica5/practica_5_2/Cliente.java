/*
 * File:    Cliente.java
 *
 * Version: V1.0
 *
 * Date:    Noviembre 2013
 *
 * Com:     Implementa un cliente que se conecta a un puerto concreto donde un
 *          un servidor est� escuchando. Se le suministran cadenas tomadas de stdin
 *          � responde con el n�mero de vocales que contiene (adaptable a cualquier
 *          otro procesamiento de cadenas).
 *          Material suministrado para la pr�ctica 5 de PSCD, Universidad de Zaragoza
 */
/**
* @author Pablo Lanaspa
* @version Cliente Modificada
* @date 10/12/2013
*/
package practica5.practica_5_2;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cliente {

	public static void main(String[] args) throws IOException {
		// Varibales para almacenar la direcci�n
		// y n�mero de puerto donde escucha el
		// proceso servidor
		String SERVER_ADDRESS = "localhost";
		int SERVER_PORT = 32023;

		// Creaci�n del socket con el que se llevar� a cabo
		// la comunicaci�n con el servidor.
		Socket socketParaConectarseAlServidor = null;
		try {
			socketParaConectarseAlServidor = new Socket(SERVER_ADDRESS,
					SERVER_PORT);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + SERVER_ADDRESS);
			System.exit(1);
		}
		
		// Inicializaci�n de los flujos de datos del socket con los
		// que se lleva a cabo la comunicaci�n con el servidor

		PrintWriter canalSalidaDirigidoAlServidor = null;
		BufferedReader canalEntradaQueVieneDelServidor = null;
		try {
			canalSalidaDirigidoAlServidor = new PrintWriter(
					socketParaConectarseAlServidor.getOutputStream(), true);
			canalEntradaQueVieneDelServidor = new BufferedReader(
					new InputStreamReader(
							socketParaConectarseAlServidor.getInputStream()));
		} catch (IOException e) {
			System.err.println("Problemas I/O para " + "conexion "
					+ SERVER_ADDRESS);
			System.exit(1);
		}

		// Definici�n de un buffer de entrada para leer
		// de la entrada standard.
		BufferedReader entradaStandard = new BufferedReader(
				new InputStreamReader(System.in));
		String userInput = "";

		// Protocolo de comunicaci�n con el Servidor.
		// Mientras no se reciba la secuencia "FINAL SERVICIO"
		// el servidor contar� el n�mero de vocales que
		// aparecen en las frases que le env�a el cliente.
		// El cliente obtiene las frases que le pasa al servidor
		// del usuario que lo est� ejecutando.

		while (!(userInput.equals("FINAL SERVICIO"))) {
			System.out.print("Texto: ");
			userInput = entradaStandard.readLine();
			if (userInput != null) {
				canalSalidaDirigidoAlServidor.println(userInput);
				String respuesta = canalEntradaQueVieneDelServidor.readLine();
				if (respuesta != null) {
					System.out.println("Texto modificado: " + respuesta);
				} else {
					System.out.println("Comunicaci�n finalizada");
				}
			} else {
				System.err.println("Entrada dada no v�lida");
			}
		}

		// Al cerrar cualquiera de los canales de
		// comunicaci�n usados por un socket, el socket se cierra.
		// Como no nos importa perder informaci�n cerramos el
		// canal de entrada.
		canalEntradaQueVieneDelServidor.close();

		// Cierre del Socket para comunicarse con el servidor.
		socketParaConectarseAlServidor.close();
	}
}