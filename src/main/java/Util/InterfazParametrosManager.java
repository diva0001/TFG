package Util;

import Proyecto.Interfaz;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author iesdi
 */
public class InterfazParametrosManager {

    private final ArrayList<String> archivos;
    private final ArrayList<String> algoritmos;
    private final ArrayList<Long> semillas;
    protected int numIteraciones;
    protected int ejecucionS;
    protected int tamPoblacion;
    protected int alfa;
    protected int beta;
    protected double q0;
    protected double actualizacionLocalFeromona;
    protected double actualizacionGlobalFeromona;
    protected double valorInicialMatrizFeromona;
    protected double porcentajeIteracionesEstancamiento;
    protected double pm;
    protected String hormigaAportante;
    protected double porcentajeItMejorGlobal;
    protected double redPorcItMejorGlobal;
    protected int iteracionesConvergencia;
    protected double porcConvergencia;
    protected double coeficienteSuavizado;
    protected double pbest;
    protected boolean reinicializacion;
    protected boolean suavizadoFeromona;
    protected Interfaz interfaz;

    public InterfazParametrosManager(Interfaz interfaz) {
        this.archivos = null;
        this.algoritmos = null;
        this.semillas = null;
        this.interfaz = interfaz;
    }

    public InterfazParametrosManager(String ruta) throws FileNotFoundException, IOException, Exception {
        this.archivos = new ArrayList();
        this.algoritmos = new ArrayList();
        this.semillas = new ArrayList();
        this.interfaz = null;

        String linea;
        FileReader f = null;

        int inf1 = 0;
        int sup1 = 0;

        double inf2 = 0;
        double sup2 = 0;

        f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);

        while ((linea = b.readLine()) != null) {
            String[] inicio = linea.split("=");
            switch (inicio[0]) {
                case "Archivos": {
                    String[] fin = inicio[1].split(" ");
                    for (int i = 0; i < fin.length; i++) {
                        archivos.add(fin[i]);
                    }
                }
                break;
                case "Semillas": {
                    try {
                        String[] fin = inicio[1].split(" ");
                        for (int i = 0; i < fin.length; i++) {
                            semillas.add(Long.parseLong(fin[i]));
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("Las semillas introducidas son incorrectas. Deben ser valores enteros");
                    }
                }
                break;
                case "Algoritmos": {
                    String[] fin = inicio[1].split(" ");
                    for (int i = 0; i < fin.length; i++) {
                        algoritmos.add(fin[i]);
                    }
                }
                break;
                case "Iteraciones": {
                    try {
                        numIteraciones = Integer.parseInt(inicio[1]);
                        if (numIteraciones <= 0) {
                            throw new Exception("El valor del parámetro Iteraciones es incorrecto. Debe ser un valor entero mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Iteraciones es incorrecto. Debe ser un valor entero mayor que 0");
                    }
                }
                break;
                case "Segundos de ejecucion": {
                    try {
                        ejecucionS = Integer.parseInt(inicio[1]);
                        if (ejecucionS <= 0) {
                            throw new Exception("El valor del parámetro Segundos de ejecucion es incorrecto. Debe ser un valor entero mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Segundos de ejecucion es incorrecto. Debe ser un valor entero mayor que 0");
                    }
                }
                break;
                case "Tamanio de la poblacion": {
                    try {
                        tamPoblacion = Integer.parseInt(inicio[1]);
                        if (tamPoblacion <= 0) {
                            throw new Exception("El valor del parámetro Tamanio de la poblacion es incorrecto. Debe ser un valor entero mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Tamanio de la poblacion es incorrecto. Debe ser un valor entero mayor que 0");
                    }
                }
                break;
                case "Alfa": {
                    try {
                        alfa = Integer.parseInt(inicio[1]);
                        if (alfa <= 0) {
                            throw new Exception("El valor del parámetro Alfa es incorrecto. Debe ser un valor real mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Alfa es incorrecto. Debe ser un valor real mayor que 0");
                    }
                }
                break;
                case "Beta": {
                    try {
                        beta = Integer.parseInt(inicio[1]);
                        if (beta <= 0) {
                            throw new Exception("El valor del parámetro Alfa es incorrecto. Debe ser un valor real mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Alfa es incorrecto. Debe ser un valor real mayor que 0");
                    }
                }
                break;
                case "q0": {
                    try {
                        q0 = Double.parseDouble(inicio[1]);
                        if (q0 < 0 || q0 > 1) {
                            throw new Exception("El valor del parámetro q0 es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro q0 es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Actualizacion global de feromona": {
                    try {
                        actualizacionGlobalFeromona = Double.parseDouble(inicio[1]);
                        if (actualizacionGlobalFeromona < 0 || actualizacionGlobalFeromona > 1) {
                            throw new Exception("El valor del parámetro Actualizacion global de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Actualizacion global de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Actualizacion local de feromona": {
                    try {
                        actualizacionLocalFeromona = Double.parseDouble(inicio[1]);
                        if (actualizacionLocalFeromona < 0 || actualizacionLocalFeromona > 1) {
                            throw new Exception("El valor del parámetro Actualizacion local de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Actualizacion local de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Feromona inicial": {
                    try {
                        valorInicialMatrizFeromona = Double.parseDouble(inicio[1]);
                        if (valorInicialMatrizFeromona <= 0) {
                            throw new Exception("El valor del parámetro Feromona inicial es incorrecto. Debe ser un valor real mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Feromona inicial es incorrecto. Debe ser un valor real mayor que 0");
                    }
                }
                break;
                case "Porcentaje de iteraciones para considerar estancamiento": {
                    try {
                        porcentajeIteracionesEstancamiento = Double.parseDouble(inicio[1]);
                        if (porcentajeIteracionesEstancamiento < 0 || porcentajeIteracionesEstancamiento > 1) {
                            throw new Exception("El valor del parámetro Porcentaje de iteraciones para considerar estancamiento es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Porcentaje de iteraciones para considerar estancamiento es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Probabilidad de mutacion": {
                    try {
                        pm = Double.parseDouble(inicio[1]);
                        if (pm < 0 || pm > 1) {
                            throw new Exception("El valor del parámetro Probabilidad de mutacion es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Probabilidad de mutacion es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Hormiga que aporta feromona": {
                    hormigaAportante = inicio[1];
                    if (!hormigaAportante.equals("mejor global") && !hormigaAportante.equals("mejor iteracion") && !hormigaAportante.equals("ambas")) {
                        throw new Exception("El valor del parámetro Hormiga que aporta feromona es incorrecto. Debe especificar mejor global, mejor iteracion o ambas");
                    }
                }
                break;
                case "Porcentaje de iteraciones inicial para usar mejor global": {
                    try {
                        porcentajeItMejorGlobal = Double.parseDouble(inicio[1]);
                        if (porcentajeItMejorGlobal < 0) {
                            throw new Exception("El valor del parámetro Porcentaje de iteracioens inicial para usar mejor global es incorrecto. Debe ser un valor real mayor o igual que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Porcentaje de iteracioens inicial para usar mejor global es incorrecto. Debe ser un valor real mayor o igual que 0");
                    }
                }
                break;
                case "Porcentaje de reduccion de iteraciones": {
                    try {
                        redPorcItMejorGlobal = Double.parseDouble(inicio[1]);
                        if (redPorcItMejorGlobal < 0 || redPorcItMejorGlobal > porcentajeItMejorGlobal) {
                            throw new Exception("El valor del parámetro Porcentaje de reduccion de iteraciones es incorrecto. Debe ser un valor real mayor o igual que 0 y menor o igual que el parámetro Porcentaje de iteraciones inicial para usar mejor global");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Porcentaje de reduccion de iteraciones es incorrecto. Debe ser un valor real mayor o igual que 0 y menor o igual que el parámetro Porcentaje de iteraciones inicial para usar mejor global");
                    }
                }
                break;
                case "Numero de iteraciones para comprobar la convergencia": {
                    try {
                        iteracionesConvergencia = Integer.parseInt(inicio[1]);
                        if (iteracionesConvergencia <= 0) {
                            throw new Exception("El valor del parámetro Numero de iteraciones para comprobar la convergencia es incorrecto. Debe ser un valor entero mayor que 0");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Numero de iteraciones para comprobar la convergencia es incorrecto. Debe ser un valor entero mayor que 0");
                    }
                }
                break;
                case "Factor de convergencia": {
                    try {
                        porcConvergencia = Double.parseDouble(inicio[1]);
                        if (porcConvergencia < 0 || porcConvergencia > 1) {
                            throw new Exception("El valor del parámetro Factor de convergencia es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Factor de convergencia es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Coeficiente de suavizado de feromona": {
                    try {
                        coeficienteSuavizado = Double.parseDouble(inicio[1]);
                        if (coeficienteSuavizado < 0 || coeficienteSuavizado > 1) {
                            throw new Exception("El valor del parámetro Coeficiente de suavizado de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Coeficiente de suavizado de feromona es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Pbest": {
                    try {
                        pbest = Double.parseDouble(inicio[1]);
                        if (pbest < 0 || pbest > 1) {
                            throw new Exception("El valor del parámetro Pbest es incorrecto. Debe ser un valor real entre 0 y 1");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("El valor del parámetro Pbest es incorrecto. Debe ser un valor real entre 0 y 1");
                    }
                }
                break;
                case "Suavizado de feromona": {
                    if (inicio[1].equals("true")) {
                        suavizadoFeromona = true;
                    } else {
                        if (inicio[1].equals("false")) {
                            suavizadoFeromona = false;
                        } else {
                            throw new Exception("El valor del parámetro Suavizado de feromona es incorrecto. Debe ser el valor booleano true o false");
                        }
                    }
                }
                break;
                case "Reinicializacion": {
                    if (inicio[1].equals("true")) {
                        reinicializacion = true;
                    } else {
                        if (inicio[1].equals("false")) {
                            reinicializacion = false;
                        } else {
                            throw new Exception("El valor del parámetro Reinicializacion es incorrecto. Debe ser el valor booleano true o false");
                        }
                    }
                }
                break;
            }
        }
    }

    public void leerParametros() {
    }

    public void mostrarParametros() {
    }

    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    public int getNumIteraciones() {
        return numIteraciones;
    }

    public int getEjecucionS() {
        return ejecucionS;
    }

    public int getTamPoblacion() {
        return tamPoblacion;
    }

    public int getAlfa() {
        return alfa;
    }

    public int getBeta() {
        return beta;
    }

    public double getQ0() {
        return q0;
    }

    public double getActualizacionGlobalFeromona() {
        return actualizacionGlobalFeromona;
    }

    public double getActualizacionLocalFeromona() {
        return actualizacionLocalFeromona;
    }

    public double getValorInicialMatrizFeromona() {
        return valorInicialMatrizFeromona;
    }

    public double getPorcentajeIteracionesEstancamiento() {
        return porcentajeIteracionesEstancamiento;
    }

    public double getPm() {
        return pm;
    }

    public String getHormigaAportante() {
        return hormigaAportante;
    }

    public double getRedPorcItMejorGlobal() {
        return redPorcItMejorGlobal;
    }

    public double getPorcentajeItMejorGlobal() {
        return porcentajeItMejorGlobal;
    }

    public double getPorcConvergencia() {
        return porcConvergencia;
    }

    public int getIteracionesConvergencia() {
        return iteracionesConvergencia;
    }

    public double getCoeficienteSuavizado() {
        return coeficienteSuavizado;
    }

    public double getPbest() {
        return pbest;
    }

    public void setValorInicialMatrizFeromona(double valorInicialMatrizFeromona) {
        this.valorInicialMatrizFeromona = valorInicialMatrizFeromona;
    }

    public void setTamPoblacion(int tamPoblacion) {
        this.tamPoblacion = tamPoblacion;
    }

    public void setRedPorcItMejorGlobal(double redPorcItMejorGlobal) {
        this.redPorcItMejorGlobal = redPorcItMejorGlobal;
    }

    public void setQ0(double q0) {
        this.q0 = q0;
    }

    public void setPorcentajeIteracionesEstancamiento(double porcentajeIteracionesEstancamiento) {
        this.porcentajeIteracionesEstancamiento = porcentajeIteracionesEstancamiento;
    }

    public void setPorcentajeItMejorGlobal(double porcentajeItMejorGlobal) {
        this.porcentajeItMejorGlobal = porcentajeItMejorGlobal;
    }

    public void setPorcConvergencia(double porcConvergencia) {
        this.porcConvergencia = porcConvergencia;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public void setPbest(double pbest) {
        this.pbest = pbest;
    }

    public void setNumIteraciones(int numIteraciones) {
        this.numIteraciones = numIteraciones;
    }

    public void setIteracionesConvergencia(int iteracionesConvergencia) {
        this.iteracionesConvergencia = iteracionesConvergencia;
    }

    public void setHormigaAportante(String hormigaAportante) {
        this.hormigaAportante = hormigaAportante;
    }

    public void setEjecucionS(int ejecucionS) {
        this.ejecucionS = ejecucionS;
    }

    public void setCoeficienteSuavizado(double coeficienteSuavizado) {
        this.coeficienteSuavizado = coeficienteSuavizado;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public void setAlfa(int alfa) {
        this.alfa = alfa;
    }

    public void setActualizacionLocalFeromona(double actualizacionLocalFeromona) {
        this.actualizacionLocalFeromona = actualizacionLocalFeromona;
    }

    public void setActualizacionGlobalFeromona(double actualizacionGlobalFeromona) {
        this.actualizacionGlobalFeromona = actualizacionGlobalFeromona;
    }

    public boolean isReinicializacion() {
        return reinicializacion;
    }

    public void setSuavizadoFeromona(boolean suavizadoFeromona) {
        this.suavizadoFeromona = suavizadoFeromona;
    }

    public void setReinicializacion(boolean reinicializacion) {
        this.reinicializacion = reinicializacion;
    }

    public boolean isSuavizadoFeromona() {
        return suavizadoFeromona;
    }

}
