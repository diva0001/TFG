/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.ArrayList;

/**
 *
 * @author iesdi
 */
public class Hormiga {

    private ArrayList<Integer> solucion;
    private final ArrayList<Integer> disponibles;
    private double coste;

    public Hormiga() {
        this.solucion = new ArrayList();
        this.disponibles = null;
        this.coste = 0;
    }

    public Hormiga(int numCiudades) {
        this.solucion = new ArrayList();
        this.disponibles = new ArrayList();
        for (int i = 0; i < numCiudades; i++) {
            disponibles.add(i);
        }
        this.coste = 0;
    }

    public void setSolucion(ArrayList<Integer> solucion) {
        this.solucion = solucion;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public ArrayList<Integer> getSolucion() {
        return solucion;
    }

    public void addCiudad(int ciudad, int pos) {
        solucion.add(ciudad);
        disponibles.remove(pos);
    }

    public void add(int ciudad) {
        solucion.add(ciudad);
    }

    public int getUltima() {
        return solucion.get(solucion.size() - 1);
    }

    public ArrayList<Integer> getDisponibles() {
        return disponibles;
    }

    public boolean contiene(int origen, int destino) {
        for (int i = 0; i < solucion.size(); i++) {
            if ((solucion.get(i) == origen && solucion.get((i + 1) % solucion.size()) == destino) || (solucion.get(i) == destino && solucion.get((i + 1) % solucion.size()) == origen)) {
                return true;
            }
        }
        return false;
    }

    public String primerasCiudades() {
        String cadena = "";
        for (int i = 0; i < solucion.size() && i < 20; i++) {
            if (i != 19) {
                cadena += solucion.get(i) + 1 + " ";
            } else {
                cadena += solucion.get(i) + 1 + "...";
            }
        }
        return cadena;
    }

    @Override
    public String toString() {
        String cadena = "";
        for (int i = 0; i < solucion.size(); i++) {
            if (i != solucion.size() - 1) {
                cadena += solucion.get(i) + 1 + " ";
            } else {
                cadena += solucion.get(i) + 1;
            }
            if (i != 0 && i % 18 == 0) {
                cadena += "\n";
            }
        }
        return cadena;
    }

}
