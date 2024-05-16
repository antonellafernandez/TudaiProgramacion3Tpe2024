package main;

import utils.CSVReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "main.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    private Map<String, Tarea> tareas;
    private Map<String, Procesador> procesadores;

    /**
     * Complejidad temporal del constructor: O(T + P)
     * donde T es el número de tareas y P es el número de procesadores.
     */
    public Servicios(String pathTareas, String pathProcesadores) {
        procesadores = new HashMap<>();
        tareas = new HashMap<>();
        
        CSVReader reader = new CSVReader();

        reader.readProcessors(pathProcesadores, procesadores);
        reader.readTasks(pathTareas, tareas);
    }

    /**
     * Complejidad temporal del servicio 1: O(1)
     */
    public Tarea servicio1(String ID) {
        return tareas.get(ID);
    }

    /**
     * Complejidad temporal del servicio 2: O(N)
     * donde N es el número de tareas
     */
    public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> salida = new LinkedList<>();

        for (Tarea tarea : tareas.values()) {
            if (tarea.getCritica() == esCritica) {
                salida.add(tarea);
            }
        }

        return salida;
    }

    /**
     * Complejidad temporal del servicio 3: O(N)
     * donde N es el número de tareas
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> salida = new LinkedList<>();

        for (Tarea tarea : tareas.values()) {
            if ( (tarea.getPrioridad() >= prioridadInferior) && (tarea.getPrioridad() <= prioridadSuperior) ) {
                salida.add(tarea);
            }
        }

        return salida;
    }
}