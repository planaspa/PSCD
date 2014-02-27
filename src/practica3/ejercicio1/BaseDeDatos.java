
/*
* Programación de Sistemas Concurrentes y Distribuidos
* Curso 2013-2014. Grado de Informática
*
* @author PSCD
*
* Descripción: La clase BaseDeDatos implementa una base de
* Datos basada en estructuras Hash para almacenar información
* de personas conforme la estructura definida en el
* enunciado de la práctica 3.
*/

package practica3.ejercicio1;

import java.util.Hashtable;

class BaseDeDatos{
    private Hashtable tablaDni;
    private Hashtable tablaNombre;
    private Hashtable tablaDireccion;
    private Hashtable tablaApellidos;

    //Constructor
    public BaseDeDatos(){
        this.tablaNombre = new Hashtable();
        this.tablaApellidos = new Hashtable();
        this.tablaDni = new Hashtable();
        this.tablaDireccion = new Hashtable();
    }

    //Constructor
    public BaseDeDatos(Hashtable nombres, Hashtable apellidos, Hashtable dnis, Hashtable direcciones){
        this.tablaNombre = nombres;
        this.tablaApellidos = apellidos;
        this.tablaDni = dnis;
        this.tablaDireccion = direcciones;
    }

    public  Hashtable getTablaNombre(){
        return this.tablaNombre;
    }

    public  Hashtable getTablaApellidos(){
        return this.tablaApellidos;
    }

    public  Hashtable getTablaDni(){
        return this.tablaDni;
    }

    public  Hashtable getTablaDirecciones(){
        return this.tablaDireccion;
    }

    public String getNombre(int clave){
        return (String) this.tablaNombre.get(clave);
    }

    public String getApellidos(int clave){
        return (String)  this.tablaApellidos.get(clave);
    }

    public String getDni(int clave){
        return (String)  this.tablaDni.get(clave);
    }

    public String getDireccion(int clave){
        return (String) this.tablaDireccion.get(clave);
    }

    public void updateNombre(int clave, String nombre){
        this.tablaNombre.remove(clave);
        this.tablaNombre.put(clave, nombre);
    }

    public void updateApellidos(int clave, String apellidos){
        this.tablaApellidos.remove(clave);
        this.tablaApellidos.put(clave, apellidos);
    }

    public void updateDireccion(int clave, String direccion){
        this.tablaDireccion.remove(clave);
        this.tablaDireccion.put(clave, direccion);
    }

    public void insertarRegistro(int clave, String nombre, String apellidos, String dni, String direccion){
        this.tablaNombre.put(clave, nombre);
        this.tablaApellidos.put(clave, apellidos);
        this.tablaDni.put(clave, dni);
        this.tablaDireccion.put(clave, direccion);
    }
}
