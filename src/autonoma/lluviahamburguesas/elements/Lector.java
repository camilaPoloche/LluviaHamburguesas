/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import java.io.IOException;

/**
 * Modelo que representa un lector de archivos
 * @author alejandro
 * @since 20250518
 * @version 1.0
 */
public interface Lector {
    /**
     * Metodo que lee un archivo dado su ruta
     * 
     * @param localizacionArchivo ruta del archivo a leer
     * @return
     * @throws IOException si ocurre un error al leer el archivo
     */
    public abstract String leer(String localizacionArchivo) throws IOException;
}
