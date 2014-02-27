/*
 * File:    Cliente.java
 *
 * Version: V1.0
 *
 * Date:    Noviembre 2013
 *
 * Com:     Implementa un cliente que se conecta a un puerto concreto donde un
 *          un servidor está escuchando. Se le suministran cadenas tomadas de stdin
 *          ý responde con el número de vocales que contiene (adaptable a cualquier
 *          otro procesamiento de cadenas).
 *          Material suministrado para la práctica 5 de PSCD, Universidad de Zaragoza
 */
/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Conecta con el servidor e inicializa una transferencia de informacion,
 * le envia una imagen con su peso y el tiempo a mostrar. El servidor propone
 * un precio por mostrarla y el cliente decide aceptar o rechazar en caso
 * de aceptar debe enviar su Visa para efectuar el pago y posteriormente
 * su imagen se mostrara, si el servidor informa de que todo se ha realizado
 * correctamente finaliza.
 * Durante toda la ejecucion se capturan Excepciones de Socket y los timeOuts
 * del cliente.
 * 
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Cliente implements Runnable {

	// Atributos del cliente
	private long visa;
	private byte[] imagen;
	private int timeout;
	
	/**
	 * Construcor del objeto Cliente, recibe un long correspondiente a su tarjeta de credito
	 * un array de Bytes correspondiente a una imagen y un entero correspondiente al tiempo
	 * maximo de timeOut
	 * @param visa
	 * @param imagen
	 * @param timeout
	 */
	public Cliente(long visa, byte[] imagen, int timeout){
		
		// Inicializacion de atributos
		this.visa = visa;
		this.imagen = imagen;
		this.timeout = timeout;
	}

	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en realizar las tareas que un 
	* usuario podría hacer al conectarse a un servidor y realizar la compra de una posicion en una pancarta
	* durante un tiempo determinado
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
	 * Método privado que ejecuta la conexión de un cliente con el servidor y realiza
	 * las operaciones necesarias para la compra de los billetes que se desea.
	 * @throws IOException
	 */
	private void cliente() throws IOException{
		/*
		 * PROTOCOLO DE CONEXION E INICIALIZACIÓN DE VARIABLES NECESARIAS
		 * PARA LA COMUNICACIÓN CON EL SERVIDOR
		 */
		// Varibales para almacenar la dirección
		// y número de puerto donde escucha el
		// proceso servidor
		String SERVER_ADDRESS = "localhost";
		int SERVER_PORT = 32002;

		// Creación del socket con el que se llevará a cabo
		// la comunicación con el servidor.
		Socket socketParaConectarseAlServidor = null;
		try {
			socketParaConectarseAlServidor = new Socket(SERVER_ADDRESS,
					SERVER_PORT);
			socketParaConectarseAlServidor.setSoTimeout(timeout); //Timeout para los reads
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + SERVER_ADDRESS);
			System.exit(1);
		}
		catch (ConnectException er){
			/*System.err.println("El servidor esta apagado. Imposible conectar.");
			System.exit(0);*/
			Thread.currentThread().interrupt();
		}
		
		// Inicialización de los flujos de datos del socket con los
		// que se lleva a cabo la comunicación con el servidor

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
		catch (NullPointerException er){
			System.err.println("La conexión ya no es posible");
			System.exit(1);
		}
		
		/*
		 * FIN DEL PROTOCOLO DE CONEXION E INICIALIZACIÓN DE VARIABLES NECESARIAS
		 * PARA LA COMUNICACIÓN CON EL SERVIDOR
		 */
		
		/*
		 * COMIENZO DE LA ALGORITMIA NECESARIA PARA LA CORRECTA EJECUCIÓN DE LAS
		 * COMPRAS SOBRE EL SERVIDOR
		 */
		Random generador = new Random();
		int peso = imagen.length;
		int tiempo = (generador.nextInt(6)+5); // Tiempo en segundos
		
		
		System.out.println("Cliente, mensaje enviado: INICIO NEGOCIACION");
		
		canalSalidaDirigidoAlServidor.println("INICIO NEGOCIACION");
		/*
		 * INICIO PROCESO DE NEGOCIACIÓN
		 */
		//Envío el peso de la imagen y cuanto tiempo quiero que sea mostrada
		
		System.out.println("Cliente, mensaje enviado: Peso de la imagen: " + peso + " Tiempo: "
				+ tiempo);
		canalSalidaDirigidoAlServidor.println("Peso de la imagen: " + peso + " Tiempo: "
				+ tiempo);
		
		//Recibimos respuesta del servidor ante nuestra petición
		try{
			String respuesta=canalEntradaQueVieneDelServidor.readLine();
			
			System.out.println("Cliente, respuesta recibida del servidor: "+respuesta);
			
			if (primeraRespuesta (respuesta)){
				//Si todo ha ido bien en la primera respuesta seguimos
				//Elige aceptar o rechazar la respuesta según los precios y la hora recibidas
				double porcentaje = generador.nextDouble();
				
				// Si acepto el contrato propuesto por el servidor envio la Visa a este para realizar el pago
				// este me respondera indicandome cuando debo enviar la imagen
				if(porcentaje > 0.5){
					System.out.println("ACEPTO CONTRATO. VISA: "+visa);
					canalSalidaDirigidoAlServidor.println("ACEPTO CONTRATO. VISA: "+visa);
					String envia=canalEntradaQueVieneDelServidor.readLine();
					
					if (envia.equals("ENVIA")){
						System.out.println("Procedo a enviar la imagen");
						//Enviamos la imagen byte a byte
						for (int i=0; i<imagen.length; i++){
							socketParaConectarseAlServidor.getOutputStream().write(imagen[i]);
						}
						String anuncioOK=canalEntradaQueVieneDelServidor.readLine();
						
						if (!anuncioOK.equals("Anuncio correctamente programado")){
							System.err.println("(CLIENTE):Error en el mensaje del servidor");
						}
					}
					else {
						System.err.println("(CLIENTE):Error en el mensaje del servidor");
					}

				}
				else{
					canalSalidaDirigidoAlServidor.println("RECHAZO CONTRATO");
					System.out.println("Rechazo contrato");
				}
				
			}
			else{
				System.err.println("(CLIENTE):Error del servidor");
			}
			 // Comprobamos que todo a acabado conectamente
			canalSalidaDirigidoAlServidor.println("FINAL SERVICIO");
			if (!canalEntradaQueVieneDelServidor.readLine().equals("FINAL SERVICIO OK"))
				System.err.println("Error en la desconexión intencionada");
		}// Capturamos Excepciones
		catch (SocketTimeoutException er){
			System.err.println("(Cliente) Read timed out: Demasiado tiempo sin recibir respuesta");
		}
		catch (SocketException er){
			System.err.println("El servidor está apagado");
		}
		
		System.out.println("FINAL SERVICIO");
		// Al cerrar cualquiera de los canales de
		// comunicación usados por un socket, el socket se cierra.
		// Como no nos importa perder información cerramos el
		// canal de entrada.
		canalEntradaQueVieneDelServidor.close();

		// Cierre del Socket para comunicarse con el servidor.
		socketParaConectarseAlServidor.close();
		
	}
	
	private boolean primeraRespuesta (String respuesta){
		boolean salida = false;
		//Analizamos la respuesta del servidor
		try{
			Scanner lectura = new Scanner (respuesta);
			if (lectura.next().equals("ERROR:")){
				//Error en el servidor. Diferenciamos que tipo de error al seguir leyendo
				System.out.println("ERROR: "+lectura.nextLine());
			}
			else if (lectura.next().equals("DEL")){ //Referido al segundo token de "RESPUESTA DEL SERVIDOR
				//Analizamos la respuesta
				salida = true;
			}
			else {
				System.err.println("(CLIENTE):Error en el canal o en la lectura de datos");
			}
			lectura.close();
		}
		catch (Exception er){
			System.err.println("(CLIENTE):Error en el canal o en la lectura de datos");
		}
		return salida;
	}
	
}