/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Proyecto;

import Algoritmos.Algoritmo;
import Util.LectorDatos;
import Util.InterfazParametrosManager;
import Util.TipoAlgoritmo;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author iesdi
 */
public class Metaheuristicas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            Interfaz interfaz = new Interfaz();
            interfaz.setVisible(true);
        } else {
            try {
                InterfazParametrosManager config = new InterfazParametrosManager(args[0]);

                ArrayList<LectorDatos> datos = new ArrayList();

                ArrayList<String> archivos = config.getArchivos();

                for (int i = 0; i < archivos.size(); i++) {
                    try {
                        LectorDatos conjuntoDatos = new LectorDatos(archivos.get(i));
                        datos.add(conjuntoDatos);
                    } catch (Exception ex) {
                        System.out.println("El conjunto de datos " + archivos.get(i) + " no tiene un formato válido");
                    }
                }

                ExecutorService ejecutor = Executors.newCachedThreadPool();

                ArrayList<String> algoritmos = config.getAlgoritmos();

                ArrayList<Long> semillas = config.getSemillas();

                TipoAlgoritmo instancia = TipoAlgoritmo.getInstancia();

                CountDownLatch cdl = new CountDownLatch(semillas.size() * archivos.size() * algoritmos.size());

                ArrayList<Algoritmo> hilos = new ArrayList();

                for (int i = 0; i < algoritmos.size(); i++) {
                    try {
                        for (int j = 0; j < semillas.size(); j++) {
                            for (int k = 0; k < datos.size(); k++) {
                                Algoritmo hilo = instancia.getAlgoritmo(algoritmos.get(i));
                                hilo.setParametros(config);
                                hilo.setDatos(datos.get(k));
                                hilo.setSemilla(semillas.get(j));
                                hilo.setCdl(cdl);
                                hilo.inicializarAtributos();
                                hilos.add(hilo);
                                ejecutor.execute(hilo);
                            }
                        }
                    } catch (CloneNotSupportedException ex) {
                    }
                }
                try {
                    cdl.await();
                } catch (InterruptedException ex) {
                }
                for (int i = 0; i < hilos.size(); i++) {
                    Algoritmo hilo = hilos.get(i);
                    guardarArchivo("log/" + hilo.getClass().getName() + "_" + hilos.get(i).getDatos().getRuta() + "_" + hilos.get(i).getSemilla() + ".pdf", hilos.get(i).getLog());
                }
            } catch (IOException ex) {
                System.out.println("El fichero de parámetros " + args[0] + " no tiene un formato válido");
            }
        }

    }

    public static void guardarArchivo(String ruta, String texto) {
        try {
            PdfWriter writer = new PdfWriter(ruta);

            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument);

            document.add(new Paragraph(texto));

            document.close();

        } catch (Exception e) {
        }
    }

}
