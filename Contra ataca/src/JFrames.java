
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author José Humberto Guevara Martínez
 * @author Juan José López Jaimez
 * @version 1.0
 * 
 */
public class JFrames extends JFrame implements Runnable, KeyListener {

    private Base basPrincipal;         // Objeto principal
    private Image imaImagenFondo;        // para dibujar la imagen de fondo
    private Image imaImagenGameOver;        // para dibujar la imagen de game over
    private LinkedList<Base> lklMalitos;       //lista de los malos

    /* objetos para manejar el buffer del Applet y 
       que la imagen no parpadee */
    private Image imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen

    private SoundClip SClipSonidoMalos;  // Objeto SoundClip colision malos

    //private boolean bClicked;           //  Boleana para uso del mouse
    private int iNewX;                  //  Variable para saber la X nueva
    private int iNewY;                   //  Variable para saber la Y nueva

    private int iVidas;                  // Las vidas del personaje.
    private int iScore;                  //mantiene la cuenta del score.
    private int iColMalos;           //mantiene la cuenta de cuantos malos han tocado al personaje.
    private int iVelMalo;                // La velocidad del malo.

    public JFrames() {

        //Define el título de la ventana
        setTitle("It's me Mario!");
        //Define la operación que se llevará acabo cuando la ventana sea cerrada.
        // Al cerrar, el programa terminará su ejecución
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Se manda a llamar el init de variables
        init();
        
        //Se manda a llamar el thread
        start();
    }
    
    
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public void init() {
        //Define el tamaño inicial de la ventana
        setSize(1200, 800);
        
        // se inicializan la velocidad, bCamina y la dirección con un valor default
        iNewX = 0;
        iNewY = 0;

        // se inicializan las vidas con un valor aleatorio entre 3 y 5.
        iVidas = 5;

        //se inicializan el score y el contador de asteroides
        iScore = 0;
        iColMalos = 0;
        
        //se inicializa la velocidad con la que se mueve el malo
        iVelMalo = 1;

        //Creo la lista de los Malos
        lklMalitos = new LinkedList<Base>();

        // Creo la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Fondo.png");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);

        // Creo la imagen de game over.
        URL urlImagenGameOver = this.getClass().getResource("gameover.jpg");
        imaImagenGameOver = Toolkit.getDefaultToolkit().getImage(urlImagenGameOver);

        // defino la imagen principal
        URL urlImagenPrincipal = this.getClass().getResource("mariojump.gif");
        // Creo el objeto para principal 
        basPrincipal = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));

        //Se crea el sonido del choque con el malo.
        SClipSonidoMalos = new SoundClip("Choque.wav");

        //funcion para inicializar los objetos de las listas
        initObjetos();

        //funcion para posicionar inicialmente los personajes.
        posicionInicial();

        // funcion para poder utilizar el teclado.
        addKeyListener(this);
    }

    /**
     * initObjetos
     *
     * creo los objetos de las listas de personajes
     */
    public void initObjetos() {
        //defino la imagen objeto malo
        URL urlImagenMalo = this.getClass().getResource("block.gif");
        //genero aleatoriamente el numero de malos (entre 8 y 10)
        int iRandomMalos = (int) (Math.random() * 3) + 8;
        // Creo los objetos malos
        for (int iI = 0; iI < iRandomMalos; iI++) {
            //creo a cada Malo
            Base basMalo = new Base(0, 0, 
                    Toolkit.getDefaultToolkit().getImage(urlImagenMalo));

            //agrego a cada malo a la lista.
            lklMalitos.add(basMalo);
        }

    }

    /**
     * posicioninicial
     *
     * posiciona a todos los objetos personaje involucrados
     */
    public void posicionInicial() {
        // Posiciono al personaje en el centro.
        iNewX = getWidth() / 2 - basPrincipal.getAncho() / 2;
        iNewY = getHeight() - basPrincipal.getAlto();

        // Se ponen los valores es basPrincipal
        basPrincipal.setX(iNewX);
        basPrincipal.setY(iNewY);

        //variables para las posiciones iniciales de los malos.
        int iPosMaloX, iPosMaloY;
        //posiciono a los malos
        for (Base basMalo : lklMalitos) {
            // Se obtienen valores aleatorios para la X y la Y del asteroide
            iPosMaloX = getWidth() - basMalo.getAncho() - (int)(Math.random()*(getWidth()- basMalo.getAncho()));
            iPosMaloY = 2*(-(int)(Math.random()*(getHeight()- basMalo.getAlto())));

            // Se ponen los valores anteriores en basMalo
            basMalo.setX(iPosMaloX);
            basMalo.setY(iPosMaloY);
        }

    }

    /**
     * reposicionaMalo
     *
     * reposiciona a los malos
     */
    public void reposicionaMalo(Base basMalo) {
        // variables de la posicion del asteroide
        int iPosMaloX, iPosMaloY;

        // Se obtienen valores aleatorios para la X y la Y del asteroide
        iPosMaloX = getWidth() - basMalo.getAncho() - (int)(Math.random()*(getWidth()- basMalo.getAncho()));
        iPosMaloY = 2*(-(int)(Math.random()*(getHeight()- basMalo.getAlto()))); 

        // Se ponen los valores anteriores en basMalo
        basMalo.setX(iPosMaloX);
        basMalo.setY(iPosMaloY);

    }

    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
    
    
    /**
     * run
     *
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones de
     * nuestro juego.
     *
     */
    public void run() {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
         */
        //El juego va a correr mientras el jugador tenga suficientes vidas.
        while (iVidas > 0) {
            actualiza();
            checaColision();
            repaint();
            try {
                // El hilo del juego se duerme.
                Thread.sleep(20);
            } catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego "
                        + iexError.toString());
            }
        }

    }

    /**
     * actualiza
     *
     * Metodo que actualiza la posicion de los objetos
     *
     */
    public void actualiza() {

        //no permito que el principal se pase de las "paredes"
        //reviso que no se salga por la izquierda
        if (iNewX < 0) {
            iNewX = 0;
        }
        //reviso que no se salga por la derecha
        if ((iNewX + basPrincipal.getAncho()) > getWidth()) {
            iNewX = getWidth() - basPrincipal.getAncho();
        }

        // posiciona al personaje dependiendo de la tecla presionada.
        basPrincipal.setX(iNewX);

        // mueve al enemigo hacia abajo de la pantalla
        for(Base basMalo : lklMalitos)
        {
                basMalo.setY(basMalo.getY() + iVelMalo);

        }

    }

    /**
     * checaColision
     *
     * Metodo usado para checar la colision entre objetos
     *
     */
    public void checaColision() {

        //checo la colision entre principal y malo
        for (Base basMalo : lklMalitos) {

            //checo si colisiono el personaje con el malo
            if (basPrincipal.colisiona(basMalo)) {
                //reposiciono al malo que choco contra el personaje.
                reposicionaMalo(basMalo);
                //aumento el contador de malos colisionados.
                iColMalos++;

                //si la cantidad de malos pasa el limite se pierde una vida
                if (iColMalos >= 5) {
                    //redusco una vida del personaje
                    iVidas--;
                    //reseteo el valor del contador de malos para volver a empezar a contar.  
                    iColMalos = 0;
                    //genera un sonido cuando choca con el malo
                    SClipSonidoMalos.play();
                    iVelMalo++;
                }
            }

            //reviso que el malo no se salga por el lado izquierdo del applet
            if (basMalo.getY() >= getHeight()) {
                //reposiciono al malo que se salga de la imagen
                reposicionaMalo(basMalo);
                
                //aumento el contador de malos colisionados.
                iColMalos++;

                //si la cantidad de malos pasa el limite se pierde una vida
                if (iColMalos >= 5) {
                    //redusco una vida del personaje
                    iVidas--;
                    //reseteo el valor del contador de malos para volver a empezar a contar.  
                    iColMalos = 0;
                    //genera un sonido cuando choca con el malo
                    SClipSonidoMalos.play();
                    iVelMalo++;
                }
            }

        

        }
    }

    /**
     * update
     *
     * En este metodo lo que hace es actualizar el contenedor y define cuando
     * usar ahora el paint
     *
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     *
     */
    
    public void paint(Graphics graGrafico) {

        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }
        
        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Fondo.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        graGraficaApplet.drawImage(imaImagenFondo, 0, 0, getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor(getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage(imaImagenApplet, 0, 0, this);

    }

    /**
     * paint
     *
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param graDibujo es el objeto de <code>Graphics</code> usado para
     * dibujar.
     *
     */
    public void paint1(Graphics graDibujo) {
        
        //Dibujo la imagen de game over cuando se acaban las vidas.
        if (iVidas == 0) {
            graDibujo.drawImage(imaImagenGameOver, 0, 0, getWidth(), getHeight(), this);
        }
        else
        {
        // si la imagen ya se cargo
        if (basPrincipal != null && imaImagenFondo != null && lklMalitos != null) {
            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(), getHeight(), this);

            //Dibuja la imagen 
            basPrincipal.paint(graDibujo, this);

            //pinto a los personajes malos
            for (Base basMalo : lklMalitos) {
                basMalo.paint(graDibujo, this);
            }

            //Cambio el tipo de letra, el color y escribo el score del jugador
            graDibujo.setFont(new Font("TimesRoman", Font.BOLD, 30));
            graDibujo.setColor(Color.green);

            //escribo en el applet el numero de vidas.
            graDibujo.drawString("Vidas:", 10, 70);
            graDibujo.drawString(String.valueOf(iVidas), 105, 70);

        } // sino se ha cargado se dibuja un mensaje 
        else {
            //Da un mensaje mientras se carga el dibujo	
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //este metodo no se utiliza
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //switch para mover al el personaje en forma diagonal
        switch (keyEvent.getKeyCode()) {
            //muevo al personaje hacia la izquierda con la flecha izquierda
            case KeyEvent.VK_LEFT:
                iNewX -= 5;
                break;
            //muevo al personaje hacia la derecha con la flecha derecha
            case KeyEvent.VK_RIGHT:
                iNewX += 5;
                break;
            //muevo al personaje hacia la izquierda con la flecha izquierda del teclado numerico
            case KeyEvent.VK_KP_LEFT:
                iNewX -= 5;
                break;
            //muevo al personaje hacia la derecha con la flecha derecha del teclado numerico
            case KeyEvent.VK_KP_RIGHT:
                iNewX += 5;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //este metodo no se utiliza
    }

    public static void main(String[] args) {

        //Crea un nuevo objeto JFrameHolaMundo
        JFrames holaMundo = new JFrames();
        //Despliega la ventana en pantalla al hacerla visible
        holaMundo.setVisible(true);
    }

}
