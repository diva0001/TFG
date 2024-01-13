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
public final class SHMM extends Algoritmo implements Cloneable {

    private double[][] heuristica;
    private double[][] feromona;
    private double fmin;
    private double fmax;
    private int itSolucion;
    private int estancamiento;
    private double porcMejorGlobal;

    public SHMM() {
        super();
    }

    @Override
    public void inicializarAtributos() {
        this.heuristica = datos.getHeuristica();
        this.feromona = new double[distancias.length][distancias.length];
        this.itSolucion = 0;
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
        this.fmax = (1 / 1 - parametros.getActualizacionGlobalFeromona()) * (1 / coste);
        this.fmin = (fmax * (1 - Math.pow(parametros.getPbest(), 1.0 / distancias.length))) / (((distancias.length / 2) - 1) * Math.pow(parametros.getPbest(), 1.0 / distancias.length));
        for (int i = 0; i < feromona.length; i++) {
            for (int j = i; j < feromona.length; j++) {
                if (i == j) {
                    this.feromona[i][j] = 0;
                } else {
                    this.feromona[i][j] = this.feromona[j][i] = this.fmax;
                }
            }
        }
        this.estancamiento = 0;
        this.porcMejorGlobal = parametros.getPorcentajeItMejorGlobal();
    }

    @Override
    public void run() {

        log.append("Algoritmo: SHMM\n");
        log.append("Conjunto de datos: " + datos.getRutaCompleta() + "\n");
        log.append("Parámetros de configuración utilizados:\n");
        log.append("    -Iteraciones: " + parametros.getNumIteraciones() + "\n");
        log.append("    -Segundos de ejecución: " + parametros.getEjecucionS() + "\n");
        log.append("    -Pbest: " + parametros.getPbest() + "\n");
        log.append("    -Hormiga que aporta feromona: " + parametros.getHormigaAportante() + "\n");
        if (parametros.getHormigaAportante().equals("ambas")) {
            log.append("    -% iteraciones aporta gb: " + parametros.getPorcentajeItMejorGlobal() + "\n");
            log.append("    -% reducción iteraciones: " + parametros.getRedPorcItMejorGlobal() + "\n");
        }
        log.append("    -Alfa: " + parametros.getAlfa() + "\n");
        log.append("    -Beta: " + parametros.getBeta() + "\n");
        if (semilla != null) {
            log.append("    -Semilla: " + semilla + "\n");
        } else {
            log.append("    -Semilla: no se ha utilizado\n");
        }
        if (parametros.isReinicializacion()) {
            log.append("    -Reinicialización cuando se produzca estancamiento: si\n");
            log.append("    -Porcentaje de soluciones sin mejora para considerar un estancamiento: " + parametros.getPorcentajeIteracionesEstancamiento() + "\n");
        } else {
            log.append("    -Reinicialización cuando se produzca estancamiento: no\n");
        }
        log.append("    -Actualización de feromona: " + parametros.getActualizacionGlobalFeromona() + "\n");
        log.append("    -Tamaño de la población: " + parametros.getTamPoblacion() + "\n");
        if (parametros.isSuavizadoFeromona()) {
            log.append("    -Suavizado de feromona: si\n");
            log.append("    -Factor de convergencia: " + parametros.getPorcConvergencia() + "\n");
            log.append("    -Coeficiente de suavizado: " + parametros.getCoeficienteSuavizado() + "\n");
            log.append("    -Iteraciones para comprobar convergencia: " + parametros.getIteracionesConvergencia() + "\n");
        } else {
            log.append("    -Suavizado de feromona: no\n");
        }

        long tiempoInicial = System.currentTimeMillis() / 1000;

        long tiempoFinal = System.currentTimeMillis() / 1000;

        while (nIteraciones < parametros.getNumIteraciones() && tiempoFinal - tiempoInicial < parametros.getEjecucionS() && !Thread.interrupted()) {

            ArrayList<Hormiga> poblacion = inicializar();

            construirCamino(poblacion);

            Hormiga mejor = evaluar(poblacion);

            actualizarMejorGlobal(mejor);

            actualizarFeromona(mejor);

            if (parametros.isReinicializacion()) {
                comprobarEstancamiento();
            }

            nIteraciones++;

            itSolucion++;

            if (parametros.isSuavizadoFeromona()) {
                comprobarConvergencia();
            }

            if (interfaz != null) {
                interfaz.mostrarSolucion(mejorGlobal);
            }

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

                int ciudad = 0;

                ArrayList<Double> p = new ArrayList();

                double sumaProb = 0;

                int pos = 0;

                double al = aleatorio.nextDouble();

                boolean encontrado = false;

                for (int k = 0; k < disponibles.size() && !encontrado; k++) {
                    int next = disponibles.get(k);

                    double prob = Math.pow(feromona[ultima][next], parametros.getAlfa()) * Math.pow(heuristica[ultima][next], parametros.getBeta()) / suma;

                    sumaProb += prob;

                    p.add(sumaProb);

                    if (al <= sumaProb) {
                        pos = k;
                        ciudad = next;
                        encontrado = true;
                    }
                }

                hormiga.addCiudad(ciudad, pos);

            }

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

    private void actualizarFeromona(Hormiga mejor) {
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
            feromona[o][d] = feromona[d][o] = feromona[o][d] + (1 / utilizar.getCoste());
            if (feromona[o][d] < fmin) {
                feromona[o][d] = feromona[d][o] = fmin;
            }
            if (feromona[o][d] > fmax) {
                feromona[o][d] = feromona[d][o] = fmax;
            }
        }

    }

    private void actualizarMejorGlobal(Hormiga mejor) {
        if (mejorGlobal == null) {
            mejorGlobal = mejor;
        } else {
            if (mejor.getCoste() < mejorGlobal.getCoste()) {
                mejorGlobal = mejor;
                fmax = (1 / 1 - parametros.getActualizacionGlobalFeromona()) * (1 / mejorGlobal.getCoste());
                fmin = (fmax * (1 - Math.pow(parametros.getPbest(), 1.0 / distancias.length))) / (((distancias.length / 2) - 1) * Math.pow(parametros.getPbest(), 1.0 / distancias.length));
                estancamiento = 0;
            } else {
                estancamiento++;
            }
        }
    }

    private void comprobarEstancamiento() {
        if (estancamiento >= parametros.getPorcentajeIteracionesEstancamiento() * parametros.getNumIteraciones()) {
            estancamiento = 0;
            for (int i = 0; i < feromona.length; i++) {
                for (int j = i; j < feromona.length; j++) {
                    if (i != j) {
                        this.feromona[i][j] = this.feromona[j][i] = this.fmax;
                    }
                }
            }
        }
    }

    private void comprobarConvergencia() {
        if (nIteraciones % parametros.getIteracionesConvergencia() == 0) {
            ArrayList<Integer> solucion = mejorGlobal.getSolucion();
            double sup = fmax - (fmax * parametros.getPorcConvergencia());
            double inf = fmin + (fmin * parametros.getPorcConvergencia());
            boolean salir = false;
            for (int i = 0; i < feromona.length && !salir; i++) {
                for (int j = i; j < feromona.length && !salir; j++) {
                    if ((mejorGlobal.contiene(i, j) && feromona[i][j] < sup) || (!mejorGlobal.contiene(i, j) && feromona[i][j] > inf)) {
                        salir = true;
                    }
                }
            }
            if (!salir) {
                for (int i = 0; i < feromona.length; i++) {
                    for (int j = i; j < feromona.length; j++) {
                        feromona[i][j] = feromona[j][i] = feromona[i][j] + parametros.getCoeficienteSuavizado() * (fmax - feromona[i][j]);
                    }
                }
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
        return new SHMM();
    }

}
