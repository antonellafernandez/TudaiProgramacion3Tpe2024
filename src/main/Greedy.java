package main;

import java.util.Collections;
import java.util.List;

public class Greedy {
    private Solucion mejorSolucion;
    private int cantidadCandidatosConsiderados;

    public Greedy(List<Procesador>procesadores) {
        this.mejorSolucion = new Solucion(procesadores);
        cantidadCandidatosConsiderados = 0;
    }

    public int getCantidadCandidatosConsiderados() {
        return cantidadCandidatosConsiderados;
    }

    /**
     * Complejidad temporal de resolver: O(n * m)
     * donde n es el número de tareas y m es el número de procesadores.
     */
    public Solucion resolver(int tiempoMaximoNoRefrigerado, List<Tarea> listaTareas) {
        Collections.sort(listaTareas); // Se ordenan las tareas por tiempo de ejecución ascendente

        while (!listaTareas.isEmpty()) { // Si tareas esta vacia, significa que las asignó todas
            Tarea mejorTarea = listaTareas.get(0);
            Procesador procesadorOptimo = this.getProcesadorOptimo(mejorTarea,tiempoMaximoNoRefrigerado);

            if (procesadorOptimo != null) {
                this.mejorSolucion.asignarTarea(procesadorOptimo, mejorTarea);
                listaTareas.remove(mejorTarea);
            } else {
                return null; // No hay una asignación de tareas completa
            }

            this.cantidadCandidatosConsiderados++;
        }

        return mejorSolucion;
    }

    /**
     * Complejidad temporal de getProcesadorOptimo: O(m)
     * donde m es el número de procesadores.
     */
    private Procesador getProcesadorOptimo(Tarea tarea, int tiempoMaximoNoRefrigerado) {
        Procesador procesadorOptimo = null;

        int menorTiempo = Integer.MAX_VALUE;

        for (Procesador procesador : this.mejorSolucion.getAsignacion().keySet()) {

            int tiempoActual  = this.mejorSolucion.calcularTiempoEjecucionTareas(procesador);

            if (this.mejorSolucion.puedeAsignar(procesador, tarea, tiempoMaximoNoRefrigerado) && tiempoActual < menorTiempo) {
                menorTiempo = tiempoActual;
                procesadorOptimo = procesador;
            }
        }

        return procesadorOptimo;
    }
}