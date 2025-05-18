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

/**
 * Clase que representa un sprite de tipo veneno en el juego.
 * Contiene atributos para posicion, movimiento y gestion grafica.
 * @author Alejandro
 * @since 20250518
 * @version 1.0
 */
public class Veneno extends Sprite {

    /**
     * Ancho inicial del veneno
     */
    public static final int INITIAL_WIDTH = 50;

    /**
     * Alto inicial del veneno
     */
    public static final int INITIAL_HEIGHT = 50;

    /**
     * Imagen asociada al sprite veneno
     */
    private BufferedImage image;

    /**
     * Paso que avanza en cada movimiento
     */
    protected int step = 5;

    /**
     * Velocidad vertical (Y) del movimiento
     */
    private int velocidadY = 0;

    /**
     * Velocidad horizontal (X) del movimiento
     */
    private int velocidadX;

    /**
     * Gravedad que afecta el movimiento vertical para simular caida
     */
    private int gravedad = 1;

    /**
     * Indica si el veneno esta en movimiento o no
     */
    private boolean enMovimiento = false;

    /**
     * Contexto grafico para manipular la imagen (buffer)
     */
    private Graphics g_imagenBuffer;

    /**
     * Constructor que inicializa los atributos de Veneno.
     * Carga la imagen desde la ruta especificada y define posicion y tamaño.
     * @param path ruta de la imagen del veneno
     * @param x posicion en X
     * @param y posicion en Y
     * @param height alto del sprite
     * @param width ancho del sprite
     */
    public Veneno(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g_imagenBuffer = image.getGraphics();
    }

    /**
     * Inicia el movimiento del veneno estableciendo velocidades iniciales.
     */
    public void iniciarMovimiento() {
        if (!enMovimiento) {
            velocidadY = 2; // velocidad inicial hacia abajo
            velocidadX = (int) (Math.random() * 3 - 1); // velocidad horizontal aleatoria entre -1 y 1
            enMovimiento = true;
        }
    }

    /**
     * Actualiza la posicion del veneno segun sus velocidades y aplica
     * gravedad y rebotes en los limites del contenedor grafico.
     */
    public void move() {
        if (enMovimiento) {
            velocidadY += gravedad;
            y += velocidadY;
            x += velocidadX;

            Rectangle limites = gameContainer.getBoundaries();

            // Rebote en limite inferior
            if (y + height >= limites.height) {
                y = limites.height - height;
                velocidadY = -velocidadY;
                enMovimiento = false; // detiene movimiento al tocar suelo
            }

            // Rebote en limite izquierdo
            if (x < 0) {
                x = 0;
                velocidadX = -velocidadX;
            }

            // Rebote en limite derecho
            if (x + width > limites.width) {
                x = limites.width - width;
                velocidadX = -velocidadX;
            }

            // Refresca el contenedor grafico para actualizar la imagen
            if (gameContainer != null) {
                gameContainer.refresh();
            }
        }
    }

    /**
     * Devuelve los limites del sprite como un objeto Rectangle.
     * Util para deteccion de colisiones y limites.
     * @return rectangulo que representa los limites del sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    /**
     * Metodo para pintar el sprite en pantalla.
     * Dibuja la imagen si esta disponible o un rectangulo de color.
     * @param g contexto grafico para dibujar
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Metodo update que actualiza la imagen del sprite.
     * @param g contexto grafico para actualizar
     */
    @Override
    public void update(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
