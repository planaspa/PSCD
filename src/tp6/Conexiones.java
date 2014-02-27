/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * Establece la conexion con el cliente y comienza la transferencia de informacion,
 * negocia con el y obtiene una imagen, el tiempo a mostrarla y su peso. El servidor
 * pone un precio al anuncio de la imagen y en funcion de la respuesta del cliente
 * le pedira su numero de visa, si este acepta el contrato su imagen se encolara y 
 * tendra que esperar su turno para ser mostrada el tiempo especificado por el cliente.
 * Durante toda la ejecucion se tienen en cuenta excepciones del Socket y timeOuts, si
 * el cliente tarda mucho en responder se cerrara su conexion. El administrador tambien
 * puede decidir cerrar el servidor, en ese caso se atenderan los clientes que ya estaban
 * negociando y las imagenes en Cola, posteriormente se cierra el servidor no aceptando
 * mas conexiones.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Conexiones implements Runnable {

	/*
	 * Atributos de la clase
	 */
	private Socket clientSocket;	//Socket que permite la conexión entre el Cliente y el Servidor
	private Monitor monitor;		//Monitor que se encarga de la gestión en exclusión mutua de la informacion
	private NodoNegociacion nodo; 	// NodoNegociacion encargado de almacenar los datos de cada nodo
	private GestorImagenes gestor;	// GestorImagenes que se encarga de mostrar/eliminar imagenes, actualizar las ventanas
											// de Datos y controlar la pulsacion del boton
	/**
	 * Método Constructor que recibe un Socket con el que establecer la conexion, un Monitor
	 * encargado de la gestion de la informacion y un GestorImagenes encargado de la gestion
	 * de las ventanas usadas
	 * @param clientSocket
	 * @param monitor
	 */
	public Conexiones (Socket clientSocket, Monitor monitor, GestorImagenes gestor){
		// Inicializacion de los atributos
		this.clientSocket = clientSocket;
		this.monitor = monitor;
		nodo = new NodoNegociacion();
		this.gestor = gestor;
	}
	
	/**
	* Devuelve void. 
	* Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	* el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	* Cuando finaliza la ejecución del método run() el thread muere.	
	* En este caso la ejecución del método run() consiste en atender únicamente a un cliente
	* aceptado por el servidor. Le ofrece los servicios de la compra de billetes de un 
	* vagón de tren. Tiene acceso a la "base de datos" del servidor acerca del vagón.
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
	 * Método privado que analiza los mensajes recibidos por un único cliente y le da
	 * respuesta de acuerdo a lo solicitado por el cliente.
	 * @throws IOException
	 */
	private void conexion() throws IOException{
		
		monitor.nuevoCliente();
		// Inicializar los canales de comunicación usados en el
		// socket para comunicarse con el cliente.
		// El OutputStream permite enviar mensajes al cliente
		// El InputStream permite recibir al servidor
		// mensajes emitidos por el proceso cliente
		PrintWriter salidaHaciaElCliente = new PrintWriter(
				clientSocket.getOutputStream(), true);
		BufferedReader entradaDesdeElCliente = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));

		
		
		//Leemos la información enviada por el cliente
		String inputLine = "";
		
		try{
			inputLine = entradaDesdeElCliente.readLine();
			
			//Inicializamos y declaramos variables necesarias para el bucle de atención al cliente	
			boolean nosalir = true;
			long t = System.currentTimeMillis();
			//La conexión finaliza cuando se recibe un string = "FINAL SERVICIO"
			while (nosalir && (inputLine != null)
					&& (!inputLine.equals("FINAL SERVICIO"))) {
				
				// Comenzamos las negociaciones con el cliente, en caso de error
				// lo muestra por pantalla
				if (inputLine.equals("INICIO NEGOCIACION")){
					String s = "";
					try{
					s = negociacionInicial(entradaDesdeElCliente.readLine(),t);
					}
					catch (Exception er){
						System.out.println("ERROR DESCONOCIDO: " + er.getMessage());
						nosalir = false;
					}
					//Devolvemos la respuesta correspondiente al cliente
					salidaHaciaElCliente.println(s);
	
				}
				else if (inputLine.length()> 15 && inputLine.substring(0, 15).equals("ACEPTO CONTRATO")){
					//El cliente ha aceptado el precio ofrecido
					System.out.println("El cliente ha aceptado el contrato");
					try{
						Scanner lectura = new Scanner (inputLine);
						lectura.next();//ACEPTO
						lectura.next();//CONTRATO.
						lectura.next();//VISA:
						long visa = lectura.nextLong();
						lectura.close();
						
						nodo.setVisa(visa);
						//Se procede a pasar el pago y cargar la publicidad
						if (nodo.isVisaCorrecta() && monitor.pago()){
							//Se ha realizado correctamente
							//Cargar la imagen	
							
							// Recepcion de la imagen
							byte[] imagen = new byte[nodo.getPesoImagen()];
							System.out.println("Recibiendo imagen...");
							salidaHaciaElCliente.println("ENVIA");
							for (int i = 0; i<imagen.length; i++){
								imagen[i] = (byte) clientSocket.getInputStream().read();
							}
							nodo.setImagen(imagen);
							
							// Encolamos la imagen
							System.out.println("Encolando imagen...");
							String salida = monitor.encolar(nodo);
							
							//Publicamos la imagen
							System.out.println("Imagen Encolada, publicando...");
							Thread timg = new Thread (new Imagen (gestor, monitor));
							timg.start();
							
							System.out.println("Imagen publicada...");
							
							//Devolver mensaje satisfactorio
							salidaHaciaElCliente.println(salida);
						}
						else{
							if (nodo.isVisaCorrecta()){
							//Cola llena
							}
							else {
							//Visa incorrecta
							}
						}
					}
					catch (Exception er){
						//ERROR EN LA LECTURA DE DATOS DEL CLIENTE
						System.out.println("ERROR EN LA LECTURA DE DATOS DEL CLIENTE");
					}
				}
				else if (inputLine.equals("RECHAZO CONTRATO")){
					//El cliente ha rechazado el precio ofrecido
					
					//Se da por cerrada la negociación y se procede a terminar la conexión
				}
				else{
					//ERROR EN LA ENTRADA DE DATOS POR PARTE DEL CLIENTE
					System.out.println("ERROR EN LA ENTRADA DE DATOS POR PARTE DEL CLIENTE: "+
					inputLine);
				}
				inputLine = entradaDesdeElCliente.readLine();
				
			}
			salidaHaciaElCliente.println("FINAL SERVICIO OK");
		}
		// Capturamos Excepciones
		catch (SocketTimeoutException er){
			System.err.println("(Servidor) Read timed out: Demasiado tiempo sin recibir respuesta");
		}
		catch (SocketException er){
			System.err.println("El cliente ha cerrado la conexión");
		}
		
		// Al cerrar cualquiera de los canales de
		// comunicación usados por un socket, el socket se cierra.
		// Para asegurarse que se envían las respuestas que
		// están en el buffer cerramos el OutputStream.
		salidaHaciaElCliente.close();
		
		System.out.println("Socket cerrado");
		monitor.finCliente();
		
	}
	
	/**
	 * El metodo recibe un String que corresponde con la entrada del cliente.
	 * Se encarga de las negociaciones con el cliente, en las que ovtiene tiempo
	 * y peso de la imagen a mostrar en la ventana, devuelve la propuesta de la hora
	 * de muestra de su imagen
	 * @param inputLine
	 * @return
	 * @throws IOException
	 */
	private String negociacionInicial(String inputLine, long t) throws IOException{
		//El cliente quiere iniciar la negociación con el servidor
		String salida = null;
		int peso = -1;
		int tiempo = -1;
		
		//Leemos los datos que nos envía
		try{
			Scanner lectura = new Scanner (inputLine);
			lectura.next(); //Peso
			lectura.next(); //de
			lectura.next(); //la
			lectura.next();	//imagen:
			peso = lectura.nextInt();
			lectura.next(); //Tiempo:
			tiempo = lectura.nextInt();
			lectura.close();
		}
		catch (Exception er){
			salida = "ERROR: Mensaje ilegible.";
		}
		if (salida == null){ //Si no ha habido error
			nodo.setPesoImagen(peso);
			nodo.setTiempo(tiempo);
			
			// Tiempo de conexion y negociacion con el Socket
			long tfinal = System.currentTimeMillis() -t;
			tfinal = monitor.getT()+ tfinal;
			
			monitor.setT(tfinal);
			//Nos ponemos en contacto con el gestor del servicio (monitor)
			salida = monitor.negociacion(nodo);
			
		}
		
		return salida;
	}
	
}
