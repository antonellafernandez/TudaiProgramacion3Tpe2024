package main;

import utils.CSVReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Servicios2 {
    private HashMap<String, Tarea> tareas;
    private HashMap<String, Procesador> procesadores;

    /**
     * Complejidad temporal del constructor: O(T + P)
     * donde T es el número de tareas y P es el número de procesadores.
     */
    public Servicios2(String pathProcesadores, String pathTareas) {
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
     * La estrategia utilizada en este Backtracking comienza con una solución inicial vacía y utiliza recursión para explorar todas las posibles combinaciones de asignaciones de tareas.
     * En cada paso de la recursión, intenta asignar una tarea a un procesador si cumple con no superar el tiempo máximo
     * no refrigerado del mismo y que este no supere la ejecución de dos tareas críticas.
     * Luego evalúa si esta asignación mejora la solución actual en términos del tiempo máximo de ejecución.
     * Si es así, actualiza la mejor solución encontrada.
     * Si no puede mejorar la solución actual, realiza backtracking para deshacer esa asignación y probar con otras combinaciones.
     * El proceso continúa hasta que se agotan las tareas por asignar.
     * Finalmente, retorna la mejor solución encontrada.
     *
     * Complejidad temporal: en el peor de los casos O(m^n)
     * donde m es el número de procesadores y n es el número de tareas.
     */
    public void backtracking(int tiempoMaximoNoRefrigerado) {
        Stack<Tarea> pilaTareas = new Stack<>();
        List<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        pilaTareas.addAll(tareas.values());

        Backtracking backtracking = new Backtracking(listaProcesadores);
        Solucion mejorSolucionB = backtracking.resolver(tiempoMaximoNoRefrigerado, listaProcesadores, pilaTareas);

        if (mejorSolucionB != null ) {
            System.out.println(mejorSolucionB);
        } else {
            System.out.println("Tiempo máximo de ejecución: 0. No se encontró solución!");
        }

        System.out.print("Métrica para analizar el costo de la solución (cantidad de estados generados): ");
        System.out.println(backtracking.getCantidadEstadosGenerados());
    }

    /**
     * Método para resolver el problema utilizando el algoritmo de greedy.
     *
     * La estrategia utilizada en este Greedy comienza ordenando todas las tareas por su tiempo de ejecución en orden ascendente.
     * Luego, itera sobre estas tareas y para cada una, intenta asignarla al procesador que actualmente tiene el menor tiempo
     * acumulado de ejecución y que cumple con los requisitos de la tarea en cuanto a tiempo máximo no refrigerado
     * y que no ejecute más de dos tareas críticas.
     * Si encuentra un procesador adecuado, asigna la tarea a ese procesador y la elimina de la lista de tareas disponibles.
     * Este proceso se repite hasta que no queden más tareas por asignar.
     *
     * Complejidad temporal: O(n * m)
     * donde n es el número de tareas y m es el número de procesadores.
     */
    public void greedy(int tiempoMaximoNoRefrigerado) {
        List<Tarea> listaTareas = new ArrayList<>(tareas.values());
        List<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());

        Greedy greedy = new Greedy(listaProcesadores);
        Solucion mejorSolucionG = greedy.resolver(tiempoMaximoNoRefrigerado, listaTareas);

        if (mejorSolucionG != null) {
            System.out.println(mejorSolucionG);
        } else {
            System.out.println("Tiempo máximo de ejecución: 0. No se encontró solución!");
        }

        System.out.print("Métrica para analizar el costo de la solución (cantidad de candidatos considerados): ");
        System.out.println(greedy.getCantidadCandidatosConsiderados());
    }
}
