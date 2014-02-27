/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Inicializa la ventana donde se aloja el Boton encargado de cerrar el servidor,
 * en caso de que este se pulse comienza a cerrarlo e informa de ello
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class Boton implements Runnable {
	 
	// Atributos encargados de la gestion de la ventana del boton
	private Container panelBoton;
	private JFrame frameBoton;
	private JButton boton;
	
	private Monitor monitor;// Atributo encargado de la gestion de la informacion
	
	/**
	 * Constructor del objeto Boton, recibe un monitor para gestionar la informacion, un
	 * Container y JFrame que gestionaran la ventana donde se aloja el boton
	 * @param monitor
	 * @param PD
	 * @param FD
	 */
	public Boton(Monitor monitor, Container PD, JFrame FD){
		// Inicializacion de los atributos
		this.monitor = monitor;
		this.panelBoton = PD;
		this.frameBoton = FD;
		
		boton = new JButton ("Apagar Servidor");
	}
	
	@Override
	public void run() {
		
		// Añadimos un nuevo Action Listener para reconocer la pulsacion del boton
		boton.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Comenzamos el cierre del servidor e informamos
				System.out.println("CERRANDO SERVIDOR..");
				monitor.setApagar(true);
				panelBoton.remove(boton);
				JLabel labelBoton = new JLabel();
				labelBoton.setText("CERRANDO SERVIDOR..");
				panelBoton.add(labelBoton);
				
				// Actualizamos la ventana del boton
				frameBoton.repaint();
				frameBoton.setVisible(true);
				
			}
		});
		// Actualizamos la ventana del boton
		panelBoton.add(boton);
		frameBoton.setVisible(true);
		
		
	}
}
