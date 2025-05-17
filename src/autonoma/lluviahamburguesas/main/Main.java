/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.main;

import autonoma.lluviahamburguesas.elements.Cielo;
import autonoma.lluviahamburguesas.elements.HiloAnadirComida;
import autonoma.lluviahamburguesas.elements.HiloAnadirVeneno;
import autonoma.lluviahamburguesas.elements.HiloMoverSprites;
import autonoma.lluviahamburguesas.gui.GameWindow;

/**
 *
 * @author maria
 */
public class Main {
    public static void main(String[] args){
        // Se crea una instancia deL cielo
        Cielo cielo = new Cielo("/autonoma/lluviahamburguesas/images/Cielo.png", 0, 0, 480, 480);
        
        // Se crean los hilos que agregan y mueven las pulgas
        Thread hilo1 = new Thread(new HiloAnadirComida(cielo));
        Thread hilo2 = new Thread(new HiloAnadirVeneno(cielo));
        Thread hilo3 = new Thread(new HiloMoverSprites(cielo));
        
        
        // Se crea la ventana grafica del juego
        GameWindow window = new GameWindow(cielo);
        window.setCielo(cielo);
        cielo.setGraphicContainer(window);
        window.setSize(480, 480);
        window.setTitle("   ATRAPA LA COMIDA, CUIDADO CON EL VENENO");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}

