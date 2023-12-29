/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import Util.Eje;
import Util.Hormiga;
import java.util.ArrayList;

/**
 *
 * @author iesdi
 */
public final class LinKerninghan extends Algoritmo implements Cloneable {

    public int[] solucionActual;

    public LinKerninghan() {
        super();
    }

    @Override
    public void inicializarAtributos() {
        this.solucionActual = solucionAleatoria();
    }

    /**
     * This function create a random solucionActual using the dunken sailor
     * algorithm
     *
     * @param None
     * @return ArrayList<Integer> array with the list of nodes in the
     * solucionActual (sorted)
     */
    private int[] solucionAleatoria() {
        int[] array = new int[distancias.length];
        for (int i = 0; i < distancias.length; i++) {
            array[i] = i;
        }

        for (int i = 0; i < distancias.length; ++i) {
            int indice = aleatorio.nextInt(i + 1);
            // Simple swap
            int a = array[indice];
            array[indice] = array[i];
            array[i] = a;
        }

        return array;
    }

    @Override
    public void run() {

        log.append("Algoritmo: Lin-Kerninghan\n");
        log.append("Conjunto de datos: " + datos.getRutaCompleta() + "\n");
        log.append("Parámetros de configuración utilizados:\n");
        log.append("    -Iteraciones: " + parametros.getNumIteraciones() + "\n");
        log.append("    -Segundos de ejecución: " + parametros.getEjecucionS() + "\n");
        if (semilla != null) {
            log.append("    -Semilla: " + semilla + "\n");
        } else {
            log.append("    -Semilla: no se ha utilizado\n");
        }

        long tiempoInicial = System.currentTimeMillis() / 1000;

        long tiempoFinal = System.currentTimeMillis() / 1000;

        double antiguaDistancia = 0;
        double nuevaDistancia = getDistancia();

        actualizarMejorGlobal(nuevaDistancia);

        do {
            antiguaDistancia = nuevaDistancia;
            mejorar();
            nuevaDistancia = getDistancia();
            if (nuevaDistancia < antiguaDistancia) {
                actualizarMejorGlobal(nuevaDistancia);
            }
            if (interfaz != null) {
                interfaz.mostrarSolucion(mejorGlobal);
            }
            nIteraciones++;
            tiempoFinal = System.currentTimeMillis() / 1000;
        } while (nuevaDistancia < antiguaDistancia && nIteraciones < parametros.getNumIteraciones() && tiempoFinal - tiempoInicial < parametros.getEjecucionS() && !Thread.interrupted());

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

        if (interfaz != null) {
            interfaz.setLog(log);
        }

    }

    /**
     * This function returns the current solucionActual distancia
     *
     * @param Nothing
     * @return double the distancia of the solucionActual
     */
    private double getDistancia() {
        double suma = 0;

        for (int i = 0; i < distancias.length; i++) {
            int a = solucionActual[i];                  // <->
            int b = solucionActual[(i + 1) % distancias.length];    // <->
            suma += this.distancias[a][b];
        }

        return suma;
    }

    /**
     * This function tries to mejorar the solucionActual
     *
     * @param None
     * @return void
     */
    private void mejorar() {
        for (int i = 0; i < distancias.length; ++i) {
            mejorar(i);
        }
    }

    /**
     * This functions tries to mejorar by stating from a particular node
     *
     * @param x the reference to the city to start with.
     * @return void
     */
    private void mejorar(int x) {
        mejorar(x, false);
    }

    /**
     * This functions attempts to mejorar the solucionActual by stating from a
     * particular node
     *
     * @param t1 the reference to the city to start with.
     * @return void
     */
    private void mejorar(int t1, boolean anterior) {
        int t2 = anterior ? getIndiceAnterior(t1) : getIndiceSiguiente(t1);
        int t3 = getVecinoMasCercano(t2);

        if (t3 != -1 && getDistancia(t2, t3) < getDistancia(t1, t2)) { // Implementing the gain criteria
            empezarAlgoritmo(t1, t2, t3);
        } else if (!anterior) {
            mejorar(t1, true);
        }
    }

    /**
     * This function returns the previous indice for the solucionActual, this
     * typically should be x-1 but if x is zero, well, it is the last indice.
     *
     * @param x the indice of the node
     * @return the previous indice
     */
    private int getIndiceAnterior(int indice) {
        return indice == 0 ? distancias.length - 1 : indice - 1;
    }

    /**
     * This function returns the next indice for the solucionActual, this
     * typically should be x+1 but if x is the last indice it should wrap to
     * zero
     *
     * @param x the indice of the node
     * @return the next indice
     */
    private int getIndiceSiguiente(int indice) {
        return (indice + 1) % distancias.length;
    }

    /**
     * This function returns the nearest neighbor for an specific node
     *
     * @param the indice of the node
     * @return the indice of the nearest node
     */
    private int getVecinoMasCercano(int indice) {
        double minimaDistancia = Double.MAX_VALUE;
        int nodoMasCercano = -1;
        int nodoActual = solucionActual[indice];
        for (int i = 0; i < distancias.length; ++i) {
            if (i != nodoActual) {
                double distancia = distancias[i][nodoActual];
                if (distancia < minimaDistancia) {
                    nodoMasCercano = getIndice(i);
                    minimaDistancia = distancia;
                }
            }
        }
        return nodoMasCercano;
    }

    /**
     * This functions retrieves the distancia between two nodes given its
     * indexes
     *
     * @param int indice of the first node
     * @param int indice of the second node
     * @return double the distancia from node 1 to node 2
     */
    private double getDistancia(int n1, int n2) {
        return distancias[solucionActual[n1]][solucionActual[n2]];
    }

    /**
     * This function is actually the step four from the lin-kernighan's original
     * paper
     *
     * @param t1 the indice that references the chosen t1 in the solucionActual
     * @param t2 the indice that references the chosen t2 in the solucionActual
     * @param t3 the indice that references the chosen t3 in the solucionActual
     * @return void
     */
    private void empezarAlgoritmo(int t1, int t2, int t3) {
        ArrayList<Integer> tIndex = new ArrayList();
        tIndex.add(0, -1); // Start with the indice 1 to be consistent with Lin-Kernighan Paper
        tIndex.add(1, t1);
        tIndex.add(2, t2);
        tIndex.add(3, t3);
        double gananciaInicial = getDistancia(t2, t1) - getDistancia(t3, t2); // |x1| - |y1|
        double GStar = 0;
        double Gi = gananciaInicial;
        int k = 3;
        for (int i = 4;; i += 2) {
            int newT = selectNewT(tIndex);
            if (newT == -1) {
                break; // This should not happen according to the paper
            }
            tIndex.add(i, newT);
            int tiplus1 = getNextPossibleY(tIndex);
            if (tiplus1 == -1) {
                break;
            }

            // Step 4.f from the paper
            Gi += getDistancia(tIndex.get(tIndex.size() - 2), newT);
            if (Gi - getDistancia(newT, t1) > GStar) {
                GStar = Gi - getDistancia(newT, t1);
                k = i;
            }

            tIndex.add(tiplus1);
            Gi -= getDistancia(newT, tiplus1);

        }
        if (GStar > 0) {
            tIndex.set(k + 1, tIndex.get(1));
            solucionActual = getTPrime(tIndex, k); // Update the solucionActual
        }

    }

    /**
     * This function gets all the ys that fit the criterion for step 4
     *
     * @param tIndex the list of t's
     * @return an array with all the possible y's
     */
    private int getNextPossibleY(ArrayList<Integer> tIndex) {
        int ti = tIndex.get(tIndex.size() - 1);
        ArrayList<Integer> ys = new ArrayList<Integer>();
        for (int i = 0; i < distancias.length; ++i) {
            if (!isDisjunctive(tIndex, i, ti)) {
                continue; // Disjunctive criteria
            }

            if (!isPositiveGain(tIndex, i)) {
                continue; // Gain criteria
            }
            if (!nextXPossible(tIndex, i)) {
                continue; // Step 4.f.
            }
            ys.add(i);
        }

        // Get closest y
        double minDistance = Double.MAX_VALUE;
        int minNode = -1;
        for (int i : ys) {
            if (getDistancia(ti, i) < minDistance) {
                minNode = i;
                minDistance = getDistancia(ti, i);
            }
        }

        return minNode;

    }

    /**
     * This function implements the part e from the point 4 of the paper
     *
     * @param tIndex
     * @param i
     * @return
     */
    private boolean nextXPossible(ArrayList<Integer> tIndex, int i) {
        return isConnected(tIndex, i, getIndiceSiguiente(i)) || isConnected(tIndex, i, getIndiceAnterior(i));
    }

    private boolean isConnected(ArrayList<Integer> tIndex, int x, int y) {
        if (x == y) {
            return false;
        }
        for (int i = 1; i < tIndex.size() - 1; i += 2) {
            if (tIndex.get(i) == x && tIndex.get(i + 1) == y) {
                return false;
            }
            if (tIndex.get(i) == y && tIndex.get(i + 1) == x) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param tIndex
     * @param i
     * @return true if the gain would be positive
     */
    private boolean isPositiveGain(ArrayList<Integer> tIndex, int ti) {
        int gain = 0;
        for (int i = 1; i < tIndex.size() - 2; ++i) {
            int t1 = tIndex.get(i);
            int t2 = tIndex.get(i + 1);
            int t3 = i == tIndex.size() - 3 ? ti : tIndex.get(i + 2);

            gain += getDistancia(t2, t3) - getDistancia(t1, t2); // |yi| - |xi|

        }
        return gain > 0;
    }

    /**
     * This function gets a new t with the characteristics described in the
     * paper in step 4.a.
     *
     * @param tIndex
     * @return
     */
    private int selectNewT(ArrayList<Integer> tIndex) {
        int option1 = getIndiceAnterior(tIndex.get(tIndex.size() - 1));
        int option2 = getIndiceSiguiente(tIndex.get(tIndex.size() - 1));

        int[] tour1 = constructNewTour(solucionActual, tIndex, option1);

        if (isTour(tour1)) {
            return option1;
        } else {
            int[] tour2 = constructNewTour(solucionActual, tIndex, option2);
            if (isTour(tour2)) {
                return option2;
            }
        }
        return -1;
    }

    private int[] constructNewTour(int[] tour2, ArrayList<Integer> tIndex, int newItem) {
        ArrayList<Integer> changes = new ArrayList(tIndex);

        changes.add(newItem);
        changes.add(changes.get(1));
        return constructNewTour(tour2, changes);
    }

    /**
     * This function validates whether a sequence of numbers constitutes a
     * solucionActual
     *
     * @param tour an array with the node numbers
     * @return boolean true or false
     */
    private boolean isTour(int[] tour) {
        if (tour.length != distancias.length) {
            return false;
        }

        for (int i = 0; i < distancias.length - 1; ++i) {
            for (int j = i + 1; j < distancias.length; ++j) {
                if (tour[i] == tour[j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Construct T prime
     */
    private int[] getTPrime(ArrayList<Integer> tIndex, int k) {
        ArrayList<Integer> al2 = new ArrayList<Integer>(tIndex.subList(0, k + 2));
        return constructNewTour(solucionActual, al2);
    }

    /**
     * This function constructs a new Tour deleting the X sets and adding the Y
     * sets
     *
     * @param tour The current solucionActual
     * @param changes the list of t's to derive the X and Y sets
     * @return an array with the node numbers
     */
    private int[] constructNewTour(int[] tour, ArrayList<Integer> changes) {
        ArrayList<Eje> currentEdges = deriveEdgesFromTour(tour);

        ArrayList<Eje> X = deriveX(changes);
        ArrayList<Eje> Y = deriveY(changes);
        int s = currentEdges.size();

        // Remove Xs
        for (Eje e : X) {
            for (int j = 0; j < currentEdges.size(); ++j) {
                Eje m = currentEdges.get(j);
                if (e.equals(m)) {
                    s--;
                    currentEdges.set(j, null);
                    break;
                }
            }
        }

        // Add Ys
        for (Eje e : Y) {
            s++;
            currentEdges.add(e);
        }

        return createTourFromEdges(currentEdges, s);

    }

    /**
     * This function takes a list of edges and converts it into a solucionActual
     *
     * @param currentEdges The list of edges to convert
     * @return the array representing the solucionActual
     */
    private int[] createTourFromEdges(ArrayList<Eje> currentEdges, int s) {
        int[] tour = new int[s];

        int i = 0;
        int last = -1;

        for (; i < currentEdges.size(); ++i) {
            if (currentEdges.get(i) != null) {
                tour[0] = currentEdges.get(i).get1();
                tour[1] = currentEdges.get(i).get2();
                last = tour[1];
                break;
            }
        }

        currentEdges.set(i, null); // remove the edges

        int k = 2;
        while (true) {
            // E = find()
            int j = 0;
            for (; j < currentEdges.size(); ++j) {
                Eje e = currentEdges.get(j);
                if (e != null && e.get1() == last) {
                    last = e.get2();
                    break;
                } else if (e != null && e.get2() == last) {
                    last = e.get1();
                    break;
                }
            }
            // If the list is empty
            if (j == currentEdges.size()) {
                break;
            }

            // Remove new edge
            currentEdges.set(j, null);
            if (k >= s) {
                break;
            }
            tour[k] = last;
            k++;
        }

        return tour;
    }

    /**
     * Get the list of edges from the t indice
     *
     * @param changes the list of changes proposed to the solucionActual
     * @return The list of edges that will be deleted
     */
    private ArrayList<Eje> deriveX(ArrayList<Integer> changes) {
        ArrayList<Eje> es = new ArrayList();
        for (int i = 1; i < changes.size() - 2; i += 2) {
            Eje e = new Eje(solucionActual[changes.get(i)], solucionActual[changes.get(i + 1)]);
            es.add(e);
        }
        return es;
    }

    /**
     * Get the list of edges from the t indice
     *
     * @param changes the list of changes proposed to the solucionActual
     * @return The list of edges that will be added
     */
    private ArrayList<Eje> deriveY(ArrayList<Integer> changes) {
        ArrayList<Eje> es = new ArrayList();
        for (int i = 2; i < changes.size() - 1; i += 2) {
            Eje e = new Eje(solucionActual[changes.get(i)], solucionActual[changes.get(i + 1)]);
            es.add(e);
        }
        return es;
    }

    /**
     * Get the list of edges from the solucionActual, it is basically a
     * conversion from a solucionActual to an edge list
     *
     * @param tour the array representing the solucionActual
     * @return The list of edges on the solucionActual
     */
    private ArrayList<Eje> deriveEdgesFromTour(int[] tour) {
        ArrayList<Eje> es = new ArrayList();
        for (int i = 0; i < tour.length; ++i) {
            Eje e = new Eje(tour[i], tour[(i + 1) % tour.length]);
            es.add(e);
        }

        return es;
    }

    /**
     * This function allows to check if an edge is already on either X or Y
     * (disjunctivity criteria)
     *
     * @param tIndex the indice of the nodes in the solucionActual
     * @param x the indice of one of the endpoints
     * @param y the indice of one of the endpoints
     * @return true when it satisfy the criteria, false otherwise
     */
    private boolean isDisjunctive(ArrayList<Integer> tIndex, int x, int y) {
        if (x == y) {
            return false;
        }
        for (int i = 0; i < tIndex.size() - 1; i++) {
            if (tIndex.get(i) == x && tIndex.get(i + 1) == y) {
                return false;
            }
            if (tIndex.get(i) == y && tIndex.get(i + 1) == x) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function gets the indice of the node given the actual number of the
     * node in the solucionActual
     *
     * @param the node id
     * @return the indice on the solucionActual
     */
    private int getIndice(int node) {
        int i = 0;
        for (int t : solucionActual) {
            if (node == t) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * This function returns a string with the current solucionActual and its
     * distancia
     *
     * @param None
     * @return String with the representation of the solucionActual
     */
    public String toString() {
        String str = "[" + this.getDistancia() + "] : ";
        boolean add = false;
        for (int city : this.solucionActual) {
            if (add) {
                str += " => " + city;
            } else {
                str += city;
                add = true;
            }
        }
        return str;
    }

    private void actualizarMejorGlobal(Double coste) {
        mejorGlobal = new Hormiga();
        for (int i = 0; i < distancias.length; i++) {
            mejorGlobal.add(solucionActual[i]);
        }
        mejorGlobal.setCoste(coste);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new LinKerninghan();
    }

}
