/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.lluviahamburguesas.elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Modelo que permite representar la lectura de un archivo de texto plano
 * @author alejandro
 * @since 20250517
 * @version 1.0
 */
public class LectorArchivoTextoPlano implements Lector{
    
    /**
     * Contiene el contenido del archivo leido
     */
    private String archivo;
    
    /**
     * Charset utilizado para la lectura del archivo
     */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * Constructor por defecto
     */
    public LectorArchivoTextoPlano(){
    }
    
    /**
     * Lee el contenido de un archivo de texto dado su ruta
     * 
     * @param localizacionArchivo ruta del archivo a leer
     * @return cadena con el contenido del archivo
     * @throws IOException si ocurre un error al abrir o leer el archivo
     */
    public String leer(String localizacionArchivo) throws IOException {
        File file = new File(localizacionArchivo);
        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);

        StringBuilder contenido = new StringBuilder();
        String linea;
        
        // Lee linea por linea hasta el final del archivo
        while ((linea = buffer.readLine()) != null) {
            contenido.append(linea);
        }

        // Cierra los streams abiertos
        buffer.close();
        reader.close();

        // Guarda el contenido leido en el atributo archivo
        this.archivo = contenido.toString().trim();
        
        return this.archivo;
    }

    /**
     * Metodo para obtener la configuracion de pulgas almacenada
     * 
     * @return cadena con la configuracion de pulgas (contenido leido)
     */
    public String getConfiguracionPulgas() {
        return archivo;
    }
}
