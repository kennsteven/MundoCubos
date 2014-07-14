/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mundocubos;

import java.util.ArrayList;



/**
 *
 * @author Kenneth Alvarado
 */
public class EstadoDelMundo {
    private ArrayList<Estado> listaEstados;
    
    public EstadoDelMundo(){
        listaEstados = new ArrayList<Estado>();
    }
    
    /*Constructor por copia*/
    public EstadoDelMundo copia(){
        EstadoDelMundo copia = new EstadoDelMundo();
        for(int i= 0; i < this.getLista().size();i++){
            Estado c = this.getLista().get(i).copia();
            copia.listaEstados.add(c);
        }
        
       return copia;
    }
        
    public void setLista(ArrayList<Estado> l){
        this.listaEstados = l;
    }
    
    public ArrayList<Estado> getLista(){
        return this.listaEstados;
    }
    
    public void agregarEstado(Estado es){
        this.listaEstados.add(es);
    }
    
    public String imprimirLista(){
        String s = "";
        s += " { ";
        System.out.print("El estado es: { ");
        for(int i = 0; i < listaEstados.size(); i++){ 
            System.out.print(" " + listaEstados.get(i).getEstado());
            s += " " + listaEstados.get(i).getEstado();
            if(i != listaEstados.size() - 1){
                System.out.print(",");
                s+=",";
            }
        }
        System.out.print(" } ");
        s += " } ";
        return s;
    }
    
    //OPERACIONES
    public void eliminarElemento(Estado e){
        //listaEstados.remove(e);
        for(int i = 0; i < this.getLista().size(); i++){
            if(this.getLista().get(i).getEstado().equals(e.getEstado())){
                this.getLista().remove(i);
            }
        }
    }
    
    public void ordernar(){
        
    }
    
    public ArrayList<String> cubosSinNadaEncima(){
        ArrayList<String> l = new ArrayList<String>();
        for(int i = 0; i < this.getLista().size(); i++){
            String s = this.getLista().get(i).getCuboEncima();
         
            if(this.nadaEncima(s) == false){
                l.add(s);
            }
        }
        return l;
    }
    
    //Me dice si un estado del mundo esta contenido en una 
    // lista de estados del mundo
    public boolean estadoDelmundoContenido(ArrayList<EstadoDelMundo> lista, EstadoDelMundo estados){
        boolean respuesta = false;
        
        for(int i = 0; i < lista.size(); i++){
            if(this.iguales(lista.get(i), estados)==true){
                respuesta = true;
                break;
            }
        }
        
        return respuesta;
    }

    public boolean iguales(EstadoDelMundo ed1, EstadoDelMundo ed2 ){
        ArrayList<Estado> lista1 = ed1.getLista();
        ArrayList<Estado> lista2 = ed2.getLista();
        boolean respuesta = true;
        
        if(lista1.size() != lista2.size()){
            respuesta = false;
        }else{
            for(int i = 0; i< lista1.size();i++){
                Estado e1 = lista1.get(i);
                for(int j = 0; j < lista2.size(); j++){
                    Estado e2 = lista2.get(j);
                    if(e1.iguales(e1, e2) == true){
                        break;
                    }

                    if(j == lista2.size() - 1){
                        respuesta = false;
                        break;
                    }
                }
            }
        }
        return  respuesta;
    }
    
    
    public int igualesInt(EstadoDelMundo ed1, EstadoDelMundo ed2 ){
        ArrayList<Estado> lista1 = ed1.getLista();
        ArrayList<Estado> lista2 = ed2.getLista();
        boolean respuesta = true;
        int ret = -1;
        
        if(lista1.size() != lista2.size()){
            respuesta = false;
        }else{
            for(int i = 0; i< lista1.size();i++){
                Estado e1 = lista1.get(i);
                for(int j = 0; j < lista2.size(); j++){
                    Estado e2 = lista2.get(j);
                    if(e1.iguales(e1, e2) == true){
                        break;
                    }

                    if(j == lista2.size() - 1){
                        respuesta = false;
                        ret = i;
                        break;
                    }
                }
            }
        }
        return  ret;
    }
    
    //RELACIONES
    
    //Devuelve tru si hay algo encima y flase si no hay nada
    public boolean nadaEncima(String s){
        boolean respuesta = false;
        
        for(int i = 0; i < listaEstados.size(); i++){ 
            String estado = listaEstados.get(i).getEstado();
            
            if(estado.charAt(4)== s.charAt(0)){
                //System.out.println("Si hay algo encima de " + s);
                respuesta = true;
                break;
            }
        }
        
        return respuesta;
    }
    
    public boolean sobreLaMesa(String s){
        boolean respuesta = false;
        
        for(int i = 0; i < listaEstados.size(); i++){ 
            String estado = listaEstados.get(i).getEstado();
            
            if(estado.charAt(2)== s.charAt(0) && estado.charAt(4)== 'M'){
                //System.out.println("Si " + s + " esta sobre la mesa");
                respuesta = true;
                break;
            }
        }
        
        return respuesta;
    }
    
    public static void main(String args[]){
        Estado e = new Estado("A", "B", "S");
        System.out.println(e.getEstado());
        
        Estado e2 = new Estado("B", "M", "S");
        System.out.println(e.getEstado());
        
        Estado e3 = new Estado("A", "M", "S");
        System.out.println(e.getEstado());
        
        /*EstadoDelMundo ed = new EstadoDelMundo();
        ed.agregarEstado(e);
        ed.agregarEstado(e2);
        
        EstadoDelMundo ed2 = new EstadoDelMundo();
        ed2.agregarEstado(e2);
        ed2.agregarEstado(e);
        ed2.agregarEstado(e3);
        
        EstadoDelMundo ed3 = new EstadoDelMundo();
        ed2.agregarEstado(e2);
        ed2.agregarEstado(e3);
        
        System.out.println("\ned1:");
        ed.imprimirLista();
        
        System.out.println("nada encima" + ed.nadaEncima("B"));
        System.out.println("sobre la mesa" + ed.sobreLaMesa("B"));
        
        EstadoDelMundo ed4 = ed2.copia();
        System.out.println("\ned4:");
        ed4.imprimirLista();
        
        ed.imprimirLista();
        
        System.out.println("\ned2:");
        ed2.imprimirLista();
        System.out.println(ed.iguales(ed,ed2));
        
        System.out.println("\ned2:");
        ed.eliminarElemento(e3);
        ed2.imprimirLista();
        
        ArrayList<EstadoDelMundo> li = new ArrayList<EstadoDelMundo>();
        li.add(ed);
        li.add(ed2);
        
        System.out.println(ed.estadoDelmundoContenido(li, ed3));*/
        
        EstadoDelMundo ed = new EstadoDelMundo();
        ed.agregarEstado(e);
        ed.agregarEstado(e2);
        ed.agregarEstado(e3);
        
        System.out.println("\ned:");
        ed.imprimirLista();
        
        System.out.println("\ned2:");
        ed.eliminarElemento(e2);
        ed.imprimirLista();
    }
}
