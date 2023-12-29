/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Algoritmos.Algoritmo;
import Algoritmos.LinKerninghan;
import Algoritmos.SCH;
import Algoritmos.SHMM;
import Algoritmos.SHMP;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author iesdi
 */
public class TipoAlgoritmo {

    private static TipoAlgoritmo instancia;
    private final Map<String, Algoritmo> algoritmos;

    private TipoAlgoritmo() {
        instancia = null;
        algoritmos = new HashMap();
        algoritmos.put("SCH", new SCH());
        algoritmos.put("SHMM", new SHMM());
        algoritmos.put("SHMP", new SHMP());
        algoritmos.put("Lin-Kerninghan", new LinKerninghan());
    }

    public static TipoAlgoritmo getInstancia() {
        if (instancia == null) {
            instancia = new TipoAlgoritmo();
        }
        return instancia;
    }

    public Algoritmo getAlgoritmo(String algoritmo) throws CloneNotSupportedException {
        return (Algoritmo) algoritmos.get(algoritmo).clone();
    }

}
