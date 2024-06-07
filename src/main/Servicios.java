package main;

import utils.CSVReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "main.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    private HashMap<String, Tarea> tareas;
    private HashMap<String, Procesador> procesadores;

    // Listas para almacenar las tareas por su tipo de criticidad
    private List<Tarea> tareasFalse;
    private List<Tarea> tareasTrue;

    // Árbol de búsqueda binaria para almacenar tareas por prioridad
    private BinarySearchTreeTarea tareaBST;

    /**
     * Complejidad temporal del constructor: O(T + P)
     * donde T es el número de tareas y P es el número de procesadores.
     */
    public Servicios(String pathTareas, String pathProcesadores) {
        procesadores = new HashMap<>();
        tareas = new HashMap<>();

        tareasFalse = new LinkedList<>();
        tareasTrue = new LinkedList<>();

        tareaBST = new BinarySearchTreeTarea();

        // Leer archivos CSV
        CSVReader reader = new CSVReader();

        reader.readProcessors(pathProcesadores, procesadores);
        reader.readTasks(pathTareas, tareas);

        // Almacenar tareas por criticidad
        for (Tarea tarea : tareas.values()) {
            if (tarea.getCritica()) {
                tareasTrue.add(tarea);
            } else {
                tareasFalse.add(tarea);
            }
        }

        // Insertar tareas en el árbol
        for (Tarea tarea : tareas.values()) {
            tareaBST.insert(tarea);
        }
    }

    /**
     * Complejidad temporal del servicio 1: O(1)
     */
    public Tarea servicio1(String ID) {
        return tareas.get(ID);
    }

    /**
     * Complejidad temporal del servicio 2: O(1)
     *
     * Guardamos todas las tareas TRUE en una estructura y las FALSE en otra,
     * aumentando la memoria para poder bajar la complejidad, aunque sabemos que
     * después tendremos mayor gasto de mantenimiento, porque al hacer un DELETE
     * por ejemplo, tendremos que sacarlos del Map y de la Lista.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        if(esCritica == true) {
            return tareasTrue;
        } else {
            return tareasFalse;
        }
    }

    /**
     * Complejidad temporal del servicio 3: O(N)
     * donde N es el número de nodos del árbol.
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return tareaBST.getTareasEnRango(prioridadInferior, prioridadSuperior);
    }
}
