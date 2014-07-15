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
    ArrayList<String> listaDeMovimientos;
    boolean fin = false;

    public Solucionador(EstadoDelMundo inicial, EstadoDelMundo meta){
        estadoTemporal = new EstadoDelMundo();
        listaDeEstadosVisitados = new ArrayList<EstadoDelMundo>();
        listaDeSolucion = new ArrayList<EstadoDelMundo>();
        listaDeMovimientos = new ArrayList<String>();
        this.estadoInicial = inicial;
        this.estadoMeta = meta;
    }
    
    public ArrayList<EstadoDelMundo> getListaSolucion(){
        return listaDeSolucion;
    }
    
    public void eliminarRangoEstados(ArrayList<EstadoDelMundo> listaDeEstados, int ini, int fin){
        for(int i = ini; i < fin; i++ ){
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
            for(int j =(listaDeEstados.size()-1); j > i+1 ; j--){
                
                for(int k = 0; k < estadoActual.getLista().size(); k++){
                    EstadoDelMundo temporal2 = estadoActual.copia();
                    Estado es = temporal2.getLista().get(k);
                    
                    if(colocarMesa(temporal2, es.getCuboEncima(),es)){                          
                        temporal2.imprimirLista();
                        if(listaDeEstados.get(j).iguales(temporal2, listaDeEstados.get(j))){
                            eliminarRangoEstados(listaDeEstados, i + 1,j);
                            return true;
                        }
                    }
                    ArrayList<String> l; //Lista de cubos en los que se puede colocar encima el cubo actual
                    l = estadoActual.cubosSinNadaEncima();
                    for(int m = 0; m < l.size(); m++){
                        temporal2 = estadoActual.copia();
                        if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(m),es)){
                            if(listaDeEstados.get(j).iguales(temporal2,listaDeEstados.get(j))){
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
            nuevo = new Estado(cuboAmover, "M", "S");
            ed.eliminarElemento(es);
            ed.agregarEstado(nuevo);
            ed.imprimirLista();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean colocarCubo1SobreCubo2(EstadoDelMundo ed,String cubo1, String cubo2, Estado es){
        Estado nuevo;
        if(!ed.nadaEncima(cubo1) && !ed.nadaEncima(cubo2)&& !cubo1.equals(cubo2)){
            ed.imprimirLista();
            nuevo = new Estado(cubo1, cubo2, "S");
            ed.eliminarElemento(es);
            ed.agregarEstado(nuevo);
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
    
    public String imprimirListaDeMovimeintos(ArrayList<String> lista){
        String respuesta = "";
        for(int i = 0; i< lista.size(); i++){
            respuesta += (i+1) + ". " + lista.get(i) + "\n";
        }
        return respuesta;
    }
    
    public void solucionar(){
        estadoInicial.imprimirLista();
        solucion = estadoInicial.copia();
        soluciona(solucion);
        analizarSolucion(getListaSolucion());
    }
    
    /*
     * Explora todas las posbles opciones posibles para encontrar el camino de
     * un estado a otro
     */
    public void soluciona(EstadoDelMundo estadoActual){
        if(this.fin == false){
            if(estadoActual.iguales(estadoActual, estadoMeta)){
                this.fin = true;
                estadoActual.imprimirLista();
                listaDeSolucion.add(0,estadoActual.copia());
                return;
            }else{
                /*Analiza todos los estados posibles a partir del movimiento de un solo cubo*/    
                /*Solo lo ejecuta:
                 * Si el estado actual en el que estoy no ha sido visitado por esta posibilidad*/
                if(estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual) == false){
                    EstadoDelMundo temporal = estadoActual.copia();
                    listaDeEstadosVisitados.add(0,temporal);
                    /*Por cada elemento(estado) del mundo actual se analizan los posibles movimientos*/
                    for(int i = 0; i< estadoActual.getLista().size() && !fin;i++){
                        EstadoDelMundo temporal2 = estadoActual.copia();
                        Estado es = estadoActual.getLista().get(i);
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
                            if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(j),es)){
                                soluciona(temporal2);
                                if(this.fin == true){
                                    listaDeSolucion.add(0,estadoActual.copia());
                                    String s = "Mover cubo " + es.getCuboEncima() + " sobre " + l.get(j);
                                    listaDeMovimientos.add(0,s);
                                    return;
                                }
                            }
                        }

                        /**
                         * Si se puede colocar en la mesa se coloca en la mesa y 
                         * se analizan las siguientes posibilidades
                        */
                        if(colocarMesa(temporal2, es.getCuboEncima(),es)){
                            temporal2.imprimirLista();
                            soluciona(temporal2);
                            if(this.fin == true){
                                listaDeSolucion.add(0,estadoActual.copia());
                                String s = "Mover cubo " + es.getCuboEncima() + " a la mesa";
                                listaDeMovimientos.add(0,s);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void soluciona2(EstadoDelMundo estadoActual){
        if(this.fin == false){
            if(estadoActual.iguales(estadoActual, estadoMeta)){
                this.fin = true;
                estadoActual.imprimirLista();
                listaDeSolucion.add(0,estadoActual.copia());
                return;
            }else{
            /*Analiza todos los estados posibles a partir del movimiento de un solo cubo*/    
                /*Solo lo ejecuta:
                 * Si el estado actual en el que estoy no ha sido visitado por esta posibilidad*/
                if(estadoActual.estadoDelmundoContenido(listaDeEstadosVisitados, estadoActual) == false){
                    EstadoDelMundo temporal = estadoActual.copia();
                    listaDeEstadosVisitados.add(0,temporal);
                    
                    /*Por cada elemento(estado) del mundo actual se analizan los posibles movimientos*/
                    for(int i = 0; i< estadoActual.getLista().size() && !fin;i++){
                        EstadoDelMundo temporal2 = estadoActual.copia();
                        Estado es = estadoActual.getLista().get(i);
                        /**
                         * Si se puede colocar en la mesa se coloca en la mesa y 
                         * se analizan las siguientes posibilidades
                        */
                        if(colocarMesa(temporal2, es.getCuboEncima(),es)){
                            temporal2.imprimirLista();
                            soluciona(temporal2);
                            if(this.fin == true){
                                listaDeSolucion.add(0,estadoActual.copia());
                                String s = "Mover cubo " + es.getCuboEncima() + " a la mesa";
                                listaDeMovimientos.add(0,s);
                                return;
                            }
                        }
                        /*Si no se encuentra solucion colocando elemento en la mesa
                         * se busca colocar el cubo encima de de otro
                         */
                        ArrayList<String> l; //Lista de elemtos en los que se puede colocar encima el cubo actual
                        l = estadoActual.cubosSinNadaEncima();
                        //Por cada elemento se analiza si el cubo actual se poene encima genera solución
                        for(int j = 0; j < l.size(); j++){
                            temporal2 = estadoActual.copia();
                            //Si se puede colocar el cubo encima de otro se anlizan las posibilidades
                            //a partir de ese nuevo estado
                            if(colocarCubo1SobreCubo2(temporal2, es.getCuboEncima(), l.get(j),es)){
                                soluciona(temporal2);
                                if(this.fin == true){
                                    listaDeSolucion.add(0,estadoActual.copia());
                                    String s = "Mover cubo " + es.getCuboEncima() + " sobre " + l.get(j);
                                    listaDeMovimientos.add(0,s);
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
        System.out.println("Los movimientos son: \n" + s.imprimirListaDeMovimeintos(s.listaDeMovimientos));
        
    }
}
