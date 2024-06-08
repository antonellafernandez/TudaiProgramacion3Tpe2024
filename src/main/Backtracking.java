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

    // Complejidad: en el peor de los casos O(m^n) donde m es el número de procesadores y n es el número de tareas
    public Solucion resolver(int tiempoMaximoNoRefrigerados, List<Procesador> listaProcesadores, List<Tarea> listaTareas) {
        int nroTareaActual = 0;

        Map<Procesador, List<Tarea>> asignacionInicial = new HashMap<>();
        Map<Procesador, Integer> tiempoEjecucionProcesadores = new HashMap<>();

        for (Procesador procesador : listaProcesadores) {
            asignacionInicial.put(procesador, new ArrayList<>());
            tiempoEjecucionProcesadores.put(procesador, 0);
        }

        Solucion mejorSolucion = new Solucion(asignacionInicial, Integer.MAX_VALUE, 0); // Inicializar con valores máximos para encontrar una solución mejor

        // Verificar la poda antes de empezar la asignación
        if (!poda(tiempoEjecucionProcesadores, mejorSolucion)) {
            backtrackingAsignarTareas(nroTareaActual, tiempoMaximoNoRefrigerados, listaTareas, listaProcesadores, asignacionInicial, tiempoEjecucionProcesadores, mejorSolucion);
        }

        return mejorSolucion;
    }


    // Método de poda
    // Comlejidad: O(m) donde m es el número de procesadores
    private boolean poda(Map<Procesador, Integer> tiempoEjecucionProcesadores, Solucion mejorSolucion) {
        int tiempoActual = calcularTiempoEjecucion(tiempoEjecucionProcesadores);
        return tiempoActual >= mejorSolucion.getTiempoMaximo();
    }

    // Complejidad: en el peor de los casos O(m^n)
    private void backtrackingAsignarTareas(int nroTareaActual, int tiempoMaximoNoRefrigerados, List<Tarea> listaTareas, List<Procesador> listaProcesadores,
                                           Map<Procesador, List<Tarea>> asignacionActual, Map<Procesador, Integer> tiempoEjecucionProcesadores, Solucion mejorSolucion) {

        this.cantidadEstadosGenerados++;
        mejorSolucion.setCostoSolucion(this.cantidadEstadosGenerados);

        if (nroTareaActual == listaTareas.size()) {
            int tiempoActual = calcularTiempoEjecucion(tiempoEjecucionProcesadores);
            if (tiempoActual < mejorSolucion.getTiempoMaximo()) {
                mejorSolucion.setAsignacion(new HashMap<>(asignacionActual)); // Copiar la asignación actual
                mejorSolucion.setTiempoMaximo(tiempoActual);
            }
        } else {
            Tarea tareaActual = listaTareas.get(nroTareaActual); // Tomar la tarea actual de la lista de tareas

            for (Procesador procesadorActual : listaProcesadores) {
                // Tomar la lista de tareas que se le asigno hasta el momento
                List<Tarea> tareasAsignadas = asignacionActual.get(procesadorActual);

                if (puedeAsignar(tareaActual, procesadorActual, tareasAsignadas, tiempoMaximoNoRefrigerados)) {
                    // Asignar la tarea al procesador
                    asignarTarea(tareaActual, procesadorActual, asignacionActual, tiempoEjecucionProcesadores);

                    // Verificar la poda antes de la llamada recursiva
                    if (!poda(tiempoEjecucionProcesadores, mejorSolucion)) {
                        // Llamar recursivamente para la siguiente tarea
                        backtrackingAsignarTareas(nroTareaActual + 1, tiempoMaximoNoRefrigerados, listaTareas, listaProcesadores, asignacionActual, tiempoEjecucionProcesadores, mejorSolucion);
                    }

                    // Desasignar la tarea cuando regresa de la recursión
                    desasignarTarea(tareaActual, procesadorActual, asignacionActual, tiempoEjecucionProcesadores);
                }
            }
        }
    }

    // Complejidad: O(1) ya que solo elimina un elemento de la lista y resta el tiempo de la tarea al tiempo de ejecución total
    private void desasignarTarea(Tarea tarea, Procesador procesador, Map<Procesador, List<Tarea>> asignacionActual, Map<Procesador, Integer> tiempoEjecucionProcesadores) {
        asignacionActual.get(procesador).remove(tarea);
        tiempoEjecucionProcesadores.put(procesador, tiempoEjecucionProcesadores.get(procesador) - tarea.getTiempo());
    }

    // Complejidad: O(1) ya que solo agrega un elemento a la lista y suma el tiempo de la tarea al tiempo de ejecución total
    private void asignarTarea(Tarea tarea, Procesador procesador, Map<Procesador, List<Tarea>> asignacionActual, Map<Procesador, Integer> tiempoEjecucionProcesadores) {
        asignacionActual.get(procesador).add(tarea);
        tiempoEjecucionProcesadores.put(procesador, tiempoEjecucionProcesadores.get(procesador) + tarea.getTiempo());
    }

    // Complejidad: O(n) donde n es el número de tareas asignadas al procesador
    private boolean puedeAsignar(Tarea tarea, Procesador procesador, List<Tarea> tareasAsignadas, int tiempoMaximoNoRefrigerado) {
        if (tareasAsignadas == null) {
            tareasAsignadas = new ArrayList<>();
        }

        // Verificar si el procesador ya tiene más de dos tareas críticas asignadas
        if (tieneMasDeDosTareasCriticas(tareasAsignadas)) {
            return false;
        }
        // Verificar si el procesador es refrigerado
        if (procesador.getRefrigerado()) {
            return true;
        } else {
            // Calcular el tiempo total de ejecución si se añade una nueva tarea
            int tiempoTotal = calcularTiempoTotal(tareasAsignadas) + tarea.getTiempo();
            return tiempoTotal <= tiempoMaximoNoRefrigerado; // Verificar si el tiempo total no excede el máximo permitido sin refrigeración
        }
    }

    // Complejidad: O(n) donde n es el número de tareas asignadas al procesador
    public boolean tieneMasDeDosTareasCriticas(List<Tarea> tareasAsignadas) {
        int cantidadTareasCriticas = 0;

        for (Tarea tarea : tareasAsignadas) {
            if (tarea.getCritica()) {
                cantidadTareasCriticas++;

                if (cantidadTareasCriticas >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    // Complejidad: O(n) donde n es el número de tareas
    private int calcularTiempoTotal(List<Tarea> tareas) {
        int tiempoTotal = 0;

        for (Tarea t : tareas) {
            tiempoTotal += t.getTiempo();
        }
        return tiempoTotal;
    }

    // Complejidad: O(1) ya que solo se itera sobre los procesadores
    private int calcularTiempoEjecucion(Map<Procesador, Integer> tiempoEjecucionProcesadores) {
        int maxTiempo = 0;

        for (int tiempoTotal : tiempoEjecucionProcesadores.values()) {
            if (tiempoTotal > maxTiempo) {
                maxTiempo = tiempoTotal;
            }
        }
        return maxTiempo;
    }
}
