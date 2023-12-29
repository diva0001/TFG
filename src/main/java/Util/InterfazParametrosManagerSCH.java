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
public class InterfazParametrosManagerSCH extends InterfazParametrosManager {

    public InterfazParametrosManagerSCH(Interfaz interfaz) {
        super(interfaz);
    }

    @Override
    public void leerParametros() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File archivoJSON = new File("parametros/SCH.json");

            JsonNode parametros = objectMapper.readTree(archivoJSON);

            interfaz.jTextField2setText(parametros.get("Iteraciones").asText());
            numIteraciones = parametros.get("Iteraciones").asInt();
            interfaz.jTextField3setText(parametros.get("Segundos de ejecución").asText());
            ejecucionS = parametros.get("Segundos de ejecución").asInt();
            interfaz.jTextField4setText(parametros.get("Tamaño de la población").asText());
            tamPoblacion = parametros.get("Tamaño de la población").asInt();
            interfaz.jTextField5setText(parametros.get("α").asText());
            alfa = parametros.get("α").asInt();
            interfaz.jTextField6setText(parametros.get("β").asText());
            beta = parametros.get("β").asInt();
            interfaz.jTextField7setText(parametros.get("q0").asText());
            q0 = parametros.get("q0").asDouble();
            interfaz.jTextField8setText(parametros.get("Actualización global de feromona").asText());
            actualizacionGlobalFeromona = parametros.get("Actualización global de feromona").asDouble();
            interfaz.jTextField9setText(parametros.get("Actualización local de feromona").asText());
            actualizacionLocalFeromona = parametros.get("Actualización local de feromona").asDouble();
            interfaz.jTextField1setText(parametros.get("Feromona inicial").asText());
            valorInicialMatrizFeromona = parametros.get("Feromona inicial").asDouble();
            interfaz.jTextField35setText(parametros.get("Porcentaje de reducción de iteraciones").asText());
            redPorcItMejorGlobal = parametros.get("Porcentaje de reducción de iteraciones").asDouble();
            hormigaAportante = parametros.get("Hormiga que aporta feromona").asText();
            interfaz.jTextField11setText(parametros.get("Porcentaje de iteraciones inicial para usar mejor global").asText());
            porcentajeItMejorGlobal = parametros.get("Porcentaje de iteraciones inicial para usar mejor global").asDouble();

        } catch (IOException e) {
        }
    }

    @Override
    public void mostrarParametros() {
        interfaz.mostrarParametrosSCH();
    }

}
