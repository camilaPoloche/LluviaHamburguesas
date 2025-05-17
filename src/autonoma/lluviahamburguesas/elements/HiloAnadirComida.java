/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

/**
 *
 * @author maria
 */
public class HiloAnadirComida implements Runnable {
    /**
     * instancia de la clase Cielo
    */
    private Cielo cielo;
    
     /**
     * bandera de control para determinar la ejecucion 
    */
    private boolean running = true;

     /**
    * Inicializa los atributos de la clase HiloAnadirPulgaMutante
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
                Thread.sleep(1500); // Espera 1 segundo antes de revisar de nuevo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
