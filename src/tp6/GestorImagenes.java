package tp6;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Inicializa las ventanas a usar (Imagenes,Datos,Boton) establece su tamaño y 
 * las situa en la pantalla. Inicializa la ejecucion de los Thread que gestionan
 * la ventana de Datos ,el Boton y cada imagen, por ultimo cuenta con metodos
 * auxiliares para insertar imagenes en una ventana o borrarlas y otro metodo
 * capaz de cerrar las ventanas empleadas.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class GestorImagenes {

	final int NUM_IM_FILS = 2; 		// Ventana con 2x2 imágenes
	final int NUM_IM_COLS = 2;
	final int PIX_SEP = 5;    		// Píxeles de separación entre imágenes
	final int NUMFILAS = 400; 		// Longitud imagenes
	
	// Objetos necesarios para la inicializacion , refresco y modificacion de 
	// las tres ventanas gestionadas
	private  ImageIcon[] lasImagenes;
	private  JLabel[] labelImg, labelDatos;
	private  Container panelImg, panelDatos, panelBoton;
	private  JFrame frameImg, frameDatos, frameBoton;

	private boolean [] huecos; 		// Huecos libres en la ventana de imagenes
	private Monitor monitor; 		// Monitor usado en la gestion de la informacion
	
	/**
	 * Constructor del objeto GestorImagenes. Inicializa los atributos de la
	 * clase.
	 * @param monitor
	 */
	public GestorImagenes(Monitor monitor){
		// Inicializacion de atributos
		huecos = new boolean[NUM_IM_FILS * NUM_IM_COLS];
		for (int i = 0; i<huecos.length; i++) huecos[i] = true;
		this.monitor = monitor;
		
		
		//Inicializacion de las Ventanas
		iniciarPanel();
		System.out.println("Panel iniciado");
		
		
	}
	
	/**
	 * El metodo inicializa las ventanas usadas, ajusta su tamaño,
	 * las posiciona en la pantalla y comienza la ejecucion de los
	 * Thread correspondientes al objeto GestorDatos y al objeto Boton
	 */
	private void iniciarPanel(){
		long t = System.currentTimeMillis();
		lasImagenes = new ImageIcon[NUM_IM_FILS * NUM_IM_COLS];
		
		// Frames para colocar las imágenes
		frameImg = new JFrame("Imagenes");  
		frameDatos = new JFrame("Datos");
		frameBoton = new JFrame("Boton");
		
		// Para acceder al panel del frame
		panelImg = frameImg.getContentPane();   
		panelDatos = frameDatos.getContentPane();
		panelBoton = frameBoton.getContentPane();
		
		labelImg = new JLabel[NUM_IM_FILS * NUM_IM_COLS]; // un JLabel por imagen
		labelDatos = new JLabel[2*2];
	
		// Poner propiedades fundamentales del "frame"
		frameImg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameDatos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBoton.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// -----------------------------------------------------------------------------
		// Preparar infraestructura para colocar las imágenes
		panelImg.setLayout(new GridLayout(NUM_IM_FILS, NUM_IM_COLS, PIX_SEP,
				PIX_SEP));
		panelDatos.setLayout(new GridLayout(NUM_IM_FILS, NUM_IM_COLS, PIX_SEP,
				PIX_SEP));
		panelBoton.setLayout(new GridLayout(1, 1, PIX_SEP,
				PIX_SEP));
		
		// Ajustamos tamaño de los Frames
		frameImg.setSize(35 + NUM_IM_COLS * NUMFILAS, 55 + NUM_IM_FILS * NUMFILAS);
		frameDatos.setSize(200 + NUM_IM_COLS * NUMFILAS/2, 55 + NUM_IM_FILS * NUMFILAS/2);
		frameBoton.setSize(NUMFILAS/2,NUMFILAS/2);
		
		// Posicionamos los frames en pantalla
		frameImg.setLocation(0,0);
		frameDatos.setLocation(840, 0);
		frameBoton.setLocation(1040, 600);
		
		// Modificamos los permisos de modificacion del tamaño
		// de los frames, no permitiendo que esto suceda
		frameBoton.setResizable(false);
		frameDatos.setResizable(false);
		frameImg.setResizable(false);
		
		// Creamos los nuevos objetos Boton y GestorDatos, los asociamos correspondientemente
		// a dos Thread y comenzamos su ejecucion. 
		Thread Boton = new Thread (new Boton (monitor, panelBoton,frameBoton));
		Thread GD = new Thread(new GestorDatos(monitor,labelDatos,panelDatos,frameDatos));
		GD.start();
		Boton.start();
		
		// Tiempo de inicializacion de las ventanas
		long tfinal = System.currentTimeMillis()-t;
		monitor.setT(tfinal);
	}
	
	/**
	 * El metodo recibe un vector de Bytes correspondiente a la imagen y un entero i,
	 * correspondiente a la posicion dentro de los diferentes Array. El metodo inicializa el 
	 * imageIcon que le corresponde a la imagen, comprueba que se ha cargado correctamente, 
	 * asocia el label ha dicho imageIcon lo añade y refresca el panel y por ultimo lo hace
	 * visible.
	 * @param imageData
	 * @param i
	 */
	public void insertarImagen(byte[] imageData, int i){
		lasImagenes[i]= new ImageIcon(imageData);
		
		// Comprobamos que la imagen se haya cargado correctamente, en caso afirmativo
		// ajustamos la imagen al panel, en caso de que no se haya cargado informamos
		// de dicho suceso
		if (lasImagenes[i].getImageLoadStatus() != MediaTracker.COMPLETE) { //¿Imagen cargada correctamente?
           	labelImg[i] = new JLabel();
           	labelImg[i].setText("Imagen no encontrada"); //si no encuentra la imagen, añadir texto
       	} else {
       		lasImagenes[i] = new ImageIcon( // -1: aplicará en columnas mismo factor que en filas
       					lasImagenes[i].getImage().getScaledInstance(NUMFILAS, -1, Image.SCALE_SMOOTH)
       		);
       	}
       	
		// Asociamos el JLabel al imagenIcon y lo añadimos al panel
		labelImg[i] = new JLabel(lasImagenes[i]);
       	panelImg.add(labelImg[i]);
       	
       	// Refresco del panel
       	panelImg.repaint();
		frameImg.setVisible(true);
       	
	}
	
	/**
	 * El metodo recibe un entero i correspondiente a la posicion de la imagen
	 * a eliminar dentro de el Array de Jlabel. Comprueba que la imagen no este
	 * ya borrada, en este caso elimina la imagen del panel y lo refresca, en caso
	 * contrario no realiza ninguna modificacion.
	 * @param i
	 */
	public void eliminarImagen(int i){
		// Comprueba que la imagen no este ya borrada
		if(labelImg[i]!= null){
			panelImg.remove(labelImg[i]);
			labelImg[i]=null;
	
		}
		//Refresco del panel
		panelImg.repaint();
		frameImg.setVisible(true);	
	}

	/**
	 * El metodo finaliza la ejecucion de la ventana de datos y posteriormente
	 * vuelve las tres ventanas que estamos usando no visibles.
	 */
	public void cerrarVentanas(){
		// Finaliza la ejecucion de GestorDatos
		monitor.cerrarVentanaDatos();
		// Cerramos las tres ventanas abiertas
		frameImg.setVisible(false);
		frameDatos.setVisible(false);
		frameBoton.setVisible(false);
	}
	
	/**
	 * El metodo recibe un entero i correspondiente a un hueco dentro de la ventana
	 * de imagenes. Deja libre dicho hueco (cambia una posicion de un Array de boolean
	 * a true)
	 * @param i
	 */
	public void dejoHueco(int i){
		huecos[i] = true;
	}
	
	/**
	 * El metodo espera a que haya hueco en la ventana, en caso de que encuentre un hueco
	 * informa por pantalla, mira las cuatro posiciones dentro de la ventana buscando la 
	 * que esta libre. Una vez la encuentra la coloca a ocupada y devuelve un entero que 
	 * se corresponde con esta posicion.
	 * @return
	 */
	public int publicarImagen() {	
		// Esperamos a que haya un hueco en la ventana
		monitor.esperarHuecoEnPantalla();
		System.out.println("Hay hueco en la ventana");
		
		// Buscamos la posicion de dicho hueco, lo modificamos para que este ocupado
		// y devolvemos un entero correspondiente a la posicion que estaba libre.
		int num = -1;
		for (int i=0; i< huecos.length; i++){
			if (huecos[i]){
				num = i;
				huecos[i] = false;
				i = huecos.length;
			}
		}
		return num;
		
	}

}
