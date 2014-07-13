/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mundocubos;

import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

/**
 *
 * @author Kenneth Alvarado
 */
public class CuboGrafico {
    private TransformGroup objTrans;
    private Transform3D pos;
    private Vector3f v;
    private Vector3f vectorPosicionEnMesa; 
    private char nombre;
    
    public CuboGrafico(Vector3f posInicial,char nombre){
        this.nombre = nombre;
        this.vectorPosicionEnMesa = new Vector3f(posInicial.x,posInicial.y,posInicial.z);
        ColorCube c =  new ColorCube(0.1);
        objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        pos = new Transform3D();
        v =  new Vector3f(posInicial.x,posInicial.y,posInicial.z);
        pos.setTranslation(v);
        
        objTrans.setTransform(pos);
        objTrans.addChild(c);
        //contents.addChild(objTrans);
    }

    public TransformGroup getObjTrans() {
        return objTrans;
    }

    public Transform3D getPos() {
        return pos;
    }

    public Vector3f getV() {
        return v;
    }
    
    public Vector3f getVectorEnMesa() {
        return vectorPosicionEnMesa;
    }
    
    public char getNombre(){
        return nombre;
    }
    
    public void setPosicionInicial(Vector3f n){
        Vector3f v = new Vector3f(n.x,n.y,n.z);
        this.vectorPosicionEnMesa = v;
    }
    
    public Vector3f setPosicion(float x, float y, float z, Transform3D pos, TransformGroup objTrans){
        Vector3f v = new Vector3f(x,y,z);
        this.v = v;
        pos.setTranslation(v);
        objTrans.setTransform(pos);
        return this.v;
    }
    
    public Vector3f setPosicion(Vector3f n, Transform3D pos, TransformGroup objTrans){
        Vector3f v = new Vector3f(n.x,n.y,n.z);
        this.v = v;
        pos.setTranslation(v);
        objTrans.setTransform(pos);
        return this.v;
    }
    
    
}
