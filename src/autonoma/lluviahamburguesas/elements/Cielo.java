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

    private int cantidadComida = 0;
    private int cantidadVeneno = 0;
    
    private BufferedImage image;


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
    }
    
    public void agregarSprites(int comidasFaltantes, int venenosFaltantes) {
        for (int i = 0; i < comidasFaltantes; i++) {
            this.sprites.add(this.addComida());
            this.refresh();
        }

        for (int i = 0; i < venenosFaltantes; i++) {
            this.sprites.add(this.addVeneno());
            this.refresh();
        }
    }
    
    public Comida addComida() {
        int w = 50;
        int h = 50;
        boolean chocadas;
        do{
            chocadas = false;
            x = (int) (Math.random() * (this.width - 50));
            y = 60;
            chocadas = false;
            for (Sprite sprite : sprites) {
                int distanciaX = Math.abs(x - sprite.getX());
                int distanciaY = Math.abs(y - sprite.getY());

                if (distanciaX < w && distanciaY < h) {
                    chocadas = true;
                    break;
                }
            }
        }while(chocadas);
        this.cantidadComida ++;
        Comida comida = new Comida("/autonoma/lluviahamburguesas/images/Hamburguesa.png", 
                x, y, 50, 60);
        comida.setGraphicContainer(this);
        comida.iniciarMovimiento(); 
        return comida;
    }

    public Veneno addVeneno() {
        int w = 50;
        int h = 50;
        boolean chocadas;
        do{
            chocadas = false;
            x = (int) (Math.random() * (this.width - 50));
            y = 60;
            chocadas = false;
            for (Sprite sprite : sprites) {
                int distanciaX = Math.abs(x - sprite.getX());
                int distanciaY = Math.abs(y - sprite.getY());

                if (distanciaX < w && distanciaY < h) {
                    chocadas = true;
                    break;
                }
            }
        }while(chocadas);
        
        Veneno veneno = new Veneno("/autonoma/lluviahamburguesas/images/Cigarrillo.png", 
                x, y, 50, 60);
        veneno.setGraphicContainer(this);
        veneno.iniciarMovimiento(); 
        this.cantidadVeneno++;
        return veneno;
    }

    /**
     * Atrapa sprite cercanas a una coordenada con el mouse
     * @param x posición X del disparo
     * @param y posición Y del disparo
     * @throws IOException
     */
    public void atraparSprite(int x, int y) throws IOException {
        int rango = 33;  

        for (int i = 0; i < this.sprites.size(); i++) {
            Sprite p = sprites.get(i);

            int spriteX = p.getX();
            int spriteY = p.getY();
            int puntajeActual = this.getPuntaje();

            if (Math.abs(spriteX - x) < rango && Math.abs(spriteY - y) < rango) {
                if (p instanceof Comida){
                    int puntajeNuevo = puntajeActual += 1;
                    this.setPuntaje(puntajeNuevo);
                }
                else if (p instanceof Veneno){
                    int puntajeNuevo = puntajeActual -= 2;
                    this.setPuntaje (puntajeNuevo);
                }
                
                this.remove(i);
                break;  
            }
        }
        refresh();
    }


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
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
        
        g.setColor(Color.WHITE);
        Font fuente = new Font("Century Gothic", Font.PLAIN, 25);
        g.setFont(fuente);
        g.drawString("Puntaje: ", 10, 55);
        g.drawString(this.puntaje + "", 120, 57);

        ArrayList<Sprite> copiaSprites = new ArrayList<>(sprites);
        for (Sprite sprite : copiaSprites) {
            sprite.paint(g);
        }
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
        this.agregarSprites(4, 4);
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

    public int getCantidadComida() {
        return cantidadComida;
    }

    public void setCantidadComida(int cantidadComida) {
        this.cantidadComida = cantidadComida;
    }

    public int getCantidadVeneno() {
        return cantidadVeneno;
    }

    public void setCantidadVeneno(int cantidadVeneno) {
        this.cantidadVeneno = cantidadVeneno;
    }
}

