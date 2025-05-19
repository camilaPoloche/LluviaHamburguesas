/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import autonoma.lluviahamburguesasBase.elements.Sprite;

/**
 * Clase que representa un hilo encargado de mover los sprites de comida y veneno dentro del cielo.
 * Implementa la interfaz Runnable para su ejecucion en un hilo separado.
 * @author Alejandro
 * @since 20250517
 * @version 1.0
 */
public class HiloMoverSprites implements Runnable {

    /**
     * Instancia del contenedor grafico donde se encuentran los sprites (Cielo).
     */
    private Cielo cielo;

    /**
     * Bandera de control que determina si el hilo debe seguir ejecutandose.
     */
    private boolean running = true;

    /**
     * Constructor que inicializa la clase HiloMoverSprites con una instancia de Cielo.
     * @param cielo instancia del contenedor grafico donde se moveran los sprites
     */
    public HiloMoverSprites(Cielo cielo) {
        this.cielo = cielo;
    }

    /**
     * Metodo sobrescrito de la interfaz Runnable.
     * Recorre constantemente los sprites del cielo y ejecuta su metodo de movimiento,
     * diferenciando entre instancias de comida y veneno.
     */
    @Override
    public void run() {
        while (running) {
            // Itera sobre una copia de los sprites para evitar errores de concurrencia
            for (Sprite sprite : this.cielo.getCopiaSprites()) {
                if (sprite instanceof Comida) {
                    ((Comida) sprite).move();
                } else if (sprite instanceof Veneno) {
                    ((Veneno) sprite).move();
                }
            }
            try {
                // Espera medio segundo antes de volver a mover los sprites
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
