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
public final class SHMP extends Algoritmo implements Cloneable {

    private Hormiga mejor;
    private Hormiga peor;
    private double[][] heuristica;
    private double[][] feromona;
    private double feromonaInicial;
    private int estancamiento;

    public SHMP() {
        super();
    }

    @Override
    public void inicializarAtributos() {
        this.mejor = null;
        this.peor = null;
        this.heuristica = datos.getHeuristica();
        this.feromona = new double[distancias.length][distancias.length];
        this.estancamiento = 0;
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
    }

    @Override
    public void run() {

        log.append("Algoritmo: SHMP\n");
        log.append("Conjunto de datos: " + datos.getRutaCompleta() + "\n");
        log.append("Parámetros de configuración utilizados:\n");
        log.append("    -Iteraciones: " + parametros.getNumIteraciones() + "\n");
        log.append("    -Segundos de ejecución: " + parametros.getEjecucionS() + "\n");
        log.append("    -Tamaño de la población: " + parametros.getTamPoblacion() + "\n");
        log.append("    -Alfa: " + parametros.getAlfa() + "\n");
        log.append("    -Beta: " + parametros.getBeta() + "\n");
        if (semilla != null) {
            log.append("    -Semilla: " + semilla + "\n");
        } else {
            log.append("    -Semilla: no se ha utilizado\n");
        }
        log.append("    -Feromona inicial: " + parametros.getValorInicialMatrizFeromona() + "\n");
        log.append("    -Actualización de feromona: " + parametros.getActualizacionGlobalFeromona() + "\n");
        log.append("    -Probabilidad de mutación: " + parametros.getPm() + "\n");
        if (parametros.isReinicializacion()) {
            log.append("    -Reinicialización cuando se produzca estancamiento: si\n");
            log.append("    -Porcentaje de soluciones sin mejora para considerar un estancamiento: " + parametros.getPorcentajeIteracionesEstancamiento() + "\n");
        } else {
            log.append("    -Reinicialización cuando se produzca estancamiento: no\n");
        }
      
        long tiempoInicial = System.currentTimeMillis() / 1000;

        long tiempoFinal = System.currentTimeMillis() / 1000;

        while (nIteraciones < parametros.getNumIteraciones() && tiempoFinal - tiempoInicial < parametros.getEjecucionS() && !Thread.interrupted()) {
       
            ArrayList<Hormiga> poblacion = inicializar();

            construirCamino(poblacion);

            evaluar(poblacion);

            actualizarMejorGlobal();

            actualizarFeromona();

            mutacion();

            if (parametros.isReinicializacion()) {
                comprobarEstancamiento();
            }

            if (interfaz != null) {
                interfaz.mostrarSolucion(mejorGlobal);
            }

            nIteraciones++;

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

    private void actualizarFeromona() {
        ArrayList<Integer> mejorSolGlobal = mejorGlobal.getSolucion();
        ArrayList<Integer> peorSolucion = peor.getSolucion();

        double porc = parametros.getActualizacionGlobalFeromona();

        for (int i = 0; i < feromona.length; i++) {
            for (int j = i + 1; j < feromona.length; j++) {
                feromona[i][j] = feromona[j][i] = (1 - porc) * feromona[i][j];
            }
        }

        for (int i = 0; i < mejorSolGlobal.size(); i++) {
            int o = mejorSolGlobal.get(i);
            int d = mejorSolGlobal.get((i + 1) % mejorSolGlobal.size());
            feromona[o][d] = feromona[d][o] = feromona[o][d] + (1 / mejorGlobal.getCoste());
            o = peorSolucion.get(i);
            d = peorSolucion.get((i + 1) % peorSolucion.size());
            if (!mejorGlobal.contiene(o, d)) {
                feromona[o][d] = feromona[d][o] = (1 - porc) * feromona[o][d];
            }
        }

    }

    private void evaluar(ArrayList<Hormiga> poblacion) {
        double menor = Double.POSITIVE_INFINITY;
        double mayor = Double.NEGATIVE_INFINITY;

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
                mejor = hormiga;
            }

            if (coste > mayor) {
                mayor = coste;
                peor = hormiga;
            }
        }

    }

    private void actualizarMejorGlobal() {
        if (mejorGlobal == null) {
            mejorGlobal = mejor;
        } else {
            if (mejor.getCoste() < mejorGlobal.getCoste()) {
                mejorGlobal = mejor;
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
                        this.feromona[i][j] = this.feromona[j][i] = this.feromonaInicial;
                    }
                }
            }
        }
    }

    private double generaAleatorioRealEnRango(double min, double max) {
        return aleatorio.nextDouble() * (max - min) + min;
    }

    private void mutacion() {

        ArrayList<Integer> mejorSol = mejorGlobal.getSolucion();

        double coste = 0;

        for (int i = 0; i < mejorSol.size(); i++) {
            coste += feromona[mejorSol.get(i)][mejorSol.get((i + 1) % mejorSol.size())];
        }

        double umbral = coste / mejorSol.size();

        for (int i = 0; i < feromona.length; i++) {
            for (int j = i; j < feromona.length; j++) {
                if (i != j && aleatorio.nextDouble() < parametros.getPm()) {
                    feromona[i][j] = feromona[j][i] = feromona[i][j] + generaAleatorioRealEnRango(-umbral, umbral);
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
        return new SHMP();
    }

}
