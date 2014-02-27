/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Lee y almacena todos ficheros de imagenes contenidos en un directorio, transforma dicho
 * fichero a un vector de Bytes y asocia cada imagen a un Cliente (pudiendo haber clientes
 * con la misma imagen). Una vez asociados inicia la ejecucion de dichos Clientes, una vez
 * que todos finalizan muestra un mensaje por pantalla.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Simulador {

	
	/*
	 * CONSTANTES Y VARIABLES QUE DEFINEN EL COMPORTAMIENTO DE LA SIMULACIÓN
	 */
	
	final static int NUM_CLIENTS = 100; //Número de clientes para la simulación
	final static int NUM_IMAGENES = 10; //Número máximo de imágenes en la cola
	final static String NOMBREDIRECTORIO = "src/tp6/images";
	final static boolean SIMULACION_ALEATORIA = true; //Realizar una simulación de acuerdo al modelo estadístico de Poisson
	final static double LAMBDA = 10; //Número de conexiones por segundo que se quiere tener de media
	final static int TIMEOUT = 10000; //Tiempo en ms necesario para que una conexión espire si no hay respuesta en los correspondientes read
	
	/**
	 * El metodo recibe un objeto [File] y lo transforma a un
	 * array de Bytes.
	 * @param fichero
	 * @return
	 */
	private static byte[] ficheroaByte(File fichero){
		try {
			// Inicializamos los atributos
			FileInputStream lectura = new FileInputStream(fichero);
			ByteArrayOutputStream img = new ByteArrayOutputStream();
			byte[] buffer = new byte[(int) fichero.length()];
			
			// Escribimos los bytes de [fichero]
			for(int numLeido; (numLeido = lectura.read(buffer)) != -1;){
				img.write(buffer, 0, numLeido);
			}
			
			// Transformamos ByteArrayOutputStream a un array de bytes
			byte[] imagen = img.toByteArray();
			lectura.close();img.close();
			
			return imagen;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * El metodo recibe un Array de Threads correspondientes a los clientes que conectaran
	 * con el servidor. Lanza los Thread segun el modelo de Poisson, que es el modelo usado
	 * para realizar simulaciones veridicas en las conexiones Cliente-Servidor
	 * @param clientes
	 */
	private static void simulacionAleatoria(Thread [] clientes){
		Random generador = new Random();
	
		for (int i=0; i<clientes.length; i++) {	
			//Calculamos tiempo aleatorio según distribución de Poisson para la creación de un nuevo cliente
			double tiempo = - (Math.log(1-generador.nextDouble()))/LAMBDA;
			try {
				Thread.sleep((long)(tiempo*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			clientes[i].start();
		}
		
		
	}
	
	
	/**
	 * Método main del ejercicio que permite la simulación a través de los parámetros
	 * declarados como constantes del mismo.
	 * @param args
	 */
	public static void main(String[] args){
		
		System.out.println("Servidor creado correctamente.");
		System.out.print("Importando las imágenes del directorio... ");
		
		// Creamos los objetos necesarios para obtener las imagenes de un directorio
		File directorio = new File(NOMBREDIRECTORIO);
		File[] ficheros = null;
		String extension = ".jpg";
		
		// Comprobamos que el nombre del directorio introducido es correcto
		if (directorio.isDirectory()) {
			ficheros = directorio.listFiles();
		}
		else {
			System.err.println("Nombre de directorio erroneo");
			System.exit(1);
		}
		
		// Contamos el numero de imagenes en el directorio, recorriendolo por completo y
		// comparando la extension
		int numImagenesEnDirectorio = 0;
		for(int i = 0; i < ficheros.length; i++){
			if (ficheros[i].isFile()
					&& ficheros[i].getAbsolutePath().endsWith(extension.toLowerCase())){
				numImagenesEnDirectorio++;
			}
		}
		
		// Creamos un Array de ficheros con el numero de imagenes calculado anteriormente
		// y guardamos cada fichero ".jpg" en dicho vector
		int j = 0;
		File[] pictures = new File[numImagenesEnDirectorio];
		for(int i = 0; i < ficheros.length; i++){
				if (ficheros[i].isFile()
						&& ficheros[i].getAbsolutePath().endsWith(extension.toLowerCase())){
					pictures[j]= ficheros[i];
					j++;
				}
				
		}
		
		
		System.out.println("Imágenes correctamente importadas.");
		System.out.print("Creando clientes... ");
		
		// Creamos los clientes...
		Thread [] clientes = new Thread [NUM_CLIENTS];
		
		// Cada fichero lo transformamos en un Array de bytes para evitar errores
		// a la hora del intercambio de informacion Cliente-Servidor.
		for (int i=0; i<clientes.length; i++) {
			clientes [i] = new Thread (new Cliente ((long)Math.pow(10, 15)+i,
					ficheroaByte(pictures[i%numImagenesEnDirectorio]), TIMEOUT));
			}
		
		System.out.println("Clientes creados correctamente.");
		System.out.println("Lanzamos los clientes para las compras.");
		
		//... Y los lanzamos
		if (SIMULACION_ALEATORIA) simulacionAleatoria(clientes);
		else for (int i=0; i<clientes.length; i++) clientes[i].start();
		
		// Cuando todos han terminado informamos de ello
		for (int i=0; i<clientes.length; i++){
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		System.out.println("Todos los clientes han finalizado sus compras, solo queda vivo el servidor.");
		System.out.println("--- FIN DE LA SIMULACIÓN ---");
		
		
	}
}
