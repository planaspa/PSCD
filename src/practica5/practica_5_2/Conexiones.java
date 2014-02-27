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
	private Socket clientSocket; //Socket que permite la conexi�n entre el Cliente y el Servidor
	

	/**
	 * M�todo Constructor
	 * @param clientSocket
	 */
	public Conexiones (Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
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
	
	/**
	* Devuelve void. 
	* Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	* el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	* En este caso la ejecuci�n del m�todo run() consiste en recibir los mensajes que 
	* env�a un determinado cliente y devolverle el n�mero de vocales halladas en dicho
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
	}

}
