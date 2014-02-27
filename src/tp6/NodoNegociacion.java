/**
* @author Pablo Lanaspa y Manuel Lagunas
* @version
* @date 12/01/2014
*/
package tp6;

/**
 * Esta clase aloja metodos que se encargan de gestionar la diferente informacion
 * necesaria en cada conexion de un Cliente al Servidor, que se utilizaran
 * posteriormente para poder publicar la imagen correctamente.
 * @author Manuel Lagunas y Pablo Lanaspa
 *
 */
public class NodoNegociacion {
	
	private int tiempo; 			// Tiempo a mostrar la imagen
	private int precio; 			// Precio establecido por mostrarla
	private int pesoImagen; 		// Peso de la imagen
	private long visa; 				// Visa del cliente
	private byte[] imagen;			// Array de Bytes correspondientes a la imagen a mostrar
	private boolean visaCorrecta;	// Muestra si es correcta la visa o no
	
	/**
	 * Devuelve un array de bytes correspondientes a la imagen a mostrar
	 * @return
	 */
	public byte[] getImagen() {
		return imagen;
	}
	
	/**
	 * Asigna al atributo imagen el parametro pasado al metodo
	 * @param imagen
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * Devuelve un entero correspondiente al numero de visa del cliente
	 * @return
	 */
	public long getVisa() {
		return visa;
	}
	
	/**
	 * Asigna al atributo visa el paramatro pasado al metodo y establece
	 * el boolean visaCorrecta true o false si el paramtro cumple con la
	 * especificacion de una visa
	 * @param visa
	 */
	public void setVisa(long visa) {
		this.visa = visa;
		visaCorrecta = esVisaCorrecta (visa);
	}
	
	/**
	 * Devuelve un entero correspondiente al peso de la imagen
	 * @return
	 */
	public int getPesoImagen() {
		return pesoImagen;
	}
	
	/**
	 * Asigna al atributo pesoImagen el parametro que recibe el metodo
	 * @param pesoImagen
	 */
	public void setPesoImagen(int pesoImagen) {
		this.pesoImagen = pesoImagen;
	}
	
	/**
	 * Devuelve un entero correspondiente al tiempo a mostrar la imagen 
	 * @return
	 */
	public int getTiempo() {
		return tiempo;
	}
	
	/**
	 * Asigna al atributo tiempo el parametro recibido por el metodo
	 * @param tiempo
	 */
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	
	/**
	 * Devuelve un entero correspondiente al precio de publicar la imagen
	 * @return
	 */
	public int getPrecio() {
		return precio;
	}
	
	/**
	 * Asigna al atributo precio el parametro recibido por el metodo
	 * @param precio
	 */
	
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	/**
	 * El metodo recibe un long correspondiente a una visa. Comprueba
	 * que dicho long se corresponda con una visa.
	 * @param numero
	 * @return
	 */
	private boolean esVisaCorrecta (long numero){
		int digitos = 0;
		while (numero != 0){
			numero= numero/10;
			digitos++;
		}
		return digitos == 16;
	}
	
	/**
	 * Devuelve un boolean que nos muestra si es correcta o si no lo es
	 * la visa introducida
	 * @return
	 */
	public boolean isVisaCorrecta() {
		return visaCorrecta;
	}	

}

