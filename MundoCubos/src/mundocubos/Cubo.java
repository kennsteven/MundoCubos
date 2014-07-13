/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mundocubos;

/**
 *
 * @author Kenneth Alvarado
 */
public class Cubo {
    String nombre;
    
    public Cubo(String nombre){
        setNombre(nombre);
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
}
