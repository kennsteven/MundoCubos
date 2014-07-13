package mundocubos;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Label;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.universe.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.Vector3d;


public class Graficos2 extends JFrame implements KeyListener, ActionListener{

    //*Constantes*/
    /*A mesa, B mesa, C mesa*/
    static final Vector3f POSMESAA= new Vector3f(-0.6f,-0.6f,0.0f);
    static final Vector3f POSMESAB= new Vector3f(0.0f, -0.6f, 0.0f);
    static final Vector3f POSMESAC= new Vector3f(0.6f,-0.6f,0.0f);
    
    /*A mesa, B sobre C, C mesa*/
    /*
    static final Vector3f POSMESAA= new Vector3f(-0.6f,-0.6f,0.0f);
    static final Vector3f POSMESAB= new Vector3f(0.0f, -0.6f, 0.0f);
    static final Vector3f POSMESAC= new Vector3f(0.6f,-0.6f,0.0f);   
    */
    
    /*A mesa, B sobre mesa, C sobre A*/
    /*
    static final Vector3f POSMESAA= new Vector3f(-0.6f,-0.6f,0.0f);
    static final Vector3f POSMESAB= new Vector3f(0.0f, -0.6f, 0.0f);
    static final Vector3f POSMESAC= new Vector3f(-0.6f,0.0f,0.0f);
    */
    
    /*A mesa, B sobre A, C sobre B*/
    /*
    static final Vector3f POSMESAA= new Vector3f(-0.6f,-0.6f,0.0f);
    static final Vector3f POSMESAB= new Vector3f(-0.6f, 0.0f, 0.0f);
    static final Vector3f POSMESAC= new Vector3f(-0.6f, 0.6f,0.0f);
    */
    /*Controlador*/
    private MundoCubos controlador;
    Panel panel;
    /*Ojetos cubos y movimientos*/
    private int vectorCubosDebajo[] = {0,0,0};
    
    
    //[0] = 0 --> no se movio A, [0] = 1 --> A se movio 1 a la der, [0] = 2 --> A se movio 2 a la derecha( esta sobre c)
    //[1] = 0 --> no se movio B, [1] = -1 --> B se movio 1 a la izq, [1] = 1 --> B se movio 1 a la derecha( esta sobre c)
    //[2] = 0 --> no se movio C, [2] = 1 --> C se movio 1 a la izq, [1] = 2 --> C se movio 2 a la izquierda( esta sobre c)
    private int vectorMovimietnosAlado[] = {0,0,0};
    
    private CuboGrafico cuboA;
    private CuboGrafico cuboB;
    private CuboGrafico cuboC;   
    
    //private TransformGroup objTransD;

    public Graficos2(MundoCubos controlador){
        this.controlador = controlador;
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();    
        this.panel = new Panel(this.controlador);
        Canvas3D canvas = new Canvas3D(config);
        canvas.addKeyListener(this);

        this.setSize(800, 600);
        this.setVisible(true);
        this.add("North",new Label("Proyecto realizado por Kenneht Alvarado"));
        this.add("Center",canvas);
        this.add("South",panel);      
        panel.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        BranchGroup contents = new BranchGroup();

        /*******Cubo A*******************************/
        cuboA = new CuboGrafico(POSMESAA, 'A');
        contents.addChild(cuboA.getObjTrans());
        
       /****Cubo B************************************/
        cuboB = new CuboGrafico(POSMESAB, 'B');
        contents.addChild(cuboB.getObjTrans());

       /***********CuboC*******************************/        
        cuboC = new CuboGrafico(POSMESAC,'C');
        contents.addChild(cuboC.getObjTrans());
        /*Vector3f tempor= new Vector3f(0.6f,-0.6f,0.0f);
        cuboC.setPosicionInicial(tempor);*/
        
        /*******************Aniadir Luz*******************/
        // Creates a bounding sphere for the lights
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(1000d);

        // Then create a directional light with the given
        // direction and color
       DirectionalLight lightD = new DirectionalLight(new Color3f(0.5f, 0.0f, 0.2f),new Vector3f(-0.9f, -0.6f, -1.0f));
        //DirectionalLight lightD = new DirectionalLight(new Color3f(0.5f, 0.0f, 0.2f),new Vector3f(0.0f, 0.6f, 0.6f));

        lightD.setInfluencingBounds(bounds);

	// Then add it to the root BranchGroup
        contents.addChild(lightD);
        
        BoundingSphere bounds2 = new BoundingSphere();
        bounds2.setRadius(1000d);

        // Then create a directional light with the given
        // direction and color
        DirectionalLight lightD2 = new DirectionalLight(new Color3f(0.5f, 1.0f, 0.2f),new Vector3f(0.6f, 0.6f, -1.0f));
        lightD2.setInfluencingBounds(bounds2);

	// Then add it to the root BranchGroup
        //contents.addChild(lightD2);
        
        /**********Universo******************/
        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(contents); 
        System.out.println((int)'C');
    }
    
    public int[] getVectorDeBajo(){
        return vectorCubosDebajo;
    }
    
    public void setVectorDeBajo(int i, int v){
        vectorCubosDebajo[i] = v;
    }
    
    public void moverCubo(char c1, char c2){
        System.out.println("\nEl cubo arriba " + c1 + " Cubo de abajo " + c2);
        if(c2 =='M'){
            CuboGrafico cuboEncima = escogerCubo(c1);
            moverAlaMesa2(cuboEncima,cuboEncima.getVectorEnMesa());
            this.vectorCubosDebajo[cuboEncima.getNombre() - 65] = 0;
        }else{
            CuboGrafico cuboEncima = escogerCubo(c1);
            CuboGrafico cuboDebajo = escogerCubo(c2);
            moverCuboSobreCubo(cuboEncima,cuboDebajo);
        }
    }
    
    public CuboGrafico escogerCubo(char c1){
        switch(c1){
            case 'A':
                return cuboA;
            case 'B':
                return cuboB;
            case 'C':
               return cuboC;
        }
        return null;
    }
    
    //C1 cubo el que va sobre C2 destino
    public void moverCuboSobreCubo(CuboGrafico cuboEncima, CuboGrafico cuboDebajo){
        boolean entro = true;
        int posV1 = cuboEncima.getNombre()-65;
        int posV2 = cuboDebajo.getNombre()-65;
        int finCiclo = vectorCubosDebajo[posV2] - vectorCubosDebajo[posV1] + 1;
        System.out.println(Arrays.toString(this.getVectorDeBajo()));
        //System.out.println("El fin " + finCiclo + " el cubo 1 es: " + cuboEncima.getNombre() + 
          //      " el cubo2 es " + cuboDebajo.getNombre());
        //MoverArriba
        for(int i = 0; i < finCiclo; i++){
            //if(entro){
                vectorCubosDebajo[cuboEncima.getNombre()-65] += 1;
                entro = false;
            //}
            moverArriba(cuboEncima);
        }
        moverCuboA2(cuboEncima,cuboDebajo);
    }
    
    public void moverAlaMesa2(CuboGrafico cubo, Vector3f v){
        CuboGrafico cuboN = new CuboGrafico(v, 'T');
        moverCuboA2(cubo, cuboN);
        int finCiclo = vectorCubosDebajo[cubo.getNombre() - 65];
        System.out.println(Arrays.toString(this.getVectorDeBajo()));
        //System.out.println("El fin " + finCiclo + " el cubo 1 es: " + cubo.getNombre() + 
          //      " el cubo2 es " + cuboN.getNombre());
        for(int i = 0; i < finCiclo; i++){
            moverAbajo(cubo);
        }
    }
   
    public Vector3f moverCuboA2(CuboGrafico cuboEncima, CuboGrafico cuboDebajo){
        float x1 = cuboEncima.getV().getX();
        float x2 = cuboDebajo.getV().getX();
        //System.out.println("El x1 es: " + x1 + " y El x2 es:" + x2);
        Float yFinal = cuboEncima.getV().getY();
        Vector3f vectorNuevo = new Vector3f(cuboEncima.getV().x, cuboEncima.getV().y,cuboEncima.getV().z);
        if(x1 > x2){
            while(x1 > x2){
                x1 -= 0.01f;
                vectorNuevo = new Vector3f(x1, yFinal,cuboDebajo.getV().getZ());
                cuboEncima.getPos().setTranslation(vectorNuevo);
                cuboEncima.getObjTrans().setTransform(cuboEncima.getPos());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Se movio a a la IZQUIERDA " + cuboEncima.getNombre());
            }
        }else{
            while(x1 < x2){
                x1 += 0.01f;
                vectorNuevo = new Vector3f(x1, yFinal,cuboDebajo.getV().getZ());
                cuboEncima.getPos().setTranslation(vectorNuevo);
                cuboEncima.getObjTrans().setTransform(cuboEncima.getPos());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Se movio a a la DERECHA " + cuboEncima.getNombre());
            }
        }
        
        //if(entro){vectorCubosDebajo[cuboEncima.getNombre()-65] += 1;}
        return cuboEncima.setPosicion(vectorNuevo, cuboEncima.getPos(), cuboEncima.getObjTrans());
    }
     
    public Vector3f moverArriba(CuboGrafico cubo){
        boolean seguir = true;
        Vector3f vectorNuevo = new Vector3f(cubo.getV().x, cubo.getV().y,cubo.getV().z);
        float valorF = cubo.getV().y + 0.6f;
        while(seguir){
            vectorNuevo = new Vector3f(vectorNuevo.x, vectorNuevo.y + 0.01f,vectorNuevo.z);
            try {
                //Thread.sleep(1);
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
            }
            cubo.getPos().setTranslation(vectorNuevo);
            cubo.getObjTrans().setTransform(cubo.getPos());
            if(valorF < vectorNuevo.y){
                seguir = false;
            }
            System.out.println("Se movio para ARRIBA" + cubo.getNombre());
        }
        
        return cubo.setPosicion(cubo.getV().x, valorF, cubo.getV().z, cubo.getPos(), cubo.getObjTrans());
    }
    
    public Vector3f moverAbajo(CuboGrafico cubo){
        boolean seguir = true;
        Vector3f vectorNuevo = new Vector3f(cubo.getV().x, cubo.getV().y,cubo.getV().z);
        float valorF = cubo.getV().y - 0.6f;
        while(seguir){
            vectorNuevo = new Vector3f(vectorNuevo.x, vectorNuevo.y - 0.01f,vectorNuevo.z);
            cubo.getPos().setTranslation(vectorNuevo);
            cubo.getObjTrans().setTransform(cubo.getPos());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(valorF > vectorNuevo.y){
                seguir = false;
            }
            System.out.println("Se movio para ABAJO " + cubo.getNombre());
        }    
        return cubo.setPosicion(cubo.getV().x, valorF, cubo.getV().z, cubo.getPos(), cubo.getObjTrans());
    }
    
    public Vector3f moverDerecha(CuboGrafico cubo){
        boolean seguir = true;
        Vector3f vectorNuevo = new Vector3f(cubo.getV().x, cubo.getV().y,cubo.getV().z);
        float valorF = cubo.getV().x + 0.6f;
        while(seguir){
            System.out.println(vectorNuevo.x);
            vectorNuevo = new Vector3f(vectorNuevo.x + 0.01f, vectorNuevo.y,vectorNuevo.z);
            cubo.getPos().setTranslation(vectorNuevo);
            cubo.getObjTrans().setTransform(cubo.getPos());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(valorF < vectorNuevo.x){
                seguir = false;
            }
        }
        return cubo.setPosicion(valorF, cubo.getV().y, cubo.getV().z, cubo.getPos(), cubo.getObjTrans());
    }
    
    public Vector3f moverIzquierda(CuboGrafico cubo){
        boolean seguir = true;
        Vector3f vectorNuevo = new Vector3f(cubo.getV().x, cubo.getV().y,cubo.getV().z);
        float valorF = cubo.getV().x - 0.6f;
        while(seguir){
            vectorNuevo = new Vector3f(vectorNuevo.x - 0.01f, vectorNuevo.y,vectorNuevo.z);
            cubo.getPos().setTranslation(vectorNuevo);
            cubo.getObjTrans().setTransform(cubo.getPos());
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Graficos2.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(valorF >= vectorNuevo.x){
                seguir = false;
            }
        }
        cubo.setPosicion(valorF, cubo.getV().y, cubo.getV().z, cubo.getPos(), cubo.getObjTrans());
        return vectorNuevo;
    }   
    
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()=='w') {
           //vC = moverArriba(vC, posC, objTransC);
           //vB = moverArriba(vB, posB, objTransB);
           moverArriba(cuboC);
           

        }
        
        if (e.getKeyChar()=='s') {
            //vC = moverAbajo(vC, posC, objTransC);
            //vB = moverAbajo(vB, posB, objTransB);
           moverAbajo(cuboC);
        }
        
        if (e.getKeyChar()=='a') {
            //vC = moverIzquierda(vC, posC, objTransC);
            //vB = moverIzquierda(vB, posB, objTransB);
           moverIzquierda(cuboC);

        }
        
        if (e.getKeyChar()=='d') {
            //vC = moverDerecha(vC, posC, objTransC);
            moverDerecha(cuboC);
            //moverDerecha(cuboA);
        }
        
        if (e.getKeyChar()=='k') {
            //vA = moverAlaMesaA(vA, POSMESAA, posA, objTransA);
            //vB = moverAlaMesaB(vB, POSMESAB, posB, objTransB);
            //vC = moverAlaMesaC(vC, POSMESAC, posC, objTransC);
            //moverCubo('A','B');
        }
        
        if (e.getKeyChar()=='j') {
            //vA = moverAlaMesaA(vA, POSMESAA, posA, objTransA);
            //vB = moverAlaMesaB(vB, POSMESAB, posB, objTransB);
            //vC = moverAlaMesaC(vC, POSMESAC, posC, objTransC);
            moverCubo('C', 'M');
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
    }
    
    public static void main(String[] args) {
        MundoCubos controlador = new MundoCubos();
        Graficos2 demo = new Graficos2(controlador);
        demo.moverCubo('A', 'M');
        demo.moverCubo('B', 'M');
        demo.moverCubo('C', 'M');
        
        demo.moverCubo('A', 'M');
        demo.moverCubo('C', 'M');
        demo.moverCubo('B', 'A');
        
        demo.moverCubo('A', 'M');
        demo.moverCubo('B', 'A');
        demo.moverCubo('C', 'B');
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
