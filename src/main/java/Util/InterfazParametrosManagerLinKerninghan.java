/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Proyecto.Interfaz;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author iesdi
 */
public class InterfazParametrosManagerLinKerninghan extends InterfazParametrosManager {

    public InterfazParametrosManagerLinKerninghan(Interfaz interfaz) {
        super(interfaz);
    }

    @Override
    public void leerParametros() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File archivoJSON = new File("parametros/Lin-Kerninghan.json");

            JsonNode parametros = objectMapper.readTree(archivoJSON);

            interfaz.jTextField36setText(parametros.get("Iteraciones").asText());
            numIteraciones = parametros.get("Iteraciones").asInt();
            interfaz.jTextField37setText(parametros.get("Segundos de ejecución").asText());
            ejecucionS = parametros.get("Segundos de ejecución").asInt();

        } catch (IOException e) {
        }
    }

    @Override
    public void mostrarParametros() {
        interfaz.mostrarParametrosLinKerninghan();
    }

}
