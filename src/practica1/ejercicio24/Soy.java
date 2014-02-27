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
	    * Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
	    * el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
	    * Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
	    * En este caso la ejecuci�n del m�todo run() consiste en dormir el thread durante 0.5 segundos 
	    * y a continuaci�n imprimir el mensaje almacenado en el atributo nombre.	
	    */
	    public void run(){
	    		
	    	for (int i=0; i<veces;i++){
	            //Dormimos la ejecuci�n durante 0.5 segundos
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
