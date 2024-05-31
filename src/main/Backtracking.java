package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Backtracking extends Solucion {
    private int cantidadEstadosGenerados;

    public Backtracking(int tiempoMaximo, HashMap<String, Procesador> procesadores) {
        super(tiempoMaximo, procesadores);
        cantidadEstadosGenerados = 0;
    }

    public int getCantidadEstadosGenerados() {
        return cantidadEstadosGenerados;
    }

    @Override
    public Solucion resolver(int tiempoMaximoNoRefrigerado, HashMap<String, Procesador> procesadores, ArrayList<Tarea> tareas) {
        ArrayList<Procesador> listaProcesadores = new ArrayList<>(procesadores.values());
        backtrack(0, listaProcesadores, tareas, tiempoMaximoNoRefrigerado);

        return this;
    }

    private void backtrack(int tareaActual, ArrayList<Procesador> listaProcesadores, ArrayList<Tarea> tareas, int tiempoMaximoNoRefrigerado) {
        cantidadEstadosGenerados++;  // Incrementar la cantidad de estados generados

        if (tareaActual == tareas.size()) {
            int maxTiempoActual = 0;

            // Calcular el tiempo máximo de ejecución actual entre todos los procesadores
            for (Procesador procesador : listaProcesadores) {
                maxTiempoActual = Math.max(maxTiempoActual, getTiempoProcesador(procesador));
            }

            // Actualizar la mejor asignación si encontramos una mejor
            if (maxTiempoActual < this.getTiempoMaximo()) {
                this.setTiempoMaximo(maxTiempoActual);
                actualizarMejorAsignacion(listaProcesadores);
            }

            return;
        }

        Tarea tarea = tareas.get(tareaActual);

        // Intentar asignar la tarea actual a cada procesador
        for (Procesador procesador : listaProcesadores) {
            if (puedeAsignar(tarea, procesador, tiempoMaximoNoRefrigerado)) {
                asignarTarea(tarea, procesador);
                backtrack(tareaActual + 1, listaProcesadores, tareas, tiempoMaximoNoRefrigerado);
                desasignarTarea(tarea, procesador);
            }
        }
    }

    private boolean puedeAsignar(Tarea tarea, Procesador procesador, int tiempoMaximoNoRefrigerado) {
        // Verificar si el procesador no refrigerado no excede el tiempo máximo permitido
        if (!procesador.getRefrigerado() && getTiempoProcesador(procesador) + tarea.getTiempo() > tiempoMaximoNoRefrigerado) {
            return false;
        }

        // Verificar si la última tarea asignada al procesador es crítica y si esta nueva tarea es crítica también
        if (tarea.getCritica() && ultimaTareaAsignadaEsCritica(procesador)) {
            return false;
        }

        return true;
    }

    private boolean ultimaTareaAsignadaEsCritica(Procesador procesador) {
        List<Tarea> tareasAsignadas = this.getAsignacion().get(procesador);

        if (!tareasAsignadas.isEmpty()) {
            Tarea ultimaTareaAsignada = tareasAsignadas.get(tareasAsignadas.size() - 1);
            return ultimaTareaAsignada.getCritica();
        }

        return false;
    }

    private int getTiempoProcesador(Procesador procesador) {
        int tiempoTotal = 0;

        // Calcular el tiempo total de ejecución de las tareas asignadas al procesador
        for (Tarea tarea : this.getAsignacion().get(procesador)) {
            tiempoTotal += tarea.getTiempo();
        }

        return tiempoTotal;
    }

    private void asignarTarea(Tarea tarea, Procesador procesador) {
        List<Tarea> tareasAsignadas = this.getAsignacion().get(procesador);
        tareasAsignadas.add(tarea);
    }

    private void desasignarTarea(Tarea tarea, Procesador procesador) {
        List<Tarea> tareasAsignadas = this.getAsignacion().get(procesador);
        tareasAsignadas.remove(tarea);
    }

    private void actualizarMejorAsignacion(ArrayList<Procesador> listaProcesadores) {
        // Actualizar la mejor asignación encontrada
        this.getAsignacion().clear();

        for (Procesador procesador : listaProcesadores) {
            this.getAsignacion().put(procesador, new ArrayList<>(this.getAsignacion().get(procesador)));
        }
    }

    @Override
    public String toString() {
        return "Backtracking{" + super.toString() +
                "Costo de la solución (cantidad de estados generados): " + cantidadEstadosGenerados +
                '}';
    }
}
