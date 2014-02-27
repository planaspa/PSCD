package practica5.practica_5_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Conexiones implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private Socket clientSocket; //Socket que permite la conexión entre el Cliente y el Servidor
	

	/**
	 * Método Constructor
	 * @param clientSocket
	 */
	public Conexiones (Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	/**
	 * Método privado que calcula el número de vocales en un determinado String
	 * @param frase
	 * @return
	 */
	private static int calculaNumeroVocales(String frase) {
		int resultado = 0;
		frase = frase.toUpperCase();
		for (int i=0; i<frase.length();i++){
			if (frase.charAt(i) == 'A' || frase.charAt(i) == 'E' || frase.charAt(i) == 'I' 
					|| frase.charAt(i) == 'O'|| frase.charAt(i) == 'U' || 
					frase.charAt(i) == 'Á' || frase.charAt(i) == 'É' ||
					frase.charAt(i) == 'Í' || frase.charAt(i) == 'Ó'|| 
					frase.charAt(i) == 'Ú' || frase.charAt(i) == 'Ü') resultado ++;	
		
		}
		return resultado;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en recibir los mensajes que 
	* envía un determinado cliente y devolverle el número de vocales halladas en dicho
	* mensaje.
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
	
	private void conexion() throws IOException{

		// Inicializar los canales de comunicación usados en el
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
			// Calcular el número de vocales que
			// tiene la respuesta.
			String respuesta = "Número de vocales: "
					+ calculaNumeroVocales(inputLine);

			// Enviar la respuesta al cliente
			salidaHaciaElCliente.println(respuesta);

			// Recibir nueva petición del cliente
			inputLine = entradaDesdeElCliente.readLine();
		}

		// Al cerrar cualquiera de los canales de
		// comunicación usados por un socket, el socket se cierra.
		// Para asegurarse que se envían las respuestas que
		// están en el buffer cerramos el OutputStream.
		salidaHaciaElCliente.close();
	}

}
