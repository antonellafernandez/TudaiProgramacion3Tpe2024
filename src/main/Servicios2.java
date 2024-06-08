package main;

import utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * Método para resolver el problema utilizando el algoritmo de backtracking.
     *
     * La estrategia utilizada en este Backtracking es explorar recursivamente todas las posibles asignaciones
     * de tareas a procesadores, evaluando en cada paso si una tarea puede ser asignada a un procesador sin
     * incumplir ciertas restricciones.
     *
     * Complejidad temporal: en el peor de los casos O(m^n)
     * donde m es el número de procesadores y n es el número de tareas.
     */
    public Solucion backtracking(int tiempoMaximoNoRefrigerado) {
        Backtracking backtracking = new Backtracking();

        List<Tarea> listaTareas = new ArrayList<>(tareas.values());
        List<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        return backtracking.resolver(tiempoMaximoNoRefrigerado, listaProcesadores, listaTareas);
    }

    /**
     * Método para resolver el problema utilizando el algoritmo de greedy.
     *
     * La estrategia utilizada en este Greedy es seleccionar en cada iteración de la lista de tareas el procesador que
     * tenga el menor tiempo acumulado de ejecución hasta el momento, y asignar la tarea a ese procesador si cumple
     * con ciertas condiciones.
     *
     * Complejidad temporal: O(n * m)
     * donde n es el número de tareas y m es el número de procesadores.
     */
    public Solucion greedy(int tiempoMaximoNoRefrigerado) {
        Greedy greedy = new Greedy();

        List<Tarea> listaTareas = new ArrayList<>(tareas.values());
        List<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        return greedy.resolver(tiempoMaximoNoRefrigerado, listaTareas, listaProcesadores);
    }
}
