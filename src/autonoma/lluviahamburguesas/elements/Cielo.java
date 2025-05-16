/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import autonoma.lluviahamburguesasBase.elements.GraphicContainer;
import autonoma.lluviahamburguesasBase.elements.Sprite;
import autonoma.lluviahamburguesasBase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author maria
 */
public class Cielo extends SpriteContainer implements GraphicContainer {
    /**
     * Instancia de la clase Comida
     */
    private Comida comida;
    
    private boolean acabado = false;

    /**
     * Instancia de la clase Veneno
     */
    private Veneno veneno;

    /**
     * Instancia para escribir archivos de texto plano
     */
    private EscritorArchivoTextoPlano escritor;

    /**
     * Instancia para leer archivos de texto plano
     */
    private LectorArchivoTextoPlano lector;

    /**
     * Puntaje actual del jugador
     */
    private int puntaje;
    
    private static final int MAX_COMIDAS_EN_PANTALLA = 4;
    private static final int MAX_VENENOS_EN_PANTALLA = 4;
    
    private BufferedImage image;
    private BufferedImage buffer;
    private ArrayList<Sprite> spritesEnPantalla = new ArrayList<>();
    private ArrayList<Comida> comidasEnPantalla = new ArrayList<>();
    private ArrayList<Veneno> venenosEnPantalla = new ArrayList<>();


    /**
     * Constructor que inicializa los atributos y configura las pulgas iniciales en la montaña
     * @param x posición en X del contenedor
     * @param y posición en Y del contenedor
     * @param height altura del contenedor
     * @param width ancho del contenedor
     */
    public Cielo(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        comida = new Comida("/autonoma/lluviahamburguesas/images/Hamburguesa.png",(this.width - Comida.INITIAL_WIDTH),
                (this.height - Comida.INITIAL_HEIGHT),
                Comida.INITIAL_WIDTH, Comida.INITIAL_HEIGHT);
        comida.setGraphicContainer(this);

        veneno = new Veneno("/autonoma/lluviahamburguesas/images/Cigarrillo.png",(this.width - Veneno.INITIAL_WIDTH),
                (this.height - Veneno.INITIAL_HEIGHT),
                Veneno.INITIAL_WIDTH, Veneno.INITIAL_HEIGHT);
        veneno.setGraphicContainer(this);

        this.puntaje = 0;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    }
    
    public synchronized void inicializarSpritesDisponibles() {
        sprites.clear();
        for (int i = 0; i < 4; i++) {
            Comida comida = new Comida("/autonoma/lluviahamburguesas/images/Hamburguesa.png", 
                (int)(Math.random() * (this.width - 50)), 0, 50, 50);
            comida.setGraphicContainer(this);
            sprites.add(comida);

            Veneno veneno = new Veneno("/autonoma/lluviahamburguesas/images/Cigarrillo.png", 
                (int)(Math.random() * (this.width - 50)), 0, 50, 50);
            veneno.setGraphicContainer(this);
            sprites.add(veneno);
        }
    }
    
    public synchronized void agregarSpritesAleatorios() {
        while (spritesEnPantalla.size() < 4 && !sprites.isEmpty()) {
            int indice = (int)(Math.random() * sprites.size());
            Sprite sprite = sprites.remove(indice);
            spritesEnPantalla.add(sprite);
        }
        refresh(); 
    }
    
    public synchronized void verificarYAgregarNuevosSprites() {
        ArrayList <Sprite> eliminados = new ArrayList<>();
        for (Sprite s : spritesEnPantalla) {
            if (!sprites.contains(s)) {
                eliminados.add(s);
            }
        }
        
        spritesEnPantalla.removeAll(eliminados);

        if (spritesEnPantalla.size() <= 1) {
            inicializarSpritesDisponibles();
            agregarSpritesAleatorios();
        }
    }
    
    public synchronized void addComida() {
        if (comidasEnPantalla.size() < MAX_COMIDAS_EN_PANTALLA) {
            Comida nuevaComida = new Comida("/autonoma/lluviahamburguesas/images/Hamburguesa.png",
                (int)(Math.random() * (this.width - 50)), 0, 50, 50);
            nuevaComida.setGraphicContainer(this);
            comidasEnPantalla.add(nuevaComida);
            spritesEnPantalla.add(nuevaComida);
            refresh();
        }
    }

    // Este método agrega un veneno si hay menos de 4
    public synchronized void addVeneno() {
        if (venenosEnPantalla.size() < MAX_VENENOS_EN_PANTALLA) {
            Veneno nuevoVeneno = new Veneno("/autonoma/lluviahamburguesas/images/Cigarrillo.png",
                (int)(Math.random() * (this.width - 50)), 0, 50, 50);
            nuevoVeneno.setGraphicContainer(this);
            venenosEnPantalla.add(nuevoVeneno);
            spritesEnPantalla.add(nuevoVeneno);
            refresh();
        }
    }
    
    public synchronized void eliminarSprite(Sprite sprite) {
        spritesEnPantalla.remove(sprite);
        if (sprite instanceof Comida) {
            comidasEnPantalla.remove(sprite);
        } else if (sprite instanceof Veneno) {
            venenosEnPantalla.remove(sprite);
        }
        refresh();
    }
    
    public synchronized void actualizarSprites() {
        for (Sprite sprite : spritesEnPantalla) {
            if (sprite instanceof Comida) {
                ((Comida) sprite).move();
            } else if (sprite instanceof Veneno) {
                ((Veneno) sprite).move();
            }
        }
        refresh();
    }

    /**
     * Elimina pulgas cercanas a una coordenada con el arma Pulguipium
     * @param x posición X del disparo
     * @param y posición Y del disparo
     * @throws IOException
     */
//    public void asesinarComidasPulguipium(int x, int y) throws IOException {
//        ArmaPistolaPulguipium armaPulguipium = new ArmaPistolaPulguipium(this);
//        armaPulguipium.destruirComidas(x, y);
//        refresh();
//    }


    /**
     * Actualiza el puntaje y lo guarda en un archivo
     * @param nuevoPuntaje nuevo valor del puntaje
     * @throws IOException
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;

        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntaje.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Muestra el puntaje actual almacenado en el archivo
     * @return puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntaje.txt");
    }

    /**
     * Dibuja los elementos graficos del juego sobre el panel
     * @param g contexto gráfico
     */
    @Override
    public void paint(Graphics g) {
        Graphics gBuffer = buffer.getGraphics();

        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
        
        gBuffer.setColor(Color.WHITE);
        Font fuente = new Font("Century Gothic", Font.PLAIN, 25);
        gBuffer.setFont(fuente);
        gBuffer.drawString("Puntaje: ", 10, 55);
        gBuffer.drawString(this.puntaje + "", 120, 57);

        ArrayList<Sprite> copiaSprites = new ArrayList<>(sprites);
        for (Sprite sprite : copiaSprites) {
            sprite.paint(gBuffer);
        }

        g.drawImage(buffer, x, y, null);

        gBuffer.dispose();
    }

    /**
     * Refresca la vista del contenedor grafico
     */
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los limites del area del juego
     * @return limites como objeto Rectangle
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }
    

    /**
     * Retorna la lista de sprites actuales
     * @return lista de sprites
     */
    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    /**
     * Retorna el puntaje actual
     * @return puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el puntaje y lo actualiza en el archivo
     * @param puntaje nuevo puntaje
     * @throws IOException
     */
    public void setPuntaje(int puntaje) throws IOException {
        this.puntaje = puntaje;
        this.actualizarPuntaje(puntaje);
    }
    
    /**
     * Reinicia el puntaje y el arreglo de sprites
     * @throws IOException
     */
    public void reiniciarJuego() throws IOException {
        this.puntaje = 0;
        this.sprites.clear(); 
        this.acabado = false;
        this.actualizarPuntaje(0);
        inicializarSpritesDisponibles();
        agregarSpritesAleatorios();
        this.refresh();
    }

    /**
     * Devuelve una copia de los sprites 
     * @return copia de la lista de sprites
    */
    public ArrayList<Sprite> getCopiaSprites() {
        return new ArrayList<>(sprites); 
    }
    
    /**
     * Modifica la bandera acabado
     * @return acabado
    */
    public boolean isAcabado() {
        return acabado;
    }

    /**
     * Modifica la bandera acabado
     * @param acabado
    */
    public void setAcabado(boolean acabado) {
        this.acabado = acabado;
    }
}

