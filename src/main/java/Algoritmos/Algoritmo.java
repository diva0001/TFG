/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import Util.Hormiga;
import Proyecto.Interfaz;
import Util.LectorDatos;
import Util.InterfazParametrosManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author iesdi
 */
public class Algoritmo implements Runnable, Cloneable {

    protected Random aleatorio;
    protected InterfazParametrosManager parametros;
    protected LectorDatos datos;
    protected Long semilla;
    protected StringBuilder log;
    protected CountDownLatch cdl;
    protected Hormiga mejorGlobal;
    protected double[][] distancias;
    protected Interfaz interfaz;
    protected int nIteraciones;

    public Algoritmo() {
        this.aleatorio = null;
        this.parametros = null;
        this.datos = null;
        this.semilla = null;
        this.log = new StringBuilder();
        this.cdl = null;
        this.mejorGlobal = null;
        this.distancias = null;
        this.interfaz = null;
        this.nIteraciones = 0;
    }

    @Override
    public void run() {
    }

    public void inicializarAtributos() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Algoritmo();
    }

    public String getLog() {
        return log.toString();
    }

    public Long getSemilla() {
        return semilla;
    }

    public LectorDatos getDatos() {
        return datos;
    }

    public void setCdl(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    public void setDatos(LectorDatos datos) {
        this.datos = datos;
        this.distancias = datos.getDistancias();
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public void setParametros(InterfazParametrosManager parametros) {
        this.parametros = parametros;
    }

    public void setSemilla(Long semilla) {
        this.semilla = semilla;
        if (semilla != null) {
            aleatorio = new Random(semilla);
        } else {
            aleatorio = new Random();
        }
    }

    protected void mostrarHormiga(Hormiga hormiga) {
        ArrayList<Integer> solucion = (ArrayList<Integer>) hormiga.getSolucion().clone();
        for(int i=0;i<solucion.size();i++){
            solucion.set(i, solucion.get(i) + 1);
        }
        log.append(solucion.toString() + "\n");
        log.append("Coste: " + hormiga.getCoste() + "\n");
    }

}
