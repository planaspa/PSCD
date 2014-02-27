/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import java.awt.Container;

/**
 * Se crean objetos JLabel y JTextArea para mostrar informacion en una ventana,
 * se añaden a la ventana de los Datos y continuamente se refrescan exceptuando
 * el objeto JTextArea que se refresca cada 2 segundos debido a su peso, sino la
 * ventana de Datos se ralentizaria. Cuando el administrador decida finaliza la
 * ejecucion del bucle.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class GestorDatos implements Runnable{

	private Monitor monitor; // monitor usado para la gestion de la informacion
	
	// Objetos necesarios para la inicializacion, refresco y modificacion de la ventana
	private JLabel[] labelDatos;
	private Container panelDatos;
	private JFrame frameDatos;
	
	/**
	 * Constructor del objeto GestorDatos, recibe un Monitor usado para gestionar
	 * informacion y un objeto JLabel, un objeto Container y un objeto JFrame que
	 * serviran para gestionar la ventana de Datos
	 * @param mon
	 * @param LD
	 * @param PD
	 * @param FD
	 */
	public GestorDatos(Monitor mon,JLabel[] LD, Container PD, JFrame FD){
		this.monitor = mon;
		this.labelDatos = LD;
		this.panelDatos = PD;
		this.frameDatos = FD;
		
	}
	
	@Override
	public void run() {
		
		// Inicializo variables
		Date fecha = new Date();
		for(int i = 0; i<labelDatos.length-1; i++){
			labelDatos[i] = new JLabel();
		}
		JTextArea texto = new JTextArea();
		
		// Establecemos el scrolling automatico
		DefaultCaret caret = (DefaultCaret)texto.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Asigno el texto correspondiente a los diferentes objetos que se van a mostrar
		//en la ventana de datos
		texto.setText(monitor.getTexto());
		labelDatos[0].setText("CLIENTES ATENDIENDO: "+ monitor.getnClientes());
		labelDatos[1].setText("HORA ACTUAL :"+fecha.toString()); 
		labelDatos[2].setText("SERVICIOS PENDIENTES: "+monitor.getElemCola());
		
		// Añadimos a la ventana los objetos JLabel y JTextArea
		panelDatos.add(texto,null);
		for(int i = 0; i<labelDatos.length-1; i++){
			panelDatos.add(labelDatos[i]);
		}
		
		// Añadimos al JTextArea la barra para realizar Scroll
		panelDatos.add(new JScrollPane (texto));
       	
		// Refrescamos la ventana de Datos
		panelDatos.repaint();
		frameDatos.setVisible(true);
		
		long time = System.currentTimeMillis();
	 	String aux = "";
	 	
		while(!monitor.getCerrarDatos()){
			
	 		// Actualizo la variable fecha
	 		fecha = new Date();
			
			//Asigno el texto correspondiente a los diferentes objetos que se van a mostrar
			//en la ventana de datos  
			labelDatos[0].setText("CLIENTES EN NEGOCIACION: "+ monitor.getnClientes());
			labelDatos[1].setText("HORA ACTUAL :"+fecha.toString()); 
			labelDatos[2].setText("SERVICIOS EN COLA: "+monitor.getElemCola());
			long time2 = System.currentTimeMillis();
			
			// Actualizamos cada dos segundos para evitar sobre carga en la ventana de Datos
			// y que su ejecucion se ralentice
			if (time2-time > 2000){ 
				time = time2;
				if(!aux.equals(monitor.getTexto())){
					texto.setText(monitor.getTexto());
					aux = monitor.getTexto();
				}
				
			}
		panelDatos.repaint();
	 	}
		
		System.out.println("Gestor de datos apagado");
	}

}
