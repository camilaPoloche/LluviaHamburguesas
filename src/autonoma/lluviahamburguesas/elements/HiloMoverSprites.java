/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import autonoma.lluviahamburguesasBase.elements.Sprite;

/**
 *
 * @author Camila
 */
public class HiloMoverSprites implements Runnable{
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
    public HiloMoverSprites(Cielo cielo) {
        this.cielo = cielo;
    }
    
    /**
    * Sobrescribe el metodo run() de la interfaz Runnable
    */
    @Override
    public void run() {
         while (running) {
            for (Sprite sprite : this.cielo.getCopiaSprites()) {
                if (sprite instanceof Comida) {
                    ((Comida) sprite).move();
                }
                else if (sprite instanceof Veneno) {
                    ((Veneno) sprite).move();
                }
            }
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                
            }
        }
    }
}
