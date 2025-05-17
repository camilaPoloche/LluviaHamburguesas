/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import autonoma.lluviahamburguesasBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 *
 * @author Camila
 */
public class Comida extends Sprite { 
        /**
     * Ancho inicial de la pulga
     */
    public static final int INITIAL_WIDTH = 50;

    /**
     * Alto inicial de la pulga
     */
    public static final int INITIAL_HEIGHT = 50;

    /**
     * Tamaño que crece la pulga en ciertos eventos
     */
    public static final int GROW_SIZE = 4;

    /**
     * Imagen asociada a la pulga
     */
    private BufferedImage image;

    /**
     * Paso que avanza
     */
    protected int step = 5;
    /**
     * Velocidad del movimiento en Y
    */
    private int velocidadY = 0;
    /**
     * Velocidad del movimiento en X
    */
    private int velocidadX;
    /**
     * Gravedad para efecto de caida
    */
    private int gravedad = 1;
    /**
     * Indica si esta en movimiento o no
    */
    private boolean enMovimiento = false;
    
    private Graphics g_imagenBuffer;

    /**
     * Constructor que inicializa los atributos de la pulga
     * @param path ruta de la imagen de la pulga
     * @param x posición en X
     * @param y posición en Y
     * @param height alto del sprite
     * @param width ancho del sprite
     */
    public Comida(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        }catch (IOException e) {
            e.printStackTrace();
        }
        g_imagenBuffer = image.getGraphics();
    }
    
    /**
     * Inicia el movimiento la bola de PingPong
    */
    public void iniciarMovimiento() {
        if (!enMovimiento) {
            velocidadY = 2; 
            velocidadX = (int)(Math.random() * 3 - 1); 
            enMovimiento = true;
        }
    }
    
    /**
     * Mueve la pulga en una dirección aleatoria si no sale del contenedor
     */
    public void move() {
        if (enMovimiento) {
            velocidadY += gravedad;
            y += velocidadY;
            x += velocidadX;

            Rectangle limites = gameContainer.getBoundaries();

            if (y + height >= limites.height) {
                y = limites.height - height;
                velocidadY = 0;
                enMovimiento = false; 
            }

            if (x < 0) {
                x = 0;
                velocidadX = -velocidadX;
            }

            if (x + width > limites.width) {
                x = limites.width - width;
                velocidadX = -velocidadX;
            }
            
            if (gameContainer != null){
                gameContainer.refresh();
            }
        }
    }

    /**
     * Devuelve los limites de la imagen como un rectangulo
     * @return objeto Rectangle con los límites de la imagen
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }   

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }
    
    @Override
    public void update(Graphics g) {
       g.drawImage(image, 0, 0, this);
    }
}
