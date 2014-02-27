/**
* @author Pablo Lanaspa
* @version
* @date 10/12/2013
*/
package practica5.practica_5_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Conexiones implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private Socket clientSocket;//Socket que permite la conexi�n entre el Cliente y el Servidor
	private Monitor monitor;//Monitor que se encarga de la gesti�n en exclusi�n mutua del vag�n
	
	/**
	 * M�todo Constructor
	 * @param clientSocket
	 * @param monitor
	 */
	public Conexiones (Socket clientSocket, Monitor monitor){
		this.clientSocket = clientSocket;
		this.monitor = monitor;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en atender �nicamente a un cliente
	* aceptado por el servidor. Le ofrece los servicios de la compra de billetes de un 
	* vag�n de tren. Tiene acceso a la "base de datos" del servidor acerca del vag�n.
	*/
	@Override
	public void run() {
		try {
			conexion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo privado que analiza los mensajes recibidos por un �nico cliente y le da
	 * respuesta de acuerdo a lo solicitado por el cliente.
	 * @throws IOException
	 */
	private void conexion() throws IOException{

		// Inicializar los canales de comunicaci�n usados en el
		// socket para comunicarse con el cliente.
		// El OutputStream permite enviar mensajes al cliente
		// El InputStream permite recibir al servidor
		// mensajes emitidos por el proceso cliente

		PrintWriter salidaHaciaElCliente = new PrintWriter(
				clientSocket.getOutputStream(), true);
		BufferedReader entradaDesdeElCliente = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));

		//Leemos la informaci�n enviada por el cliente
		String inputLine = "";
		inputLine = entradaDesdeElCliente.readLine();
		
		//Inicializamos y declaramos variables necesarias para el bucle de atenci�n al cliente
		Scanner lector;
		int fila = -1; //Al principio -1, para darle un valor imposible inicial
		int columna = -1;//Al principio -1, para darle un valor imposible inicial
		String respuesta;

		//La conexi�n finaliza cuando se recibe un string = "FINAL SERVICIO"
		while ((inputLine != null)
				&& (!inputLine.equals("FINAL SERVICIO"))) {
			try {
				//Leemos los datos que nos interesa del mensaje del cliente
				lector = new Scanner (inputLine);
				lector.next();
				fila = lector.nextInt();
				columna = lector.nextInt();
				System.out.println("(Servidor) Intentando reservar asiento --> " +
						"Fila: "+fila+" Columna: "+columna);
				//Iniciamos el proceso de reservar del asiento solicitado y guardamos
				//en respuesta la respuesta a enviar a dicha reserva al cliente
				respuesta = monitor.reservar(fila, columna);
			}
			catch (Exception er){
				respuesta = "Error en el formato de envio de asientos. Inserte:\nAsiento: <fila> <columna>\n";
			}
			// Enviar la respuesta al cliente
			salidaHaciaElCliente.println(respuesta);
			System.out.println("(Servidor) Texto enviado al cliente con solicitud " +
					"Fila: "+fila+" Columna: "+columna+ ". Respuesta --> "+ respuesta);

			// Recibir nueva petici�n del cliente
			inputLine = entradaDesdeElCliente.readLine();
		}

		// Al cerrar cualquiera de los canales de
		// comunicaci�n usados por un socket, el socket se cierra.
		// Para asegurarse que se env�an las respuestas que
		// est�n en el buffer cerramos el OutputStream.
		salidaHaciaElCliente.close();
		
		System.out.println("Socket cerrado");
	}

}
