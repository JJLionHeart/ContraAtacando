
import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase Shot:
 * 
 * Hereda de la clase base los atributos y métodos para facilitar el
 * funcionamiento del juego, pero tiene atributos extra que facilitan la
 * creacion de disparos por parte del personaje principal
 * 
 * @author JuanJosé
 */
public class Shot extends Base {
    private final int iDireccion;
    private boolean boolActivo;
    public Shot(Image imaSprite,int iDir,int iX, int iY){
        /* Las direcciones que puede tomar un shot son las siguientes
        1 = completamente vertical
        2 = 45° a la derecha
        3 = 45° a la izquierda
        */
        
        super(0,0,imaSprite);
        iDireccion = iDir;
        setX(iX);
        setY(iY);
        boolActivo = true;
    }
    /**
     * Mueve al disparo dependiendo de la direccion que se haya especificado
     */
    public void mover(){
        if(iDireccion == 1){
            int iY = getY();
            setY(iY-3);
        }else if(iDireccion == 2){
            int iX = getX();
            int iY = getY();
            setX(iX+3);
            setY(iY-3);
        }else if(iDireccion == 3){
             int iX = getX();
            int iY = getY();
            setX(iX-3);
            setY(iY-3);
        }
    }
    public void out(){
        boolActivo = false;
    }
    public boolean getActivo(){
        return boolActivo;
    }
}
