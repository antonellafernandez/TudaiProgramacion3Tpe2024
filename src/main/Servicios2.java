package main;

import utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Servicios2 {
    private HashMap<String, Tarea> tareas;
    private HashMap<String, Procesador> procesadores;

    /**
     * Complejidad temporal del constructor: O(T + P)
     * donde T es el número de tareas y P es el número de procesadores.
     */
    public Servicios2(String pathTareas, String pathProcesadores) {
        procesadores = new HashMap<>();
        tareas = new HashMap<>();

        // Leer archivos CSV
        CSVReader reader = new CSVReader();

        reader.readProcessors(pathProcesadores, procesadores);
        reader.readTasks(pathTareas, tareas);
    }

    /**
     * <<Breve explicación de la estrategia de resolución>>
     */ /*
    public Solucion backtracking(int tiempoMaximo, int tiempoMaximoNoRefrigerado) {
        Backtracking backtracking = new Backtracking(tiempoMaximo, procesadores);
        ArrayList<Tarea> listaTareas = new ArrayList<>(tareas.values());
        return backtracking.resolver(tiempoMaximoNoRefrigerado, new HashMap<>(procesadores), listaTareas);
    }
*/
    /**
     * <<Breve explicación de la estrategia de resolución>>
     */
    public Solucion greedy(int tiempoMaximoNoRefrigerado) {
        Greedy greedy = new Greedy();

        ArrayList<Tarea> listaTareas = new ArrayList<>(tareas.values());
        ArrayList<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        return greedy.resolver(tiempoMaximoNoRefrigerado, listaTareas, listaProcesadores);
    }
}
