package main;

import java.util.List;
import java.util.Stack;

public class Backtracking {

    private Solucion mejorSolucion;
    private int cantidadEstadosGenerados;


    public Backtracking() {
        this.mejorSolucion = new Solucion();
        this.cantidadEstadosGenerados = 0;
    }

    public Backtracking(List<Procesador> procesadores) {
        this.mejorSolucion = new Solucion(procesadores);
        this.cantidadEstadosGenerados = 0;
    }

    public int getCantidadEstadosGenerados() {
        return this.cantidadEstadosGenerados;
    }

    /**
     * Complejidad en el peor de los casos de resolver: O(m^n)
     * donde m es la cantidad de procesadores y n la cantidad de tareas.
     */
    public Solucion resolver(int tiempoMaximoNoRefrigerados, List<Procesador> listaProcesadores, Stack<Tarea> pilaTareas) {
        Solucion asignacionActual = new Solucion(listaProcesadores);

        int cantTotalTareas = pilaTareas.size();

        this.backtrackingAsignarTareas(tiempoMaximoNoRefrigerados, pilaTareas, listaProcesadores, asignacionActual,cantTotalTareas);

        if (this.mejorSolucion.getCantTareasAsignadas() == 0){
            return null;
        }

        return this.mejorSolucion;
    }

    /**
     * Complejidad en el peor de los casos de backtrackingAsignarTareas: O(m^n)
     * donde m es la cantidad de procesadores y n la cantidad de tareas.
     */
    private void backtrackingAsignarTareas(int tiempoMaximoNoRefrigerados, Stack<Tarea> pilaTareas, List<Procesador> listaProcesadores,
                                           Solucion asignacionActual,  int cantTotalTareas) {

        this.cantidadEstadosGenerados++;

        if (pilaTareas.empty()){
            if (this.mejorSolucion == null || (this.mejorSolucion.getTiempoMaximo() > asignacionActual.getTiempoMaximo() && cantTotalTareas == asignacionActual.getCantTareasAsignadas())){
                this.mejorSolucion.setAsignacion(asignacionActual);
            }
        } else {
            Tarea tareaActual = pilaTareas.pop(); // Toma la tarea actual de la pila de tareas y la borra

            for (Procesador procesador : listaProcesadores) {
                if (asignacionActual.puedeAsignar(procesador, tareaActual, tiempoMaximoNoRefrigerados)) { // Corrobora si esa tarea se puede asignar
                    // Asigna la tarea al procesador
                    asignacionActual.asignarTarea(procesador, tareaActual);

                    if ( this.mejorSolucion == null || (asignacionActual.getTiempoMaximo() < this.mejorSolucion.getTiempoMaximo())){ //Se fija si la asignacion actual puede llegar a ser una buena asignacion llama ala recursion, sino hace la PODA
                        backtrackingAsignarTareas(tiempoMaximoNoRefrigerados,pilaTareas,listaProcesadores, asignacionActual,cantTotalTareas);
                    }
                    // Desasigna la tarea del procesador cuando regresa de la recursión
                    asignacionActual.desasignarTarea(procesador,tareaActual);
                }
            }
            pilaTareas.push(tareaActual); // Toma la tarea actual y la agrega devuelta a la pila de tareas
        }
    }
}
