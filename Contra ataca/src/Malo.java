/**
 * Malo
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Malo</code>
 *
 * @author José Humberto Guevara
 * @author Juan José López
 * @version 1.0
 * @date 24/feb/2016
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Malo extends Base{
    /**
     * Malo
     * 
     * Metodo constructor usado para crear el objeto Malo
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Malo(int iX, int iY, Image imaSprite)
    {
                
        super(0,0,imaSprite);
        setX(iX);
        setY(iY);
    }

}