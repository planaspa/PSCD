/**
* @author R. Trillo
* @version
* @date 11/09/2011
*/


package practica1.ejercicio3;

class SaludoRunnable implements Runnable{

   /**
    * El atributo saludo almacena la referencia a un objeto de tipo String (cadena de texto). 
    * 
    */	
    private String saludo;
    
   /**
    * El atributo retardo almacena un valor de tipo entero (int). 
    * 
    */	
    private int retardo;


   /** 
    * Define el constructor de objetos SaludoThread especific�ndole el valor de los atributos saludo y retardo.
    * 
    * @param String s indica el valor que se desea asignar al atributo saludo
    * @param int r indica el valor que se desea asignar al atributo retardo
    * 
    */
    public SaludoRunnable(String s, int r){

        this.saludo = s;
        this.retardo = r;
    }


   /** 
   * Devuelve void. 
   * Cuando se llama al m�todo run de un thread se realiza su ejecuci�n. Salvando las diferencias,
   * el m�todo run() es similar al m�todo main() pero para clases que descienden de la clase Thread.
   * Cuando finaliza la ejecuci�n del m�todo run() el thread muere.	
   * En este caso la ejecuci�n del m�todo run() consiste en dormir el thread durante el tiempo especificado 
   * por el atributo retardo y a continuaci�n imprimir el mensaje almacenado en el atributo saludo.	
   */
   public void run(){
        //Dormimos la ejecuci�n durante el tiempo especificado
        try{
           	Thread.sleep(retardo);
        } catch(InterruptedException e){
            ;
        }

        //Ahora imprimimos el saludo
        System.out.println( "Saludo de la siguiente forma: " 
            +  this.saludo + " despu�s de haber dormido" +
             this.retardo);
    }
}

