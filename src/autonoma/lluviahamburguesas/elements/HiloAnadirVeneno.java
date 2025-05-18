/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;
/**
 * Clase que representa un hilo encargado de anadir instancias de veneno al cielo de forma periodica.
 * Implementa la interfaz Runnable para permitir su ejecucion en un hilo separado.
 * @author Alejandro
 * @since 20250517
 * @version 1.0
 */
public class HiloAnadirVeneno implements Runnable {

    /**
     * Instancia del contenedor grafico donde se agregara el veneno.
     */
    private Cielo cielo;

    /**
     * Bandera de control que determina si el hilo debe seguir ejecutandose.
     */
    private boolean running = true;

    /**
     * Constructor que inicializa la clase HiloAnadirVeneno con una instancia de Cielo.
     * @param cielo instancia donde se anadiran los elementos de veneno
     */
    public HiloAnadirVeneno(Cielo cielo) {
        this.cielo = cielo;
    }

    /**
     * Metodo sobrescrito de la interfaz Runnable.
     * Ejecuta en un bucle la accion de anadir veneno al cielo cada 2 segundos.
     */
    @Override
    public void run() {
        while (running) {
            // Agrega un sprite de tipo veneno al cielo
            this.cielo.agregarSprites(0, 1);

            try {
                // Espera 2 segundos antes de volver a agregar otro veneno
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, se detiene adecuadamente
                Thread.currentThread().interrupt();
            }
        }
    }
}
