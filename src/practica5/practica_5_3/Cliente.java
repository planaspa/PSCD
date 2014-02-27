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
* @version Cliente Modificado
* @date 10/12/2013
*/
package practica5.practica_5_3;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cliente implements Runnable {

	private int id;			//ID �nica de cada cliente
	private int filas;		//N�mero de filas del vag�n
	private int columnas;	//N�mero de columnas del vag�n
	private int iteraciones;//N�mero de compras que quiere realizar cada cliente
	
	/**
	 * M�todo Constructor
	 * @param id
	 * @param filas
	 * @param columnas
	 * @param iteraciones
	 */
	public Cliente(int id, int filas, int columnas, int iteraciones){
		this.id = id;
		this.filas = filas;
		this.columnas = columnas;
		this.iteraciones = iteraciones;
	}

	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en realizar las tareas que un 
	* usuario podr�a hacer al conectarse a un servidor y realizar la compra de los billetes
	* de un vag�n determinado.	
	*/
	@Override
	public void run() {
		try {
			cliente();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo privado que ejecuta la conexi�n de un cliente con el servidor y realiza
	 * las operaciones necesarias para la compra de los billetes que se desea.
	 * @throws IOException
	 */
	private void cliente() throws IOException{
		/*
		 * PROTOCOLO DE CONEXION E INICIALIZACI�N DE VARIABLES NECESARIAS
		 * PARA LA COMUNICACI�N CON EL SERVIDOR
		 */
		// Varibales para almacenar la direcci�n
		// y n�mero de puerto donde escucha el
		// proceso servidor
		String SERVER_ADDRESS = "localhost";
		int SERVER_PORT = 32001;

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
		
		/*
		 * FIN DEL PROTOCOLO DE CONEXION E INICIALIZACI�N DE VARIABLES NECESARIAS
		 * PARA LA COMUNICACI�N CON EL SERVIDOR
		 */
		
		/*
		 * COMIENZO DE LA ALGORITMIA NECESARIA PARA LA CORRECTA EJECUCI�N DE LAS
		 * COMPRAS SOBRE EL SERVIDOR
		 */
		
		//Para la primera vez se solicita un asiento random
		Random generador = new Random();
		int fila = generador.nextInt(filas);
		int columna = generador.nextInt(columnas);
		
		for (int i = 0; i<iteraciones; i ++){
			
			//El String a enviar al servidor sigue la siguiente estructura: 
			//		"Asiento: <fila> <columna> "
			String userInput = "Asiento: " + fila + " " + columna;
			
			//Colocamos en el canal de salida el string a enviar
			canalSalidaDirigidoAlServidor.println(userInput);
			System.out.println("Cliente "+id+", texto enviado al servidor --> "+userInput);
			
			//Leemos la respuesta del servidor y la almacenamos en respuesta
			String respuesta = "";
			String buffer = canalEntradaQueVieneDelServidor.readLine();
			while (!buffer.equals("")) {
				respuesta += buffer +"\n";
				buffer = canalEntradaQueVieneDelServidor.readLine();
			}
			System.out.println("Cliente "+id+", respuesta del servidor --> "+respuesta);
			
			//Analizamos la respuesta recibida y actuamos en consecuencia
			if (respuesta.equals("VAGON COMPLETO\n")){
				System.out.printf("Soy el cliente n�mero: %d y solo he podido " +
						"comprar %d billetes de %d.%n", id, i, iteraciones);
				i = iteraciones;//Forzamos la salida del bucle, no hay m�s asientos libres
			}
			else if (!respuesta.equals("RESERVADO\n")){ //El asiento est� ocupado
				//Pedimos un asiento random de la lista
				Scanner lectura = new Scanner (respuesta);
				//Leemos cuantos asientos libres hay en la lista, y elegimos uno
				int libres = generador.nextInt(lectura.nextInt());
				lectura.nextLine(); //Pasamos a la siguiente l�nea
				for (int j=0; j < libres; j++) 
					lectura.nextLine(); //Pasamos tantas l�neas como necesitemos
				
				//Recogemos solo los datos de la lista que nos interesan
				lectura.next(); //Asiento
				lectura.next(); //libre,
				lectura.next(); //Fila:
				fila = lectura.nextInt();
				lectura.next(); //Columna:
				columna = lectura.nextInt();
				
				lectura.close();
				//restamos a la variable i 1 porque en esta iteraci�n no ha comprado billete
				i--;
			}
			else if (respuesta.equals("RESERVADO\n")){ //El asiento ha sido reservado
				//Elegimos otro asiento aleatorio
				fila = generador.nextInt(filas);
				columna = generador.nextInt(columnas);
				
			}
		}
		System.out.printf("Soy el cliente n�mero %d, he terminado mis compras " +
				"y cierro el socket.%n", id);
		//Mandamos cerrar la conexi�n
		canalSalidaDirigidoAlServidor.println("FINAL SERVICIO");
		
		// Al cerrar cualquiera de los canales de
		// comunicaci�n usados por un socket, el socket se cierra.
		// Como no nos importa perder informaci�n cerramos el
		// canal de entrada.
		canalEntradaQueVieneDelServidor.close();

		// Cierre del Socket para comunicarse con el servidor.
		socketParaConectarseAlServidor.close();
		
	}
}