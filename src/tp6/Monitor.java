/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.util.Calendar;

/**
 * Clase encargada del acceso y la gestion de la informacion de forma concurrente, compuesta por un conjunto de
 * metodos que nos permiten encolar los objetos NodoNegociacion de cada cliente, obtener el numero de clientes
 * que han pagado cuyas imagenes no han sido encoladas, el numero de clientes que se encuentran negociando, es capaz
 * de ordenar al servidor que finalice y se cierre, gestiona los huecos libres en la ventana de imagenes y se encarga
 * de una parte de la negociacion con el cliente. Mucha de esta informacion es obligatorio que se gestione en 
 * exclusion mutua por eso los metodos de esta clase seran synchronized, para poder dormir y despertar procesos.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Monitor {

	/*
	 * VARIABLES DEL MONITOR
	 */
	private Cola cola; 				// Cola encargada de gestionar los NodosNegociacion
	private int pagadosYnoEnCola; 	// Clientes que han efectuado el pago y no estan sus imagenes encoladas
	private int maximo;				//Número de máximo de elementos en cola
	private int porPublicar; 		// Imagenes encoladas sin publicar
	private int huecosEnPantalla; 	// Huecos libres en la ventana de imagenes
	private int nClientesNeg; 		// Numero de clientes negociando
	private boolean apagar;		 	//Variable que marca si hay que apagar el servidor
	private String texto; 			// Texto que se mostrara en la ventana de datos
	private boolean cerrarDatos; 	// Boolean que marca el fin de la ejecucion de la ventana de datos
	private long t;

	/**
	 * Método constructor, recibe un entero correspondiente al maximo numero de elementos en cola
	 */
	public Monitor(int maximo){
		// Inicializacion de los atributos
		cola = new Cola (maximo);
		pagadosYnoEnCola = 0;
		this.maximo = maximo;
		porPublicar = 0;
		huecosEnPantalla = 4;
		nClientesNeg = 0;
		apagar = false;
		texto = "";
		cerrarDatos = false;
	}
	/**
	 * El metodo recibe una serie de Strings correspondientes a el tiempo que tarda en publicarse
	 * su anuncio y a la hora actual. Calcula la hora final a la que se ha de mostrar un anuncio
	 * concreto
	 * @param hora
	 * @param seg
	 * @param min
	 * @param tUltimoAnuncio
	 * @param ms
	 * @return
	 */
	private String obtenerHora(int hora, int seg,int min, int tUltimoAnuncio, int ms){
		
		// Obtenemos segundos minutos y ms totales, pueden sobrepasar
		// los 60 seg 60 min o 1000 ms
		int msTotales = tUltimoAnuncio%1000 + ms;
		int segTotales = seg + tUltimoAnuncio/1000;
		int minTotales = min+segTotales/60;
		
		// Calculamos las horas los minutos, segundos y ms exactos 
		int horaFinal = minTotales/60 + hora;
		int segFinal = segTotales%60 + msTotales/1000;
		int msFinal = msTotales%1000;
		int minFinal = min + segTotales/60;
		
		return (horaFinal +":"+minFinal+":"+ segFinal+":"+msFinal);
	}
	
	public synchronized void setT(long tiempo){
		t = tiempo;
	}
	public synchronized long getT(){
		return t;
	}
	/**
	 * El metodo recibe un objeto NodoNegociacion que gestiona la informacion asociada
	 * a un cliente. Si tiene hueco en la cola establece un precio de imagen y devuelve la 
	 * propuesta de hora de publicacion de la imagen.
	 * @param nodo
	 * @return
	 */
	public synchronized String negociacion (NodoNegociacion nodo){
		String salida;
		// Muestro error si no hay hueco en la cola
		if ((pagadosYnoEnCola + cola.elementosEnCola()) == maximo){
			salida = "ERROR: Demasiadas peticiones en cola.";
		}
		else{
			// Establezco el precio en euros
			int precio = nodo.getTiempo()*10 + nodo.getPesoImagen()/1000; 
			nodo.setPrecio(precio);
			
			// Obtenemos la hora actual
			Calendar cal1 = Calendar.getInstance();
			int hora = cal1.get(Calendar.HOUR);
			int minutos = cal1.get(Calendar.MINUTE);
			int seg = cal1.get(Calendar.SECOND);
			int ms = cal1.get(Calendar.MILLISECOND);
			
			// Mostramos la hora de publicacion
			salida = "PROPUESTA DEL SERVIDOR: Hora aproximada de visualizacion(hora:min:seg:ms): "+
					obtenerHora(hora,seg,minutos,cola.tiempoEnSalirUltimoElemento()*1000,ms)
					+" Precio: "+precio;
			texto  = texto +("HORA APROXIMADA (H:M:S:MS) : "+obtenerHora(hora,seg,minutos,cola.tiempoEnSalirUltimoElemento()*1000,(int)(ms+ t)) + "\n\n");
			
		}
		return salida;
	}
	
	/**
	 * El metodo aumento el numero de pagados y no con la imagen no encolada en uno si
	 * la cola no esta llena, devuelve true si se realiza correctamente. false si esta
	 * llena la cola
	 * @return
	 */
	public synchronized boolean pago (){
		if ((pagadosYnoEnCola + cola.elementosEnCola()) < maximo){
			pagadosYnoEnCola++;
			return true;
		}
		else return false;
	}
	
	/**
	 * El metodo recibe un objeto NodoNegociacion que gestiona la informacion
	 * asociada al cliente. Encola el objeto recibido aumenta en uno el numero
	 * de imagenes por publicar y disminuye en uno el numero de clientes que han
	 * pagado y no han encolado, despierta el resto de procesos. Devuelve un String
	 * que informa de que se ha realizado correctamente.
	 * @param nodo
	 * @return
	 */
	public synchronized String encolar(NodoNegociacion nodo){
		
		// Gestionamos los clientes
		cola.añadir(nodo);
		porPublicar ++;
		pagadosYnoEnCola--;
		notifyAll();
		
		String salida = "Anuncio correctamente programado";
		System.out.println("Imagen correctamente encolada");
		return salida;
	}
	
	/**
	 * El metodo duerme el proceso en caso de que no haya nada por publicar,
	 * en caso de que queden imagenes por publicar decrementa en uno este numero
	 * y desencola un objeto NodoNegociacion que devolvera el metodo.
	 * @return
	 */
	public synchronized NodoNegociacion siguienteEnCola(){
		
		// Duermo el proceso mientras no haya nada que publicar
		while (porPublicar == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		porPublicar --;
		return cola.sacar();
		
	}
	
	/**
	 * El metodo duerme el proceso mientras no haya ningun hueco en la 
	 * ventana de las imagenes, en caso de que haya decrementa en uno
	 * el numero de huecos libres.
	 */
	public synchronized void esperarHuecoEnPantalla (){
		while (huecosEnPantalla == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		huecosEnPantalla--;
	}
	
	/**
	 * El metodo aumenta en uno el numero de huecos libres en la ventana de
	 * imagenes y despierta a los procesos dormidos.
	 */
	public synchronized void dejoHuecoLibreEnPantalla(){
		huecosEnPantalla++;
		notifyAll();
	}
	
	/**
	 * El metodo aumenta en uno el numero de clientes que se encuentran negociando
	 */
	public synchronized  void nuevoCliente(){
		nClientesNeg++;
	}
	
	/**
	 * El metodo decrementa en uno el numero de clientes que se encuentran negociando
	 */
	public synchronized  void finCliente(){
		nClientesNeg--;
	}
	
	/**
	 * Devuelve un booleano que nos dice si ha de cerrarse la ventana de datos o no
	 * @return
	 */
	public synchronized boolean getCerrarDatos(){
		return cerrarDatos;
	}
	
	/**
	 * El metodo asigna al atributo cerrarDatos true
	 */
	public synchronized void cerrarVentanaDatos(){
		cerrarDatos = true;
	}
	
	/**
	 * Devuelve un entero correspondiente al numero de clientes que se encuentran negociando
	 * @return
	 */
	public synchronized  int getnClientes(){
		return nClientesNeg;
	}
	
	/**
	 * Devuelve un entero correspondiente al numero de elementos encolados
	 * @return
	 */
	public synchronized  int getElemCola(){
		return porPublicar;
	}
	
	/**
	 * Devuelve un booleano que nos dice si el administrador ha decidido apagar el servidor
	 * o no
	 * @return
	 */
	public synchronized boolean getApagar(){
		return apagar;
	}
	
	/**
	 * Asigna al atributo apagar el boolean recibido por el metodo
	 * @param apagar
	 */
	public synchronized void setApagar(boolean apagar){
		this.apagar = apagar;
	}
	
	/**
	 * Devuelve un String correspondiente al texto que se ha de mostrar en la ventana
	 * de datos
	 * @return
	 */
	public synchronized String getTexto() {
		return texto;
	}

	public synchronized void todoAcabado (){
		
		// Mientras haya algun hueco en la ventana de imagenes ocupado informamos por pantalla
		// de los huecos restantes, de los clientes que han pagado y no estan sus imagenes encoladas
		// y de los elementos encolados. Despues dormimos el Thread.
		while ((huecosEnPantalla != 4) || 
				(nClientesNeg + cola.elementosEnCola() != 0)){
			System.out.printf("Huecos en pantalla = %d, pagados y no en cola = %d Elementos en cola = %d"
					+ ", %n", huecosEnPantalla, nClientesNeg, cola.elementosEnCola());
			try {
				System.out.println("Espero para apagarme");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

