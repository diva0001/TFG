/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author iesdi
 */
public class LectorDatos {

    private final String ruta;
    private final String rutaCompleta;
    private double ciudades[][];
    private final double distancias[][];
    private final double heuristica[][];

    public LectorDatos() {
        ruta = null;
        rutaCompleta = null;
        ciudades = null;
        distancias = null;
        heuristica = null;
    }

    public LectorDatos(String ruta) throws Exception {
        this.ruta = ruta.split("\\.")[0];
        this.rutaCompleta = ruta;

        String linea = null;
        FileReader f = null;

        f = new FileReader(ruta);

        BufferedReader b = new BufferedReader(f);

        linea = b.readLine();

        while (!linea.split(":")[0].equals("DIMENSION")) {
            linea = b.readLine();
        }

        int tam = Integer.parseInt(linea.split(":")[1].replace(" ", ""));

        ciudades = new double[tam][2];

        linea = b.readLine();

        while (!linea.equals("NODE_COORD_SECTION")) {
            linea = b.readLine();
        }

        linea = b.readLine();

        while (!linea.equals("EOF")) {
            int i = 0;
            String[] split = linea.split(" ");
            ciudades[Integer.parseInt(split[0]) - 1][i++] = Double.parseDouble(split[1]);
            ciudades[Integer.parseInt(split[0]) - 1][i] = Double.parseDouble(split[2]);
            linea = b.readLine();
        }

        distancias = new double[tam][tam];

        heuristica = new double[tam][tam];

        for (int i = 0; i < tam; i++) {
            for (int j = i; j < tam; j++) {
                if (i == j) {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                    heuristica[i][j] = 0;
                } else {
                    distancias[i][j] = distancias[j][i] = Math.sqrt(Math.pow(ciudades[i][0] - ciudades[j][0], 2) + Math.pow(ciudades[i][1] - ciudades[j][1], 2));
                    heuristica[i][j] = heuristica[j][i] = 1 / distancias[i][j];
                }
            }
        }

    }

    public String getRuta() {
        return ruta;
    }

    public double[][] getCiudades() {
        return ciudades;
    }

    public double[][] getDistancias() {
        return distancias;
    }

    public double[][] getHeuristica() {
        return heuristica;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }

    public void setCiudades(double[][] ciudades) {
        this.ciudades = ciudades;
    }

}
