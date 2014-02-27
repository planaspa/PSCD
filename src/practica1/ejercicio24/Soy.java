package practica1.ejercicio24;

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
	    * En este caso la ejecución del método run() consiste en dormir el thread durante 0.5 segundos 
	    * y a continuación imprimir el mensaje almacenado en el atributo nombre.	
	    */
	    public void run(){
	    		
	    	for (int i=0; i<veces;i++){
	            //Dormimos la ejecución durante 0.5 segundos
	            try{
	               	Thread.sleep(500);
	               	System.out.println("Soy " + nombre);
	            } 
	            catch(InterruptedException e){
	                ;
	            }
	    		
	    	}
	    }
}
