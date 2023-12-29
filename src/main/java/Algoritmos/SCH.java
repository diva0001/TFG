/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import Util.Hormiga;
import java.util.ArrayList;

/**
 *
 * @author iesdi
 */
public final class SCH extends Algoritmo implements Cloneable {

    private double[][] heuristica;
    private double[][] feromona;
    private double feromonaInicial;
    private int itSolucion;
    private double porcMejorGlobal;

    public SCH() {
        super();
    }

    @Override
    public void inicializarAtributos() {
        this.heuristica = datos.getHeuristica();
        this.feromona = new double[distancias.length][distancias.length];
        this.itSolucion = 0;
        for (int i = 0; i < feromona.length; i++) {
            for (int j = i; j < feromona.length; j++) {
                if (i == j) {
                    this.feromona[i][j] = 0;
                } else {
                    this.feromona[i][j] = this.feromona[j][i] = parametros.getValorInicialMatrizFeromona();
                }
            }
        }
        ArrayList<Integer> greedy = new ArrayList();
        int disponibles[] = new int[distancias.length];
        for (int i = 0; i < distancias.length; i++) {
            disponibles[i] = 1;
        }
        int ciudad = aleatorio.nextInt(distancias.length);
        greedy.add(ciudad);
        disponibles[ciudad] = 0;
        double coste = 0;
        for (int i = 1; i < distancias.length; i++) {
            double menor = distancias[ciudad][0];
            int pos = 0;
            for (int j = 1; j < distancias.length; j++) {
                if (j != i && disponibles[j] == 1 && distancias[ciudad][j] < menor) {
                    menor = distancias[ciudad][j];
                    pos = j;
                }
            }
            coste += distancias[ciudad][pos];
            greedy.add(pos);
            ciudad = pos;
            disponibles[ciudad] = 0;
        }
        coste += distancias[ciudad][greedy.get(0)];
        this.feromonaInicial = 1 / (parametros.getTamPoblacion() * coste);
        this.porcMejorGlobal = parametros.getPorcentajeItMejorGlobal();
    }

    @Override
    public void run() {
     
        log.append("Algoritmo: SCH\n");
        log.append("Conjunto de datos: " + datos.getRutaCompleta() + "\n");
        log.append("Parámetros de configuración utilizados:\n");
        log.append("    -Iteraciones: " + parametros.getNumIteraciones() + "\n");
        log.append("    -Segundos de ejecución: " + parametros.getEjecucionS() + "\n");
        log.append("    -Hormiga que aporta feromona: " + parametros.getHormigaAportante() + "\n");
        if (parametros.getHormigaAportante().equals("Ambas")) {
            log.append("    -% iteraciones aporta gb: " + parametros.getPorcentajeItMejorGlobal() + "\n");
            log.append("    -% reducción iteraciones: " + parametros.getRedPorcItMejorGlobal() + "\n");
        }
        log.append("    -Alfa: " + parametros.getAlfa() + "\n");
        log.append("    -Beta: " + parametros.getBeta() + "\n");
        log.append("    -q0: " + parametros.getQ0() + "\n");
        if (semilla != null) {
            log.append("    -Semilla: " + semilla + "\n");
        } else {
            log.append("    -Semilla: no se ha utilizado\n");
        }
        log.append("    -Actualización global de feromona: " + parametros.getActualizacionGlobalFeromona() + "\n");
        log.append("    -Actualización local de feromona: " + parametros.getActualizacionLocalFeromona() + "\n");
        log.append("    -Feromona inicial: " + parametros.getValorInicialMatrizFeromona() + "\n");
        log.append("    -Tamaño de la población: " + parametros.getTamPoblacion() + "\n");

        long tiempoInicial = System.currentTimeMillis() / 1000;

        long tiempoFinal = System.currentTimeMillis() / 1000;
        
        while (nIteraciones < parametros.getNumIteraciones() && tiempoFinal - tiempoInicial < parametros.getEjecucionS() && !Thread.interrupted()) {
           
            ArrayList<Hormiga> poblacion = inicializar();
            
            construirCamino(poblacion);
            
            Hormiga mejor = evaluar(poblacion);

            actualizarMejorGlobal(mejor);

            actualizacionOffline(mejor);

            if (interfaz != null) {
                interfaz.mostrarSolucion(mejorGlobal);
            }

            nIteraciones++;
           
            itSolucion++;
          
            tiempoFinal = System.currentTimeMillis() / 1000;
        }

        log.append("La mejor solución encontrada es:\n");

        mostrarHormiga(mejorGlobal);

        if (interfaz != null) {
            interfaz.terminadaEjecucion(mejorGlobal);
        }

        log.append("Número de iteraciones realizadas: " + nIteraciones + "\n");

        log.append("Tiempo de ejecución: " + (tiempoFinal - tiempoInicial) + " s\n\n");

        almacenarCiudades();

        cdl.countDown();

    }

    private ArrayList<Hormiga> inicializar() {
        int ciudad = aleatorio.nextInt(distancias.length);

        ArrayList<Hormiga> poblacion = new ArrayList();

        for (int i = 0; i < parametros.getTamPoblacion(); i++) {
            Hormiga hormiga = new Hormiga(distancias.length);
            hormiga.addCiudad(ciudad, ciudad);
            poblacion.add(hormiga);
        }

        return poblacion;
    }

    private void construirCamino(ArrayList<Hormiga> poblacion) {
        for (int i = 1; i < distancias.length; i++) {
            for (int j = 0; j < parametros.getTamPoblacion(); j++) {
                Hormiga hormiga = poblacion.get(j);

                double suma = 0;

                int ultima = hormiga.getUltima();

                ArrayList<Integer> disponibles = hormiga.getDisponibles();

                for (int k = 0; k < disponibles.size(); k++) {
                    int ciudad = disponibles.get(k);
                    suma += Math.pow(feromona[ultima][ciudad], parametros.getAlfa()) * Math.pow(heuristica[ultima][ciudad], parametros.getBeta());
                }

                double mayor = Double.NEGATIVE_INFINITY;

                int ciudad = 0;

                ArrayList<Double> p = new ArrayList();

                double sumaProb = 0;

                int pos = 0;

                double al1 = aleatorio.nextDouble();

                double al2 = aleatorio.nextDouble();

                boolean encontrado = false;

                for (int k = 0; k < disponibles.size() && !encontrado; k++) {
                    int next = disponibles.get(k);

                    double prob = Math.pow(feromona[ultima][next], parametros.getAlfa()) * Math.pow(heuristica[ultima][next], parametros.getBeta()) / suma;

                    sumaProb += prob;

                    p.add(sumaProb);

                    if (al1 >= parametros.getQ0()) {
                        if (prob > mayor) {
                            mayor = prob;
                            ciudad = next;
                            pos = k;
                        }
                    } else {
                        if (al2 <= sumaProb) {
                            pos = k;
                            ciudad = next;
                            encontrado = true;
                        }
                    }
                }

                hormiga.addCiudad(ciudad, pos);

            }

            actualizacionOnline(poblacion);

        }
    }

    private void actualizacionOnline(ArrayList<Hormiga> poblacion) {
        double porc = parametros.getActualizacionLocalFeromona();

        for (int i = 0; i < parametros.getTamPoblacion(); i++) {
            Hormiga hormiga = poblacion.get(i);
            ArrayList<Integer> solucion = hormiga.getSolucion();
            int o = solucion.get(solucion.size() - 2);
            int d = solucion.get(solucion.size() - 1);
            feromona[o][d] = feromona[d][o] = (1 - porc) * feromona[o][d] + porc * feromonaInicial;
        }
    }

    private Hormiga evaluar(ArrayList<Hormiga> poblacion) {
        double menor = Double.POSITIVE_INFINITY;

        int pos = 0;

        for (int i = 0; i < parametros.getTamPoblacion(); i++) {
            Hormiga hormiga = poblacion.get(i);

            ArrayList<Integer> solucion = hormiga.getSolucion();

            double coste = 0;

            for (int j = 0; j < distancias.length; j++) {
                coste += distancias[solucion.get(j)][solucion.get((j + 1) % solucion.size())];
            }

            hormiga.setCoste(coste);

            if (coste < menor) {
                menor = coste;
                pos = i;
            }
        }

        return poblacion.get(pos);
    }

    private void actualizacionOffline(Hormiga mejor) {
        ArrayList<Integer> solucion = mejor.getSolucion();

        Hormiga utilizar = mejor;

        if (parametros.getHormigaAportante().equals("mejor global")) {
            solucion = mejorGlobal.getSolucion();
            utilizar = mejorGlobal;
        } else {
            if (parametros.getHormigaAportante().equals("ambas")) {
                if (itSolucion >= parametros.getNumIteraciones() * porcMejorGlobal) {
                    solucion = mejorGlobal.getSolucion();
                    utilizar = mejorGlobal;
                    porcMejorGlobal -= parametros.getRedPorcItMejorGlobal();
                    if (porcMejorGlobal < 0) {
                        porcMejorGlobal = 0;
                    }
                    itSolucion = -1;
                }
            }
        }

        double porc = parametros.getActualizacionGlobalFeromona();

        for (int i = 0; i < feromona.length; i++) {
            for (int j = i + 1; j < feromona.length; j++) {
                feromona[i][j] = feromona[j][i] = (1 - porc) * feromona[i][j];
            }
        }

        for (int i = 0; i < solucion.size(); i++) {
            int o = solucion.get(i);
            int d = solucion.get((i + 1) % solucion.size());
            feromona[o][d] = feromona[d][o] = feromona[o][d] + porc * (1 / utilizar.getCoste());
        }

    }

    private void actualizarMejorGlobal(Hormiga mejor) {
        if (mejorGlobal == null) {
            mejorGlobal = mejor;
        } else {
            if (mejor.getCoste() < mejorGlobal.getCoste()) {
                mejorGlobal = mejor;
            }
        }
    }

    private void almacenarCiudades() {
        log.append("Información sobre la ubicación de las ciudades:\n");

        double[][] ciudades = datos.getCiudades();

        log.append(ciudades.length + "\n");

        for (int i = 0; i < ciudades.length; i++) {
            log.append(i + 1 + " " + ciudades[i][0] + " " + ciudades[i][1] + "\n");
        }

        log.append("\n");

        for (int i = 0; i < distancias.length; i++) {
            for (int j = 0; j < distancias[i].length; j++) {
                if (i != j) {
                    log.append("Distancia de la ciudad " + (i + 1) + " a la ciudad " + (j + 1) + ": " + distancias[i][j] + "\n");
                }
            }
        }

        log.append("\nMatriz de heurísticas:\n");

        double[][] heuristica = datos.getHeuristica();

        for (int i = 0; i < distancias.length; i++) {
            for (int j = i + 1; j < distancias[i].length; j++) {
                if (i != j) {
                    log.append("Heurística de la ciudad " + (i + 1) + " a la ciudad " + (j + 1) + ": " + heuristica[i][j] + "\n");
                }
            }
        }

        if (interfaz != null) {
            interfaz.setLog(log);
        }

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new SCH();
    }

}
