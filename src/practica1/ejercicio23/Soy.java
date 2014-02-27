package practica1.ejercicio23;

public class Soy implements Runnable{

	private int veces;
	
	private String nombre;
	
	public Soy (String s, int n){
		this.veces = n;
		this.nombre = s;
	}
	
	/** 
	    * Devuelve void. 
	    * Cuando se llama al método run de un thread se realiza su ejecución. Salvando las diferencias,
	    * el método run() es similar al método main() pero para clases que descienden de la clase Thread.
	    * Cuando finaliza la ejecución del método run() el thread muere.	
	    * En este caso la ejecución del método run() consiste en imprimir el mensaje almacenado en el atributo nombre.	
	    */
	    public void run(){
	    		
	    	for (int i=0; i<veces;i++){
	    		System.out.println("Soy "+nombre);
	    	}
	    }
}
