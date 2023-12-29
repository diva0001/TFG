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

    public InterfazParametrosManager(String ruta) throws FileNotFoundException, IOException {
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
                    String[] fin = inicio[1].split(" ");
                    for (int i = 0; i < fin.length; i++) {
                        semillas.add(Long.parseLong(fin[i]));
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
                    numIteraciones = Integer.parseInt(inicio[1]);
                }
                break;
                case "Segundos de ejecucion": {
                    ejecucionS = Integer.parseInt(inicio[1]);
                }
                break;
                case "Tamanio de la poblacion": {
                    tamPoblacion = Integer.parseInt(inicio[1]);
                }
                break;
                case "Alfa": {
                    alfa = Integer.parseInt(inicio[1]);
                }
                break;
                case "Beta": {
                    beta = Integer.parseInt(inicio[1]);
                }
                break;
                case "q0": {
                    q0 = Double.parseDouble(inicio[1]);
                }
                break;
                case "Actualizacion global de feromona": {
                    actualizacionGlobalFeromona = Double.parseDouble(inicio[1]);
                }
                break;
                case "Actualizacion local de feromona": {
                    actualizacionLocalFeromona = Double.parseDouble(inicio[1]);
                }
                break;
                case "Feromona inicial": {
                    valorInicialMatrizFeromona = Double.parseDouble(inicio[1]);
                }
                break;
                case "Porcentaje de iteraciones para considerar estancamiento": {
                    porcentajeIteracionesEstancamiento = Double.parseDouble(inicio[1]);
                }
                break;
                case "Probabilidad de mutacion": {
                    pm = Double.parseDouble(inicio[1]);
                }
                break;
                case "Hormiga que aporta feromona": {
                    hormigaAportante = inicio[1];
                }
                break;
                case "Porcentaje de iteraciones inicial para usar mejor global": {
                    porcentajeItMejorGlobal = Double.parseDouble(inicio[1]);
                }
                break;
                case "Porcentaje de reduccion de iteraciones": {
                    redPorcItMejorGlobal = Double.parseDouble(inicio[1]);
                }
                break;
                case "Numero de iteraciones para comprobar la convergencia": {
                    iteracionesConvergencia = Integer.parseInt(inicio[1]);
                }
                break;
                case "Factor de convergencia": {
                    porcConvergencia = Double.parseDouble(inicio[1]);
                }
                break;
                case "Coeficiente de suavizado de feromona": {
                    coeficienteSuavizado = Double.parseDouble(inicio[1]);
                }
                break;
                case "Pbest": {
                    pbest = Double.parseDouble(inicio[1]);
                }
                break;
                case "Suavizado de feromona": {
                    suavizadoFeromona = Boolean.parseBoolean(inicio[1]);
                }
                break;
                case "Reinicializacion": {
                    reinicializacion = Boolean.parseBoolean(inicio[1]);
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
