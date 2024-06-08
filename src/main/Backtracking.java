package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {
    private int cantidadEstadosGenerados;

    public Backtracking() {
        this.cantidadEstadosGenerados = 0;
    }

    public int getCantidadEstadosGenerados() {
        return cantidadEstadosGenerados;
    }

    public Solucion resolver(int tiempoMaximoNoRefrigerados, ArrayList<Tarea> listaTareas, ArrayList<Procesador> listaProcesadores) {
        Map<Procesador, List<Tarea>> asignacionInicial = new HashMap<>();

        for (Procesador procesador : listaProcesadores) {
            asignacionInicial.put(procesador, new ArrayList<>());
        }

        Solucion s = new Solucion(asignacionInicial, Integer.MAX_VALUE, 0); // Inicializamos con valores máximos para encontrar una solución mejor

        backtrackingAsignarTareas(0, tiempoMaximoNoRefrigerados, listaTareas, listaProcesadores, asignacionInicial, s);

        return s;
    }

    private void backtrackingAsignarTareas(int nroTareaActual, int tiempoMaximoNoRefrigerados, ArrayList<Tarea> listaTareas, ArrayList<Procesador> listaProcesadores,
                                           Map<Procesador, List<Tarea>> asignacionActual, Solucion mejorSolucion) {
        this.cantidadEstadosGenerados++;

        if (nroTareaActual == listaTareas.size()) {
            int tiempoActual = calcularTiempoEjecucion(asignacionActual);
            if (tiempoActual < mejorSolucion.getTiempoMaximo()) {
                int costoActual = calcularCostoSolucion(asignacionActual);
                mejorSolucion.setAsignacion(new HashMap<>(asignacionActual));
                mejorSolucion.setTiempoMaximo(tiempoActual);
                mejorSolucion.setCostoSolucion(costoActual);
            }
        } else {
            Tarea tareaActual = listaTareas.get(nroTareaActual);
            for (Procesador procesadorActual : listaProcesadores) {
                List<Tarea> tareasAsignadas = asignacionActual.get(procesadorActual);
                if (puedeAsignar(tareaActual, procesadorActual, tareasAsignadas, tiempoMaximoNoRefrigerados)) {
                    asignarTarea(tareaActual, procesadorActual, asignacionActual);
                    backtrackingAsignarTareas(nroTareaActual + 1, tiempoMaximoNoRefrigerados, listaTareas, listaProcesadores, asignacionActual, mejorSolucion);
                    desasignarTarea(tareaActual, procesadorActual, asignacionActual);
                }
            }
        }
    }

    private int calcularTiempoEjecucion(Map<Procesador, List<Tarea>> asignacionActual) {
        int maxTiempo = 0;
        for (List<Tarea> tareas : asignacionActual.values()) {
            int tiempoTotal = calcularTiempoTotal(tareas);
            if (tiempoTotal > maxTiempo) {
                maxTiempo = tiempoTotal;
            }
        }
        return maxTiempo;
    }

    private int calcularTiempoTotal(List<Tarea> tareasAsignadas) {
        int tiempoTotal = 0;

        for (Tarea t : tareasAsignadas) {
            tiempoTotal += t.getTiempo();
        }

        return tiempoTotal;
    }

    private int calcularCostoSolucion(Map<Procesador, List<Tarea>> asignacion) {
        int costoTotal = 0;

        for (List<Tarea> tareas : asignacion.values()) {
            for (Tarea tarea : tareas) {
                costoTotal += tarea.getTiempo();
            }
        }

        return costoTotal;
    }

    private void desasignarTarea(Tarea tarea, Procesador procesador, Map<Procesador, List<Tarea>> asignacionTareasActual) {
        asignacionTareasActual.get(procesador).remove(tarea);
    }

    private void asignarTarea(Tarea tarea, Procesador procesador, Map<Procesador, List<Tarea>> asignacionTareasActual) {
        asignacionTareasActual.get(procesador).add(tarea);
    }

    private boolean puedeAsignar(Tarea tarea, Procesador procesador, int tiempoMaximoNoRefrigerado, Map<Procesador, List<Tarea>> asignacion) {
        // Verificar si el procesador no refrigerado no excede el tiempo máximo permitido
        if (!procesador.getRefrigerado() && getTiempoProcesador(procesador, asignacion) + tarea.getTiempo() > tiempoMaximoNoRefrigerado) {
            return false;
        }

        // Verificar si la última tarea asignada al procesador es crítica
        // y si esta nueva tarea es crítica también
        if (tarea.getCritica() && ultimaTareaAsignadaEsCritica(procesador, asignacion)) {
            return false;
        }

        return true;
    }

    private boolean ultimaTareaAsignadaEsCritica(Procesador procesador, Map<Procesador, List<Tarea>> asignacion) {
        List<Tarea> tareasAsignadas = asignacion.get(procesador);

        if (!tareasAsignadas.isEmpty()) {
            Tarea ultimaTareaAsignada = tareasAsignadas.get(tareasAsignadas.size() - 1);
            return ultimaTareaAsignada.getCritica();
        }

        return false;
    }

    private int getTiempoProcesador(Procesador procesador, Map<Procesador, List<Tarea>> asignacion) {
        int tiempoTotal = 0;

        for (Tarea tarea : asignacion.get(procesador)) {
            tiempoTotal += tarea.getTiempo();
        }

        return tiempoTotal;
    }
}
