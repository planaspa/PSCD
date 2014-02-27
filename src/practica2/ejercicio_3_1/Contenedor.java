/**
* @author Pablo Lanaspa
* @version
* @date 21/10/2013
*/
package practica2.ejercicio_3_1;
/**
 * 
 * @author Pablo
 *
 *	La clase Contenedor implementa una abstracción del contenedor del problema, que
 *	es objeto del proceso vaciado. Es, por tanto, el recurso compartido por los brazos
 *	robotizados.
 */
public class Contenedor implements Contenedores {

	/*
	 * Atributos de la clase
	 */
	String id;					//Identificador único del contenedor
	int num_piezas_restantes;	//Número de piezas que contiene actualmente el contenedor
	boolean [] solicita_entrar;	//Vector de 2 booleanos que indica que brazo ha solicitado entrar
	int ultimo;					//Indica que brazo ha sido el ultimo en querer entrar
	
	/**
	 * El constructor asigna:
	 * @param identificador --> identificador único del contenedor
	 * @param piezas		--> número de piezas que contiene inicialmente;
	 */
	public Contenedor (String identificador, int total_piezas){
		id = identificador;
		num_piezas_restantes = total_piezas;
		solicita_entrar = new boolean [2];
		ultimo = 0;
	}
	
	/**
	 * Este método decrementa en una unidad el número de piezas existentes en el 
	 * contenedor, y representa una abstracción de la operación física por la cual 
	 * un brazo robotizado saca una pieza del contenedor.
	 */
	public void descargarUnaPieza() {
		num_piezas_restantes --;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void soyElUltimo (int id){ultimo = id;}
	
	/**
	 * 
	 * @return
	 */
	public int quienEsElUltimo () {return ultimo;}
	
	/**
	 * 
	 * @param id
	 * @param entrar
	 */
	public void quieroEntrar (int id, boolean entrar){solicita_entrar[id]=entrar;}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean haSolicitadoEntrar (int id) {return solicita_entrar[id];}
	

}
