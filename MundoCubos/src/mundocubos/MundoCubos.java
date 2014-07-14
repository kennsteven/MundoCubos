
package mundocubos;

//Clase Controladora

import java.util.Arrays;
import javax.swing.JOptionPane;

public class MundoCubos {
    Graficos2 interfaz;
    Solucionador solucionador;
    private EstadoDelMundo estadoInicial;
    private EstadoDelMundo estadoFinal;
    private EstadoDelMundo estadoActual;
    
    public MundoCubos(){
        estadoActual = creaEstadoDelMundo("S(A,M);S(B,M);S(C,M)");
        interfaz = new Graficos2(this);
    }
    
    public void recibeDatos(String estadoInicial, String estadoFinal){
        interfaz.panel.setVisible(false);
        JOptionPane.showMessageDialog(null, "Estableciendo el estado inicial");
        setEstadoInicial(estadoInicial);
        System.out.println("------------------------------------------HASTA ACA--------------------------------");
        System.out.println(Arrays.toString(interfaz.getVectorDeBajo()));
        comenzarProcesamiento(estadoInicial, estadoFinal);
        interfaz.panel.setVisible(true);
    }
    
    public void setEstadoInicial(String s){
        EstadoDelMundo estadoInicialTemp = this.estadoActual.copia();
        EstadoDelMundo estadoFinalTemp = creaEstadoDelMundo(s);
        this.solucionador = new Solucionador(estadoInicialTemp, estadoFinalTemp);
        this.solucionador.solucionar();
        this.mostrarAnimacionSolucion(false);
        this.estadoActual = estadoFinalTemp.copia();
    }
    
    public void comenzarProcesamiento(String estadoInicial, String estadoFinal){
        this.inicializaEstadosDelMundo(estadoInicial, estadoFinal);
        this.solucionador = new Solucionador(this.estadoInicial, this.estadoFinal);
        this.solucionador.solucionar();
        this.mostrarAnimacionSolucion(true);
        this.estadoActual = this.estadoFinal.copia();
    }
    
    private void mostrarAnimacionSolucion(boolean mostraMensajesEstados){
        
        for(int i = 1; i < solucionador.getListaSolucion().size();i++){
            if(mostraMensajesEstados == true){
                JOptionPane.showMessageDialog(null, "El siguiente estado es: "
                + solucionador.getListaSolucion().get(i).imprimirLista());
            }
            for(int j = 0; j < solucionador.getListaSolucion().get(i).getLista().size(); j++){
                Estado e = solucionador.getListaSolucion().get(i).getLista().get(j);
                interfaz.moverCubo(e.getEstado().charAt(2), e.getEstado().charAt(4));
            }
            //System.out.println("-----CAMBIO DE ESTADO-------");
        }
    }
    
    private void inicializaEstadosDelMundo(String estadoInicial, String estadoFinal){
        this.estadoInicial = creaEstadoDelMundo(estadoInicial);
        this.estadoFinal = creaEstadoDelMundo(estadoFinal);
    }
    
    private EstadoDelMundo creaEstadoDelMundo(String conjuntoEstados){
        EstadoDelMundo ed = new EstadoDelMundo();
        String vectorString [] = conjuntoEstados.split(";");
        Estado e;
        char c1;
        char c2;
        char c3;
        
        for(int i = 0; i < vectorString.length; i++){
            c1 = vectorString[i].charAt(2);
            c2 = vectorString[i].charAt(4);
            c3 = vectorString[i].charAt(0);
            e = creaEstado(c1,c2,c3);
            ed.agregarEstado(e);
        }
        return ed;
    }
    
    private Estado creaEstado(char nombreEstado1, char nombreEstado2, char relacion){
        String nEstado1 = nombreEstado1 + "";
        String nEstado2 = nombreEstado2 + "";
        String sRelacion = relacion + "";
        Estado e = new Estado(nEstado1,nEstado2,sRelacion);
        return e;
    }
    
    public static void main(String[] args) {
        MundoCubos m = new MundoCubos();  
    }
}
