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
public class InterfazParametrosManagerSHMM extends InterfazParametrosManager {

    public InterfazParametrosManagerSHMM(Interfaz interfaz) {
        super(interfaz);
    }

    @Override
    public void leerParametros() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File archivoJSON = new File("parametros/SHMM.json");

            JsonNode parametros = objectMapper.readTree(archivoJSON);

            interfaz.jTextField12setText(parametros.get("Iteraciones").asText());
            numIteraciones = parametros.get("Iteraciones").asInt();
            interfaz.jTextField13setText(parametros.get("Segundos de ejecución").asText());
            ejecucionS = parametros.get("Segundos de ejecución").asInt();
            interfaz.jTextField24setText(parametros.get("Pbest").asText());
            pbest = parametros.get("Pbest").asDouble();
            hormigaAportante = parametros.get("Hormiga que aporta feromona").asText();
            interfaz.jTextField39setText(parametros.get("Porcentaje de reducción de iteraciones").asText());
            redPorcItMejorGlobal = parametros.get("Porcentaje de reducción de iteraciones").asDouble();
            interfaz.jTextField14setText(parametros.get("Porcentaje de iteraciones inicial para usar mejor global").asText());
            porcentajeItMejorGlobal = parametros.get("Porcentaje de iteraciones inicial para usar mejor global").asDouble();
            interfaz.jTextField16setText(parametros.get("α").asText());
            alfa = parametros.get("α").asInt();
            interfaz.jTextField17setText(parametros.get("β").asText());
            beta = parametros.get("β").asInt();
            reinicializacion = false;
            suavizadoFeromona = false;
            interfaz.jTextField18setText(parametros.get("Porcentaje de iteraciones para considerar estancamiento").asText());
            porcentajeIteracionesEstancamiento = parametros.get("Porcentaje de iteraciones para considerar estancamiento").asDouble();
            interfaz.jTextField20setText(parametros.get("Actualización global de feromona").asText());
            actualizacionGlobalFeromona = parametros.get("Actualización global de feromona").asDouble();
            interfaz.jTextField25setText(parametros.get("Tamaño de la población").asText());
            tamPoblacion = parametros.get("Tamaño de la población").asInt();
            interfaz.jTextField26setText(parametros.get("Factor de convergencia").asText());
            porcConvergencia = parametros.get("Factor de convergencia").asDouble();
            interfaz.jTextField27setText(parametros.get("Coeficiente de suavizado de feromona").asText());
            coeficienteSuavizado = parametros.get("Coeficiente de suavizado de feromona").asDouble();
            interfaz.jTextField21setText(parametros.get("Número de iteraciones para comprobar la convergecia").asText());
            iteracionesConvergencia = parametros.get("Número de iteraciones para comprobar la convergecia").asInt();

        } catch (IOException e) {
        }
    }

    @Override
    public void mostrarParametros() {
        interfaz.mostrarParametrosSHMM();
    }

}
