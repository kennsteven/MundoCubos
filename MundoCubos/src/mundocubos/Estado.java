/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mundocubos;

/**
 *
 * @author Kenneth Alvarado
 */
public class Estado {
    
    private String estado;
    
    public Estado(String cubo1, String cubo2, String relacion){
        String est = generarEstado(cubo1, cubo2, relacion);
        setEStado(est);
    }
    
    public Estado(){};
    
    
    //Contructor por copia
    public Estado copia(){
        Estado copia = new Estado();
        copia.setEStado(this.getEstado());
        return copia;
    }
    
    private String generarEstado(String cubo1, String cubo2, String relacion){
        String s = relacion + "(" + cubo1 + "," + cubo2 + ")";
        return s;
    }
    
    public void setEStado(String s){
        this.estado = s;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public String getCuboEncima(){
        String s = "" + estado.charAt(2);
        return s;
    }
    
    public boolean iguales(Estado estado1, Estado estado2){
        boolean respuesta = false;
        if(estado1.getEstado().equals(estado2.getEstado())){
            respuesta = true;
        }
        return respuesta;
    }
    
    public static void main(String args[]){
        Estado e = new Estado("A", "B", "S");
        Estado e2 = new Estado("A", "C", "S");
        System.out.println(e.getEstado());
        System.out.println(e.iguales(e,e2));
        System.out.println(e.getCuboEncima());
        
    }
}
