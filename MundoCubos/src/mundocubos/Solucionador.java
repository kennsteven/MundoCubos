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
public class Solucionador {
    
    //EstadoDelMundo estadoActual;
    EstadoDelMundo estadoInicial;
    EstadoDelMundo estadoMeta;
    EstadoDelMundo estadoTemporal;
    EstadoDelMundo solucion;
    ArrayList<EstadoDelMundo> listaDeEstadosVisitados;
    ArrayList<EstadoDelMundo> listaDeSolucion;
    boolean fin = false;

    public Solucionador(EstadoDelMundo inicial, EstadoDelMundo meta){
        estadoTemporal = new EstadoDelMundo();
        listaDeEstadosVisitados = new ArrayList<EstadoDelMundo>();
        listaDeSolucion = new ArrayList<EstadoDelMundo>();
        this.estadoInicial = inicial;
        this.estadoMeta = meta;
    }
    
    public ArrayList<EstadoDelMundo> getListaSolucion(){
        return listaDeSolucion;
    }
    
    public void eliminarRangoEstados(ArrayList<EstadoDelMundo> listaDeEstados, int ini, int fin){
        for(int i = ini; i < fin; i++ ){
            System.out.println("EL I ES: " + i + " el size es: " + listaDeEstados.size());
            listaDeEstados.remove(i);
        }
    }
    
    public void analizarSolucion(ArrayList<EstadoDelMundo> listaDeEstados){
        boolean terminar = false;
        do{
            terminar = cicloAnalizarSolucion(listaDeEstados);
        }while(terminar);
    }
    
    public boolean cicloAnalizarSolucion(ArrayList<EstadoDelMundo> listaDeEstados){
        for(int i = 0; i < listaDeEstados.size(); i++){
            EstadoDelMundo estadoActual = listaDeEstados.get(i);
            System.out.println("Ciclo for i= " + i + " size = " + listaDeEstados.size());
            for(int j =(listaDeEstados.size()-1); j > i+1 ; j--){
                
                for(int k = 0; k < estadoActual.getLista().size(); k++){
                    EstadoDelMundo temporal2 = estadoActual.copia();
                    Estado es = temporal2.getLista().get(k);
                    
                    if(colocarMesa(temporal2, es.getCuboEncima(),es)){                          
                        temporal2.imprimirLista();
                        if(listaDeEstados.get(j).iguales(temporal2, listaDeEstados.get(j))){
                            System.out.println("SI KLARROOO i= " + i + " j = " + j);
                            eliminarRangoEstados(listaDeEstados, i + 1,j);
                            return true;
                            //break;
                        }
                    }
                    ArrayList<String> l; //Lista de cubos en los que se puede colocar encima el cubo actual
                    l = estadoActual.cubosSinNadaEncima();
                    for(int m = 0; m < l.size(); m++){
                        temporal2 = estadoActual.copia();
                        if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(m),es)){
                            if(listaDeEstados.get(j).iguales(temporal2,listaDeEstados.get(j))){
                                System.out.println("\nSI KLARROOO22 i= " + i + " j = " + j);
                                eliminarRangoEstados(listaDeEstados, i + 1,j);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean colocarMesa(EstadoDelMundo ed, String cuboAmover, Estado es){
        //no tener nada encima
        Estado nuevo = null;
        if(ed.nadaEncima(cuboAmover)==false && ed.sobreLaMesa(cuboAmover)==false){
            //System.out.println("Entro a colocar en la mesa");
            nuevo = new Estado(cuboAmover, "M", "S");
            ed.eliminarElemento(es);
            ed.agregarEstado(nuevo);
            //System.out.println("El estado recibido en colocar a la mesa es: ");
            ed.imprimirLista();
            int k = ed.getLista().size();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean colocarCubo1SobreCubo2(EstadoDelMundo ed,String cubo1, String cubo2, Estado es){
        Estado nuevo;
        if(!ed.nadaEncima(cubo1) && !ed.nadaEncima(cubo2)&& !cubo1.equals(cubo2)){
            //System.out.println("\n\nEl estado que se tiene es: " + es.getEstado());
            
            //System.out.println("\n\nEl estado recibido en colocar uno sobre otro: ");
            ed.imprimirLista();
            //System.out.println("Entro a colocar cubo " + cubo1 + "sobre " + cubo2);
            nuevo = new Estado(cubo1, cubo2, "S");
            ed.eliminarElemento(es);
            ed.agregarEstado(nuevo);
            int k = ed.getLista().size();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean estadoDelmundoContenido(ArrayList<EstadoDelMundo> lista, EstadoDelMundo estados){
        boolean respuesta = false;
        
        for(int i = 0; i < lista.size(); i++){
            if(lista.get(i).iguales(lista.get(i), estados)){
                respuesta = true;
                break;
            }
        }
        
        return respuesta;
    }
    
    public void imprimirListaDeEstadosDelMundo(ArrayList<EstadoDelMundo> lista){
        System.out.println("La lista de estados es: \n[ ");
        for(int i = 0; i< lista.size(); i++){
            lista.get(i).imprimirLista();
            if(i != lista.size() - 1){
                System.out.println(",");
            }
        }
        System.out.println("\n] ");
    }
    
    public void solucionar(){
        //System.out.println("Entro1");
        //System.out.println("\nEl inicial es: ");
        estadoInicial.imprimirLista();
        solucion = estadoInicial.copia();
        soluciona2(solucion);
        analizarSolucion(getListaSolucion());
    }
    
    /*
     * Explora todas las posbles opciones posibles para encontrar el camino de
     * un estado a otro
     */
    public void soluciona(EstadoDelMundo estadoActual){
        //System.out.println("Entro2");
        if(this.fin == false){
            if(estadoActual.iguales(estadoActual, estadoMeta)){
                this.fin = true;
                //System.out.println("CONDICION DE PARADA");
                estadoActual.imprimirLista();
                listaDeSolucion.add(0,estadoActual.copia());
                return;
            }else{
            /*Analiza todos los estados posibles a partir del movimiento de un solo cubo*/    
                //System.out.println("Entro3");
                /*Solo lo ejecuta:
                 * Si el estado actual en el que estoy no ha sido visitado por esta posibilidad*/
                //System.out.println("La respusta a si esta contenido es " + 
                //estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual));
                
                //System.out.println("El estado actual es: ");
                //estadoActual.imprimirLista();
                
                //System.out.println("\nLos estados visitados son: ");
                //imprimirListaDeEstadosDelMundo(listaDeEstadosVisitados);
                
                if(estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual) == false){
                    //System.out.println("Entro4");
                    EstadoDelMundo temporal = estadoActual.copia();
                    listaDeEstadosVisitados.add(0,temporal);
                    
                    //System.out.println("\nLos estados visitados despues del add: ");
                    //imprimirListaDeEstadosDelMundo(listaDeEstadosVisitados);
                    
                    /*Por cada elemento(estado) del mundo actual se analizan los posibles movimientos*/
                    for(int i = 0; i< estadoActual.getLista().size() && !fin;i++){
                        EstadoDelMundo temporal2 = estadoActual.copia();
                        //System.out.println("Entro5");
                        Estado es = estadoActual.getLista().get(i);
                        //System.out.println("el que esta encima es: "+ es.getCuboEncima());
                        /**
                         * Si se puede colocar en la mesa se coloca en la mesa y 
                         * se analizan las siguientes posibilidades
                        */

                        /*Si no se encuentra solucion colocando elemento en la mesa
                         * se busca colocar el cubo encima de de otro
                         */
                        ArrayList<String> l; //Lista de elemtos en los que se puede colocar encima el cubo actual
                        l = estadoActual.cubosSinNadaEncima();
                        //System.out.println("el tam es: " + l.size());
                        //Por cada elemento se analiza si el cubo actual se poene encima genera solución
                        for(int j = 0; j < l.size(); j++){
                            temporal2 = estadoActual.copia();
                            //Si se puede colocar el cubo encima de otro se anlizan las posibilidades
                            //a partir de ese nuevo estado
                            //System.out.println("Entro 7");
                            if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(j),es)){
                              //  System.out.println("Entro 8");
                                soluciona(temporal2);
                                //System.out.println("DESPUES DEL RETURN DE COLOCAR"+ this.fin);
                                if(this.fin == true){
                                    listaDeSolucion.add(0,estadoActual.copia());
                                  //  System.out.println("\nNO ENTRA ACA 2");
                                    return;
                                }
                            }
                        }

                        if(colocarMesa(temporal2, es.getCuboEncima(),es)){
                            
                            //System.out.println("\nEl estado actual despues de ser modificado por la mesa es: ");
                            temporal2.imprimirLista();
                            
                            //System.out.println("Entro6");
                            soluciona(temporal2);
                            //System.out.println("DESPUES DEL RETURN DE MESA" + fin);
                            if(this.fin == true){
                                //System.out.println("NO ENTRA ACA 1");
                                //temporal = estadoActual.copia();
                                listaDeSolucion.add(0,estadoActual.copia());
                                return;
                            }
                        }
                        
                        
                    }
                }
                
            }
        }
    }
    
    public void soluciona2(EstadoDelMundo estadoActual){
        //System.out.println("Entro2");
        if(this.fin == false){
            if(estadoActual.iguales(estadoActual, estadoMeta)){
                this.fin = true;
                //System.out.println("CONDICION DE PARADA");
                estadoActual.imprimirLista();
                listaDeSolucion.add(0,estadoActual.copia());
                return;
            }else{
            /*Analiza todos los estados posibles a partir del movimiento de un solo cubo*/    
                //System.out.println("Entro3");
                /*Solo lo ejecuta:
                 * Si el estado actual en el que estoy no ha sido visitado por esta posibilidad*/
                //System.out.println("La respusta a si esta contenido es " + 
                //estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual));
                
                //System.out.println("El estado actual es: ");
                //estadoActual.imprimirLista();
                
                //System.out.println("\nLos estados visitados son: ");
                //imprimirListaDeEstadosDelMundo(listaDeEstadosVisitados);
                
                if(estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual) == false){
                    //System.out.println("Entro4");
                    EstadoDelMundo temporal = estadoActual.copia();
                    listaDeEstadosVisitados.add(0,temporal);
                    
                    //System.out.println("\nLos estados visitados despues del add: ");
                    //imprimirListaDeEstadosDelMundo(listaDeEstadosVisitados);
                    
                    /*Por cada elemento(estado) del mundo actual se analizan los posibles movimientos*/
                    for(int i = 0; i< estadoActual.getLista().size() && !fin;i++){
                        EstadoDelMundo temporal2 = estadoActual.copia();
                        //System.out.println("Entro5");
                        Estado es = estadoActual.getLista().get(i);
                        //System.out.println("el que esta encima es: "+ es.getCuboEncima());
                        /**
                         * Si se puede colocar en la mesa se coloca en la mesa y 
                         * se analizan las siguientes posibilidades
                        */
                        if(colocarMesa(temporal2, es.getCuboEncima(),es)){
                            
                            //System.out.println("\nEl estado actual despues de ser modificado por la mesa es: ");
                            temporal2.imprimirLista();
                            
                            //System.out.println("Entro6");
                            soluciona(temporal2);
                            //System.out.println("DESPUES DEL RETURN DE MESA" + fin);
                            if(this.fin == true){
                                //System.out.println("NO ENTRA ACA 1");
                                //temporal = estadoActual.copia();
                                listaDeSolucion.add(0,estadoActual.copia());
                                return;
                            }
                        }
                        /*Si no se encuentra solucion colocando elemento en la mesa
                         * se busca colocar el cubo encima de de otro
                         */
                        ArrayList<String> l; //Lista de elemtos en los que se puede colocar encima el cubo actual
                        l = estadoActual.cubosSinNadaEncima();
                        //System.out.println("el tam es: " + l.size());
                        //Por cada elemento se analiza si el cubo actual se poene encima genera solución
                        for(int j = 0; j < l.size(); j++){
                            temporal2 = estadoActual.copia();
                            //Si se puede colocar el cubo encima de otro se anlizan las posibilidades
                            //a partir de ese nuevo estado
                            //System.out.println("Entro 7");
                            if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(j),es)){
                              //  System.out.println("Entro 8");
                                soluciona(temporal2);
                                //System.out.println("DESPUES DEL RETURN DE COLOCAR"+ this.fin);
                                if(this.fin == true){
                                    listaDeSolucion.add(0,estadoActual.copia());
                                  //  System.out.println("\nNO ENTRA ACA 2");
                                    return;
                                }
                            }
                        }

                        
                        
                        
                    }
                }
                
            }
        }
    }
    
    public static void main(String args[]){

        Estado e = new Estado("C", "M", "S");
        //System.out.println(e.getEstado());
        
        Estado e2 = new Estado("B", "C", "S");
        //System.out.println(e2.getEstado());
        
        Estado e3 = new Estado("A", "B", "S");
        //System.out.println(e3.getEstado());
        
        Estado e4 = new Estado("A", "M", "S");
        //System.out.println(e4.getEstado());
        
        Estado e5 = new Estado("B", "A", "S");
        //System.out.println(e5.getEstado());
        
        Estado e6 = new Estado("C", "B", "S");
        //System.out.println(e6.getEstado());
     
       
        /*
        Estado e = new Estado("A", "M", "S");
        //System.out.println(e.getEstado());
        
        Estado e2 = new Estado("B", "M", "S");
        //System.out.println(e2.getEstado());
        
        Estado e3 = new Estado("C", "M", "S");
        //System.out.println(e3.getEstado());
        
        Estado e4 = new Estado("C", "M", "S");
        //System.out.println(e4.getEstado());
        
        Estado e5 = new Estado("B", "C", "S");
        //System.out.println(e5.getEstado());
        
        Estado e6 = new Estado("A", "B", "S");
        //System.out.println(e6.getEstado());
        */
  
  /*      
        Estado e = new Estado("A", "M", "S");
        //System.out.println(e.getEstado());
        
        Estado e2 = new Estado("B", "M", "S");
        //System.out.println(e2.getEstado());
        
        Estado e3 = new Estado("C", "M", "S");
        //System.out.println(e3.getEstado());
        
        Estado e4 = new Estado("A", "C", "S");
        //System.out.println(e4.getEstado());
        
        Estado e5 = new Estado("B", "M", "S");
        //System.out.println(e5.getEstado());
        
        Estado e6 = new Estado("C", "M", "S");
        //System.out.println(e6.getEstado());
    */    
        
        EstadoDelMundo ed = new EstadoDelMundo();
        ed.agregarEstado(e);
        ed.agregarEstado(e2);
        ed.agregarEstado(e3);

        EstadoDelMundo ed2 = new EstadoDelMundo();
        ed2.agregarEstado(e4);
        ed2.agregarEstado(e5);
        ed2.agregarEstado(e6);
        
        /*EstadoDelMundo ed3 = new EstadoDelMundo();
        ed2.agregarEstado(e2);
        ed2.agregarEstado(e3);
        
        ed.imprimirLista();
        
        System.out.println("nada encima" + ed.nadaEncima("B"));
        System.out.println("sobre la mesa" + ed.sobreLaMesa("B"));
        
        ed.imprimirLista();
        ed2.imprimirLista();
        System.out.println(ed.iguales(ed,ed2));
        
        
        ArrayList<EstadoDelMundo> li = new ArrayList<EstadoDelMundo>();
        li.add(ed);
        li.add(ed2);
        */
        //System.out.println(ed.estadoDelmundoContenido(li, ed3));
        Solucionador s = new Solucionador(ed, ed2);
        //s.imprimirListaDeEstadosDelMundo(li);
        s.solucionar();
        System.out.println("\nLa solucion es: ");
        s.imprimirListaDeEstadosDelMundo(s.listaDeSolucion);
        System.out.println("Los visitados son: ");
        s.imprimirListaDeEstadosDelMundo(s.listaDeEstadosVisitados);
    }
}
