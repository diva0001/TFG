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
public class InterfazParametrosManagerSHMP extends InterfazParametrosManager {

    public InterfazParametrosManagerSHMP(Interfaz interfaz) {
        super(interfaz);
    }

    @Override
    public void leerParametros() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File archivoJSON = new File("parametros/SHMP.json");

            JsonNode parametros = objectMapper.readTree(archivoJSON);

            interfaz.jTextField22setText(parametros.get("Iteraciones").asText());
            numIteraciones = parametros.get("Iteraciones").asInt();
            interfaz.jTextField23setText(parametros.get("Segundos de ejecución").asText());
            ejecucionS = parametros.get("Segundos de ejecución").asInt();
            interfaz.jTextField15setText(parametros.get("Tamaño de la población").asText());
            tamPoblacion = parametros.get("Tamaño de la población").asInt();
            interfaz.jTextField29setText(parametros.get("α").asText());
            alfa = parametros.get("α").asInt();
            interfaz.jTextField34setText(parametros.get("β").asText());
            beta = parametros.get("β").asInt();
            reinicializacion = false;
            interfaz.jTextField31setText(parametros.get("Porcentaje de iteraciones para considerar estancamiento").asText());
            porcentajeIteracionesEstancamiento = parametros.get("Porcentaje de iteraciones para considerar estancamiento").asDouble();
            interfaz.jTextField32setText(parametros.get("Actualización global de feromona").asText());
            actualizacionGlobalFeromona = parametros.get("Actualización global de feromona").asDouble();
            interfaz.jTextField28setText(parametros.get("Feromona inicial").asText());
            valorInicialMatrizFeromona = parametros.get("Feromona inicial").asDouble();
            interfaz.jTextField33setText(parametros.get("Probabilidad de mutación").asText());
            pm = parametros.get("Probabilidad de mutación").asDouble();

        } catch (IOException e) {
        }
    }

    @Override
    public void mostrarParametros() {
        interfaz.mostrarParametrosSHMP();
    }

}
