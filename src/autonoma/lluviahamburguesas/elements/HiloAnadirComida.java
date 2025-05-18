/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;
/**
 * Modelo que permite representar el HiloAnadirComida
 * @author Mariana
 * @since 20250517
 * @version 1.0
*/
public class HiloAnadirComida implements Runnable {
    /**
     * Instancia de la clase Cielo
    */
    private Cielo cielo;
    /**
     * Bandera de control para determinar la ejecucion 
    */
    private boolean running = true;

    /**
    * Inicializa los atributos de la clase HiloAnadirComida
    * @param cielo
    */
    public HiloAnadirComida(Cielo cielo) {
        this.cielo = cielo;
    }
    
    /**
    * Sobrescribe el metodo run() de la interfaz Runnable
    */
    @Override
    public void run() {
        while (running) {
            this.cielo.agregarSprites(1, 0);
            try {
                Thread.sleep(1500); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
